<template>

<div style="height:300px;width:400px;">
    <video src="" controls height="300" ></video>
    <div style="display:flex;justify-content:center;">
        <button class="btn btn-success btn-sm" style="margin-right:10px;" ref="startRecord">开始录制</button>
        <button class="btn btn-danger btn-sm" style="margin-left:10px;" ref="stopRecord">停止录制</button>
    </div>
</div>

</template>
<script>
import axios from 'axios';

export default {
    name:'VideoInfo',
    mounted(){
        this.handleCamera();

    },
    data(){
        return {

        }
    },
    methods:{
        //处理开启摄像头，录制视频等一系列的事务
        handleCamera(){
            // 想要获取一个最接近 1280x720 的相机分辨率
            let _this = this;
            var constraints = { audio: false, video: { width: 1280, height: 720 } };

            const startDom = this.$refs.startRecord;
            const stopDom = this.$refs.stopRecord;


            navigator.mediaDevices.getUserMedia(constraints)
            .then(function(mediaStream) {
                var video = document.querySelector('video');
                video.srcObject = mediaStream;
                video.onloadedmetadata = function(e) {
                    video.play();

                    let chunks = [];  //一块一块的数据

                    //判断浏览器是否支持最新的格式，如果不支持的话就用原来额格式就行了
                    const mime = MediaRecorder.isTypeSupported("video/webm; codecs=vp9")?'video/webm; codecs=vp9':'video/webm';
                    /***
                     * 这个录制的格式一定要是webm，然后录制好以后可以转成.mp4 .mkv这些不同的格式都没有问题的
                     */
                    const mediaRecorder = new MediaRecorder(mediaStream,{mimeType:mime});

                    //把录制的接口函数绑定到开始录制按钮的onclick事件上
                    startDom.onclick = function(){
                        //录制视频
                        //视频的格式是mp4的，而且，帧率是2.5Mb/s
                        // let options = {
                        //     // audioBitsPerSecond : 128000,
                        //     videoBitsPerSecond : 2500000,
                        //     mimeType : 'video/mp4'  //如果把这个options配置加入到new MediaRecorder中的话，会报一个错误：无法构建mp4的文件
                        // };
                        //那这个东西该怎么用呢?
                        // console.log(mediaRecorder);
                        //开始录制
                        mediaRecorder.start();
                        console.log("开始录制!");
                        console.log(mediaRecorder.state);

                        //把录制的数据存放到chunks中去
                        /***
                         * 保存录制的数据到
                         * chunks数组中
                         */
                        mediaRecorder.ondataavailable = function(e){
                            if(chunks.length == 0){
                                chunks.push(e.data);
                            }else{
                                chunks = [];
                                chunks.push(e.data);
                            }
                        }

                    }

                    //视频停止录制和保存录制的数据绑定在停止录制的按钮上捏
                    stopDom.onclick = function(){
                        mediaRecorder.stop();
                        console.log(mediaRecorder.state); console.log("录制停止");
                    }

                    //如果停止事件被触发，调用该函数
                    mediaRecorder.onstop = function(e) {
                        console.log("保存并上传视频文件");
                        console.log(chunks);

                        const blobData = new Blob(chunks,{type:chunks[0].type});
                        // console.log(blobData);
                        /***
                         * 生成的blob对象之所以没有用很可能是因为它的url只是是用于https而不适用于http。。。所以怎么也访问不了捏!
                         */
                        const blobURL = window.URL.createObjectURL(blobData);
                        console.log(blobURL);


                        //上传文件对象
                        //妈的原来js可以new一个文件对象的啊，我还真的不知道捏...

                        var file = new File([blobData], "videoRecord.mkv");
                        console.log(file);

                        var formData = new FormData();
                        formData.append('file', file);
                        axios.post('http://localhost:8080/api/video/info/upload',formData).then(function(response){
                            let rets = response.data;
                            console.log(rets);
                        },function(error){
                        });

                        // _this.saveFileLocal(blobURL);

                        //重置chunks重置内容
                        chunks = [];

                    }

                };





            })
            .catch(function(err) { console.log(err.name + ": " + err.message); }); // 总是在最后检查错误

        },
        //开启录制的接口函数
        startRecord(){


        },
        //停止录制的接口函数
        stopRecord(){

        },
        //下载文件到本地
        saveFileLocal(blob){
            // 保存文件(产生下载的效果)
            const link = document.createElement('a');
            link.style.display = 'none';
            link.href = blob;
            link.download = 'media_.webm';
            document.body.appendChild(link);
            link.click();
            link.remove();
        }


    },
    unmounted(){

    }

}
</script>
<style scoped>

</style>
