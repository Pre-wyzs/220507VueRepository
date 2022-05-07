<template>
       <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-sm-10" style="background-color:white;;height:600px;display:flex;justify-content:center;">
                <video autoplay playsinline id="webCam" ref="webCam"></video>
            </div>
            <div class="w-100">
            </div>
            <div class="col-sm-10" style="display:flex;justify-content:center;">
                <button class="btn btn-success" @click="submitPhoto()" >人脸识别签到</button>
            </div>
        </div>
    </div>
    <canvas id="canvas" ref="canvasref"></canvas>
    <!-- <img id="img" width="640" height="480"> -->
</template>
<script>
import axios from 'axios'
import Webcam from 'webcam-easy';

export default {
    name:'Camera',
    mounted(){
        this.camera = this.openCamera();
        let _this = this;
        // setTimeout(function(){
        //     _this.submitPhoto();
        // },3500);
    },
    beforeUnmount(){
        this.camera.stop();
    },
    data(){
        return {
            camera:null,
            file:null
        }
    },
    computed:{  //bug原因把对象数据写到computed里面去了，给我整不会了。

    },
    methods:{
        openCamera(){
            const webCamDom = this.$refs.webCam;
            const canvasDom = this.$refs.canvasref;

            //创建webcam对象
            const camera = new Webcam(webCamDom,"user",canvasDom);

            //开启摄像头
            camera.start();
            return camera;
        },
        //主要就是拍摄一张照片然后再把这个照片发送到服务器的查询接口上面
        submitPhoto(){
            let pic = this.camera.snap();
            // let imgDom = document.getElementById('img');
            // imgDom.setAttribute('src',pic);
            console.log(pic);
            pic = pic.slice(22);



            let apiUrl = 'http://localhost:8080/api/worker/sign/face';
            const formData = new URLSearchParams();
            formData.append('file',pic);
            formData.append('code',this.$route.params.code);
            // console.log(formData);
            let _this = this;

            axios.post(apiUrl,formData).then(function(response){
                let rets = response.data;
                console.log(rets.data);
                alert(rets.data);
                _this.camera.stop();  //关闭摄像头，哈哈
                _this.$router.push({name:'WorkerSign'});

            },function(error){
            });
        },

    }

}
</script>

<style scoped>

canvas{
    display: none;
}


</style>