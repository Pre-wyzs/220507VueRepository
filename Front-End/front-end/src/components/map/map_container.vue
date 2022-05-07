<template>
         <div id="container" style="display:flex;"></div>
</template>
<script>
import AMapLoader from '@amap/amap-jsapi-loader';
import { shallowRef } from '@vue/reactivity'
import axios from 'axios'
export default {
    name:'MapContainer',
    setup(){
        const map = shallowRef(null);
        return{
            map,
        }
    },
    mounted(){
        this.initMap();
        //因为要在地图上画点，所以一定要先从数据库获得有哪些车辆对的吧。。哈哈
        this.getAllIMEIS();

                        // console.log("已从后端获取数据");
        var socket = new WebSocket("ws://localhost:8080/ws/api/equipment");
        let _this = this;
        socket.onopen = function(){
            console.log('websocket linked');
            //成功建立链接后发送这个连接的唯一名称
            socket.send("Map-Linking");
        }
        /**在这个钩子函数中开启socket的监听事件 ：当服务器的session向它发送消息的时候，自然就会调用到这个回调函数了*/
        socket.onmessage = function(evt){
            //返回更新了数据的那辆车的设备号
            let retIMEI = evt.data;

            //写到如何更新呢捏！
            _this.marks.forEach(obj=>{
                if(retIMEI == obj.imei){
                    // console.log('已经命中了');
                    //找到报点的那辆车了，然后让这辆车也进行移动


                    axios.post('http://localhost:8080/api/car/history/map',[retIMEI]).then(function(response){
                        let rets = response.data;
                        let retPath = rets.data;   //这里返回的数据是一个键值对啊兄嘚...
                        let history = Object.values(retPath)[0];
                        // console.log(history);
                        // console.log(mark);

                        //因为已经产生新的移动了，所以原来的cpy标记应该的坐标应该改成新的坐标了捏
                        obj.markCpy.setPosition([history[history.length-1].jd,history[history.length-1].wd]);

                        obj.mark.moveTo([history[0].jd,history[0].wd],{
                            duration: 2500, //这个值太小的话就会导致，播放的动画很卡。这涉及到短时间内绘制图像的一个性能问题，总之这个bug我们是解决不了的
                            autoRotation: false
                        });

                        let path = [];
                        history.forEach(item=>{
                            path.push(new AMap.LngLat(item.jd,item.wd));
                        })

                        obj.line.setPath(path.reverse());
                        // obj.line.hide();  //都先隐藏起来，不然的话，都是路径出现后，车辆才移动的，那就很难看。。。个人感觉。
                        obj.line.show();
                        obj.line.moveWithPos(history[0].jd,history[0].wd);

                        //重新设置点击事件
                        obj.mark.clearEvents('click');
                        _this.boundClickEvent(obj.mark,obj.line,path,obj.markCpy,history);


                    },function(error){

                    });


                }
            });
        }

        socket.onclose = function(){
            console.log('websocket closed');
        }

        socket.error = function(){
            console.log('websocket error');
        }



    },
    beforeUnmount(){
        // console.log(this.map);
        this.map.destory;
        // alert('卸载函数已经执行');
    },
    data(){
        return {
            carStatus:'已熄火',//车辆的状态主要有两种，一是行驶中，二是已熄火。

        /*------数据库中的所有imei设备---------------------------------------------------------------------------------------------------- */
            imeis:[],  //

            carPath:[],

            marks:[]

        }
    },
    
    methods:{
        initMap(){
            let that = this;
            AMapLoader.load({
                key:"929ecdde8edd530fc955ce1ddb54f09b",             // 申请好的Web端开发者Key，首次调用 load 时必填
                version:"2.0",      // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
                plugins:['AMap.MoveAnimation','AMap.Scale','AMap.MouseTool'],       // 需要使用的的插件列表，如比例尺'AMap.Scale'等
            }).then((AMap)=>{
                that.map = new AMap.Map("container",{  //设置地图容器id
                    viewMode:"2D",    //是否为3D地图模式
                    zoom:13,           //初始化地图的缩放级别，zoom的值越大，放的越大
                    center:[120.156358,30.317367], //初始化地图中心点位置
                    mapStyle:'amap://styles/darkblue'
                });
                //为地图添加比例尺
                var scale = new AMap.Scale();
                that.map.addControl(scale);

                //为地图创建鼠标工具，进行自定义点的标记操作
                //    var mousetool = new AMap.MouseTool(that.map);
                // 使用鼠标工具，在地图上画标记点
                // mousetool.rule();


                //为地图添加右键菜单
                //创建右键菜单
                var contextMenu = new AMap.ContextMenu();
        //给地图添加上右击菜单事件
                //右键放大
                contextMenu.addItem("放大一级", function () {
                    that.map.zoomIn();
                }, 0);

                //右键缩小
                contextMenu.addItem("缩小一级", function () {
                    that.map.zoomOut();
                }, 1);

                let contextMenuPositon = [120.156358,30.317367]
                //右键添加Marker标记
                contextMenu.addItem("添加标记", function (e) {
                    var marker = new AMap.Marker({
                        map: that.map,
                        position: contextMenuPositon //基点位置
                    });
                }, 3);

                //地图绑定鼠标右击事件——弹出右键菜单
                that.map.on('rightclick', function (e) {
                    contextMenu.open(that.map, e.lnglat);
                    contextMenuPositon = e.lnglat;
                });

                // contextMenu.open(that.map, lnglat);



            }).catch(e=>{
                console.log(e);
            })
        },

        
        /*------从数据库获取所有的imeis设备---------------------------------------------------------------------------------------------------- */
        async getAllIMEIS(){
            const {data: ret} = await axios.get('http://localhost:8080/api/car/info/all/imeis');
            this.imeis = ret.data
            this.getAllCarPath(this.imeis);
        },

        /*------获取所有的车辆的路径信息---------------------------------------------------------------------------------------------------- */
        getAllCarPath(imeis){
            let _this = this;
            axios.post('http://localhost:8080/api/car/history/map',imeis).then(function(response){
                let ret = response.data;
                _this.carPath = ret.data
                _this.pointPosition(_this.carPath);
            },function(error){
            });
        },


        /*------根据获得的所有的车辆的路径信息，从而在地图上绘制出所有车辆路径覆盖物---------------------------------------------------------------------------------------------------- */
        pointPosition(carPath){
            let that = this;
            AMapLoader.load({
                key:"929ecdde8edd530fc955ce1ddb54f09b",             // 申请好的Web端开发者Key，首次调用 load 时必填
                version:"2.0",      // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
            }).then((AMap)=>{
                let iconUrl = require("../../assets/images/car2.png");

                //@创建icon实例
                var icon = new AMap.Icon({
                    size: new AMap.Size(45, 45),    // 图标尺寸
                    image: iconUrl,  // Icon的图像
                    // imageOffset: new AMap.Pixel(0, -60),  // 图像相对展示区域的偏移量，适于雪碧图等
                    imageSize: new AMap.Size(45, 45)   // 根据所设置的大小拉伸或压缩图片
                });



                // console.log(Object.values(carPath)[0]);

        /*------遍历js对象中的每一个属性：Object.keys(obj)---------------------------------------------------------------------------------------------------- */
                Object.values(carPath).forEach(car=>{
                    //格式转换
                    // console.log(car);
                    let markCpy = null;  //只有熄火了的车辆才需要这个图表捏
                        //@渣土车在熄火状态下的一个备份图标
                        markCpy = new AMap.Marker({
                            position:new AMap.LngLat(car[car.length-1].jd,car[car.length-1].wd),
                            offset: new AMap.Pixel(-20, -25),
                            // title:'徐大强'+'\n'+'浙A12345',
                            icon:icon,
                            // angle:360,  //设置旋转的角度，但是好像没什么用  
                            height:2
                        });

                    //@创建一个渣土车标记：这个标记一定是最新的才对
                    let mark = new AMap.Marker({
                        position:new AMap.LngLat(car[0].jd,car[0].wd),
                        offset: new AMap.Pixel(-20, -25),
                        // title:'浙A12345\n'+'熄火',
                        title:car[0].license+'\n'+car[0].carStatus,
                        icon:icon,
                        // angle:360,  //设置旋转的角度，但是好像没什么用  
                        height:2,
                        zIndex:14
                    });


                    let path = [];
                    car.forEach(item=>{
                        path.push(new AMap.LngLat(item.jd,item.wd));
                    })

                    let polyline = new AMap.Polyline({
                        path: path.reverse(),  
                        borderWeight: 2, // 线条宽度，默认为 1
                        strokeColor: '#FFFF00', // 线条颜色
                        strokeOpacity:0.5,  //路径的透明度
                        strokeStyle:'solid',  //线的样式
                        lineJoin: 'round', // 折线拐点连接处样式
                        strokeWeight:'6',  //线宽度
                        isOutline:false,
                        borderWeight:'1',
                        outlineColor:'green',
                        lineCap:'round',
                        showDir:true
                    });

                    //把这个标识存储到本地变量中
                    this.marks.push({imei:car[0].imei,mark:mark,line:polyline,markCpy:markCpy});

                    //@创建一个覆盖物组对象
                    var carGroup = new AMap.OverlayGroup([mark,polyline,markCpy]);
                    that.map.add(carGroup);
                    // if(car[0].carStatus == 0){
                    //     //车子在熄火状态下的展示
                    //     //因为车辆的状态有两种：一个是行驶中，一个是已熄火，所以如果已经熄火的话就没必要展示这个路径了，
                    //     //然后点击这个熄火状态的车辆以后，它的历史路径会显示出来，然后
                    //     //还有配上，卡车运动的一个样子。
                    //     polyline.hide();
                    //     markCpy.hide();
                    // }else{
                    //     polyline.show();
                    //     markCpy.hide();
                    // }
                    //这部分我想重新设计过，就是如果车辆熄火的话，不用隐藏它的路径了，直接通过点击函数查看它的状态就可以知道是熄火还是行驶中了
                    //为什么呢？因为有的时候点击content会把路径遮盖掉，还不如让它走过的路径一直显示在那里就好了捏...
                    polyline.show();
                    markCpy.hide();  //同样的备份图表一定要隐藏起来。

                    //@给车辆图标绑定上事件
                    that.boundClickEvent(mark,polyline,path,markCpy,car);

                })

                // //@在地图上创建路径
                // var path = [
                //     new AMap.LngLat(120.121281,30.222718),
                //     new AMap.LngLat(120.142224,30.22468),
                //     new AMap.LngLat(120.171749,30.23699),
                //     new AMap.LngLat(120.183766,30.242033),
                //     new AMap.LngLat(120.169003,30.276284),
                //     new AMap.LngLat(120.15115,30.304002),
                //     new AMap.LngLat(120.155613,30.323415),
                // ];
                // var polyline = new AMap.Polyline({
                //     path: path,  
                //     borderWeight: 2, // 线条宽度，默认为 1
                //     strokeColor: '#FFFF00', // 线条颜色
                //     strokeOpacity:0.5,  //路径的透明度
                //     strokeStyle:'solid',  //线的样式
                //     lineJoin: 'round', // 折线拐点连接处样式
                //     strokeWeight:'6',  //线宽度
                //     isOutline:false,
                //     borderWeight:'1',
                //     outlineColor:'green',
                //     lineCap:'round',
                //     showDir:true
                // });






                // carGroup.hide();整体隐藏



            });
        },

        //事件绑定函数
        /**
         * 参数一：要给哪个标记绑定上事件
         * 参数二，绑定上什么标记
         * 
         */
        boundClickEvent(mark,polyline,path,markCpy,history){
            //@给渣土车绑定上事件
            let flag = true;
            let convertStatus = '';
            mark.on('click',function(){
                if(flag == true){
                    if(history[0].carStatus==0){
                        //这样子就可以展示了
                        polyline.show();
                        markCpy.show();

                        /**注意：这个动画只有在path的长度>1的时候才可以，两个点一条直线嘛。一个点根本连线都没有，怎么
                         * 能执行这个函数呢?所以出错了呗。。。只要这个车辆不是出生点的车辆，path一定是>1对吧，所以只要在
                         * init的时候第一次传递一个不为null的path就行了，其他情况下，path一null般都传递为空
                         * 不是的,还是要传递的，不论在什么情况下
                         */
                        //渣土车沿着路径运动的动画
                        //地图初始化的时候传进来的path不为空的，所以要判断一下
                        if(path.length>1){
                            markCpy.moveAlong(path,{
                                duration:1000,
                                circlable:true,
                                autoRotation:false
                            });
                        }
                        //顺便改成已经熄火
                        convertStatus='已熄火';
                    }else{
                        //顺便改成行驶中
                        convertStatus='行驶中';
                    }
                    mark.setLabel({
                        content:'<ul class="list-group" style="color:blue;background-color:rgba(0,0,0,0.2);">\n'+
                                '<li class="list-group-item " style="font-size:8px;padding:5px 5px;background-color:rgba(0,0,0,0.2);">车牌号:'+history[0].license+'</li>\n'+
                                '<li class="list-group-item " style="font-size:8px;padding:5px 5px;background-color:rgba(0,0,0,0.2);">车架号:'+history[0].imei+'</li>\n'+
                                '<li class="list-group-item " style="font-size:8px;padding:5px 5px;background-color:rgba(0,0,0,0.2);">经度:'+history[0].jd+'</li>\n'+
                                '<li class="list-group-item " style="font-size:8px;padding:5px 5px;background-color:rgba(0,0,0,0.2);">纬度:'+history[0].wd+'</li>\n'+
                                '<li class="list-group-item " style="font-size:8px;padding:5px 5px;color:green;background-color:rgba(0,0,0,0.2);">状态:'+convertStatus+'</li>\n'+
                                '</ul>'
                    });
                    flag = !flag;
                }else{
                    if(history[0].carStatus==0){
                        console.log('隐藏函数已被执行');
                        // polyline.hide(); 任何情况下，路径都将会被显示
                        markCpy.stopMove();
                        markCpy.hide();
                    }
                    mark.setLabel({
                        content:''
                    });
                    flag = !flag;
                }
            });

        },
        






    },


}
</script>

<style scoped>


    #container{
        padding:0px;
        margin: 0px;
        width: 100%;
        height: 80vh;
    }

/* .info-card{
    background-color: rgb(255, 255, 255,0.2);
    color:white;
} */
#information{
    background: rgb(255 255 255 / 15%) !important;
    color:white !important;
}

.carInfo{
    padding:5px 5px;
}

</style>

