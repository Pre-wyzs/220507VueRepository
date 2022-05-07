<template>

  <div class="all">
      <header>
          <h1>智慧工地项目</h1>
          <div class="showTime"></div>

          <!--全屏的提示-->
          <div class="fullscreen" @click="zoomControl()">
              <div>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-maximize text-white"><path d="M8 3H5a2 2 0 0 0-2 2v3m18 0V5a2 2 0 0 0-2-2h-3m0 18h3a2 2 0 0 0 2-2v-3M3 16v3a2 2 0 0 0 2 2h3"></path></svg>I
              </div>
              <div class="tips" id="tip">全屏</div>
          </div>
      </header>

    <!--页面主体盒子-->
    <section class="mainbox">
        <div class="column">
            <!-- 视频监控模块 -->
            <div class="panel module_spjk">
                <!-- 模块小标题 -->
                <h1>
                    <div>
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-list text-white"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg>
                    </div>
                    <div class="func_words">视频监控</div>
                    <router-link to="/home/video/info" class="func_link">
                      详情>>
                    </router-link>

                </h1>
                <!-- 真正存放视频的窗口 -->
                <div class="container-fluid">
                    <div class="row justify-content-around"> 
                        <div class="col-sm-5 " style="width:150px;height:150px;color:white;padding:0px;border:1px solid white;margin-top:20px;overflow:hidden;">
                          <camera></camera>
                        </div>
                        <div class="col-sm-5 " style="width:150px;height:150px;color:white;padding:0px;border:1px solid white;margin-top:20px;overflow:hidden;">
                          <camera></camera>
                        </div>
                        <div class="w-100"></div>
                        <div class="col-sm-5 " style="width:150px;height:150px;color:white;padding:0px;border:1px solid white;margin:30px 0px;overflow:hidden;">
                          <camera></camera>
                        </div>
                        <div class="col-sm-5 " style="height:150px;width:150px;color:white;padding:0px;border:1px solid white;margin:30px 0px;overflow:hidden;">
                          <camera></camera>
                        </div>
                    </div>

                </div>

                <div class="panel-foot"></div>
            </div>
            <!-- 车辆管理模块 -->
            <div class="panel module_clgl">

                <!-- 模块小标题 -->
                <h1>
                    <div>
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-list text-white"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg>
                    </div>
                    <div class="func_words">车辆管理</div>

                    <router-link to="/home/car/pl" class="func_link">
                    详情>>
                    </router-link>

                </h1>


                <!-- 内容主体 -->
                <div class="in_out">
                    <!-- 车辆进入模块-->
                    <!-- 车辆进入标题 -->
                    <div class="in_title">
                        <div class="title">今日入场数</div>
                        <div class="wapper">
                            <div class="num">
                                <h3>{{numIn}}</h3>
                                <div class="foot"></div>
                            </div>
                        </div>
                    </div>


                    <div class="content">
                      <table class="table-hover table-sm table-condensed">
                        <thead>
                          <tr>
                              <th>时间</th>
                              <th>车牌</th>
                              <th>车队</th>
                              <th>状态</th>
                              <th>报警</th>
                          </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(item,index) in showersInConvert" :key="index">
                                <td>{{item.timeStamp}}</td>
                                <td>{{item.license}}</td>
                                <td>{{item.team}}</td>
                                <td>{{item.purpose}}</td>
                                <td>{{item.status}}</td>
                            </tr>
                        </tbody>
                      </table>
                    </div>
                </div>

                <div class="in_out">
                    <!-- 车辆出去模块 -->
                    <!-- 车辆出去标题 -->
                    <div class="in_title">
                        <div class="title">今日出场数</div>
                        <div class="wapper">
                            <div class="num">
                                <h3>{{numOut}}</h3>
                                <div class="foot"></div>
                            </div>
                        </div>
                    </div>
                    <div class="content">
                      <table class="table-hover table-sm table-condensed">
                        <thead>
                          <tr>
                              <th>时间</th>
                              <th>车牌</th>
                              <th>车队</th>
                              <th>状态</th>
                              <th>报警</th>
                          </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(item,index) in showersOutConvert" :key="index">
                                <td>{{item.timeStamp}}</td>
                                <td>{{item.license}}</td>
                                <td>{{item.team}}</td>
                                <td>{{item.purpose}}</td>
                                <td>{{item.status}}</td>
                            </tr>
                        </tbody>

                      </table>

                    </div>

                </div>



                <div class="panel-foot"></div>
            </div>
        </div>
        <div class="column">
          <!-- 这就是地图的模块了 -->
            <div class="map">
              <map-container></map-container>
            </div>

            <div class="bottom_module">
                <div class="panel module_plgl">
                    <!-- 模块小标题 -->
                    <h1>
                        <div>
                          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-list text-white"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg>
                        </div>
                        <div class="func_words">喷淋管理</div>

                        <router-link to="/home/car/pl" class="func_link">
                        详情>> 
                        </router-link>
                    </h1>
                    <!-- 具体内容 -->
                    <div class="content">
                        <div class="title">
                            <div class="title_num">今日喷淋数</div>
                            <div class="wapper">
                                <div class="num">
                                    <h3>{{num}}</h3>
                                    <div class="foot"></div>
                                </div>
                            </div>
                        </div>
                        <div class="data">
                          <table class="table-hover table-sm table-condensed">
                            <thead>
                              <tr>
                                  <th>时间</th>
                                  <th>车牌</th>
                                  <th>车队</th>
                                  <th>状态</th>
                                  <th>报警</th>
                              </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(item,index) in showersConvert" :key="index">
                                    <td>{{item.timeStamp}}</td>
                                    <td>{{item.license}}</td>
                                    <td>{{item.team}}</td>
                                    <td>{{item.purpose}}</td>
                                    <td>{{item.status}}</td>
                                </tr>
                            </tbody>
                          </table>
                        </div>
                    <div class="panel-foot"></div>
                  </div>
                </div>

                <div class="panel module_kqgl">
                    <!-- 模块小标题 -->
                    <h1>
                        <div>
                          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-list text-white"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg>
                        </div>
                        <div class="func_words">考勤管理</div>
                        <router-link to="/home/worker/sign" class="func_link">
                        详情>>
                        </router-link>
                    </h1>

                    <!-- 考勤管理图 -->
                    <div class="chart_kaoqing"></div>
                    <div class="panel-foot"></div>
                </div>

            </div>




        </div>
        <div class="column">
            <div class="panel">
                <!-- 模块小标题 -->
                <h1>
                    <div>
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-list text-white"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg>
                    </div>
                    <div class="func_words">尾气在线检测</div>
                      <router-link to="/home/exhaust/info" class="func_link">
                        详情>>
                      </router-link>
                </h1>

                <div class="chart_weiqi"></div>
                <div class="panel-foot"></div>
            </div>
            <div class="panel module_2">
                <!-- 模块小标题 -->
                <h1>
                    <div>
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-list text-white"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg>
                    </div>
                    <div class="func_words">空气质量检测</div>
                      <router-link to="/home/dust/info" class="func_link">
                        详情>>
                      </router-link>
                </h1>

                <!-- 空气质量检测图 -->
                <div class="chart_kongqi"></div>
                <div class="panel-foot"></div>
            </div>
            <div class="panel module_3">
                <h1>
                    <div >
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-list text-white"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg>
                    </div>
                    <div class="func_words">设备管理</div>
                      <router-link to="/home/device/info" class="func_link">
                      详情>>
                      </router-link>

                </h1>
                <!-- 设备管理模块的头部描述部分 -->
                <div class="sbms">
                    <!-- 正在运行的设备数量 -->
                    <div class="left">
                        <div class="run">
                            <div class="yx">
                                在线
                            </div>
                            <div class="num">
                              {{inline}}
                            </div>
                        </div>
                    </div>
                    <!-- 已经关闭的设备数量 -->
                    <div class="right">
                        <div class="closeN">
                            <div class="gb">
                                离线
                            </div>
                            <div class="num">
                              {{outline}}
                            </div>

                        </div>
                    </div>
                </div>

                <!-- 设备管理模块的主体内容显示部分-->
                <div class="sbqk">
                    <div class="hidden">
                          <table class="table-hover table-sm table-condensed">
                            <thead>
                              <tr>
                                  <th>时间</th>
                                  <th>设备名</th>
                                  <th>状态</th>
                              </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(item,index) in devicesConvert" :key="index" :class="devicesConvert[index].status=='在线'?'onLine':'outLine'">
                                    <td>{{item.time}}</td>
                                    <td>{{item.name}}</td>
                                    <td>{{item.status}}</td>
                                </tr>
                                
                            </tbody>
                          </table>
                        <!-- <table>
                            <tbody style="font-size:13px;">
                                <tr>
                                    <td>2022.2.13 11:34</td>
                                    <td>尾气检测设备</td>
                                    <td>在线</td>
                                </tr>
                                <tr>
                                    <td>2022.2.13 10:23</td>
                                    <td>空气检测设备</td>
                                    <td>在线</td>
                                </tr>
                                <tr>
                                    <td>2022.2.13 10:46</td>
                                    <td>水处理设备1</td>
                                    <td>在线</td>
                                </tr>
                                <tr>
                                    <td>2022.2.13 11:56</td>
                                    <td>水处理设备2</td>
                                    <td>在线</td>
                                </tr>
                            </tbody>
                        </table> -->

                    </div>
                </div>
                <div class="panel-foot"></div>
            </div>
        </div>
    </section>

  </div>

    
</template>
<script>
import axios from 'axios'
import {  createNamespacedHelpers } from 'vuex' //引入辅助函数
const { mapState, mapGetters, mapActions, mapMutations } = createNamespacedHelpers('indexMain');;
import {createWebsocket} from '../utils/websocketUtil'
import {signChartOptionFunc,dustChartOptionFunc,exhaustChartOptionFunc} from './index/js/echartOptionFunc'
import * as echarts from 'echarts'
import MapContainer from '../components/map/map_container.vue'
import Camera from './Camera.vue'


export default {
    name:'Index',
    components:{
      MapContainer,
      Camera
    },
    mounted(){
      let _this = this;
      //设定一个计时器，每个一秒钟就执行一次
      let dt = new Date();
      var y = dt.getFullYear();
      var mt = dt.getMonth() + 1;
      var day = dt.getDate();
      var h = dt.getHours();//获取时
      var m = dt.getMinutes();//获取分
      var s = dt.getSeconds();//获取秒
      let showTime = document.querySelector(".showTime");

      showTime.innerHTML = y + "年" + mt + "月" + day + "-" + h + "时" + m + "分" + s + "秒";


      setInterval(function(){
          dt = new Date();
          var y = dt.getFullYear();
          var mt = dt.getMonth() + 1;
          var day = dt.getDate();
          var h = dt.getHours();//获取时
          var m = dt.getMinutes();//获取分
          var s = dt.getSeconds();//获取秒

          showTime.innerHTML = y + "年" + mt + "月" + day + "-" + h + "时" + m + "分" + s + "秒";
      },1000);

      /*******************************************喷淋的初始化模块以及websokcet接口************************************************************************** */
      //初始化的时候传递5条数据
      this.asyncGetShowers({api:'http://localhost:8080/api/index/pl?purpose=&size=5',purpose:'所有'});
      this.asyncGetShowers({api:'http://localhost:8080/api/index/pl?purpose=1&size=3',purpose:'进场'});
      this.asyncGetShowers({api:'http://localhost:8080/api/index/pl?purpose=2&size=3',purpose:'出场'});
      //获取喷淋的数据
      this.asyncGetShowerNum('http://localhost:8080/api/index/pl/num');
      //并createWebsocket且建立实时的通讯

      const stocketPL = createWebsocket("IndexPL-Linking","ws://localhost:8080/ws/api/equipment");
      stocketPL.onmessage = function(evt){
        _this.asyncGetShowers({api:'http://localhost:8080/api/index/pl?purpose=&size=5',purpose:'所有'});
        _this.asyncGetShowers({api:'http://localhost:8080/api/index/pl?purpose=1&size=3',purpose:'进场'});
        _this.asyncGetShowers({api:'http://localhost:8080/api/index/pl?purpose=2&size=3',purpose:'出场'});
        //获取喷淋的数据
        _this.asyncGetShowerNum('http://localhost:8080/api/index/pl/num');
      }

      
      /*******************************************设备管理的初始化模块以及websocker接口************************************************************************** */
      this.asyncGetDevices('http://localhost:8080/api/index/device');

      const stocketDevice = createWebsocket("IndexDevice-Linking","ws://localhost:8080/ws/api/equipment");
      stocketDevice.onmessage = function(evt){
        _this.asyncGetDevices('http://localhost:8080/api/index/device');
      }

      /*******************************************签到的模块的接口************************************************************************** */
      this.getSignData();


      /*******************************************扬尘的模块************************************************************************** */
      this.getDustData();
      const stocketDust = createWebsocket("IndexDust-Linking","ws://localhost:8080/ws/api/equipment");
      stocketDust.onmessage = function(evt){
        _this.getDustData();
      }


      /*******************************************尾气的某块的忌口额************************************************************************** */
      this.getExhaustData();
      const stocketExhaust= createWebsocket("IndexExhaust-Linking","ws://localhost:8080/ws/api/equipment");
      stocketExhaust.onmessage = function(evt){
        _this.getExhaustData();
      }

    },
    data(){ 
      return {
        /***车辆喷淋的数据 */
        //喷淋的记录

      }
    },
    computed:{
        ...mapState(['showers','showersIn','showersOut','numIn','numOut','num','inline','outline']),
        ...mapGetters(['showersConvert','showersInConvert','showersOutConvert','devicesConvert']),
   },
    methods:{
      ...mapMutations(['setShowers']),
      ...mapActions(['asyncGetShowers','asyncGetShowerNum','asyncGetDevices']),

      zoomControl(){
        var tag = document.getElementById("tip").innerHTML
        if (tag == '全屏') {
            this.fullScreen();
            document.getElementById("tip").innerHTML = '退出';
            //@13并且隐藏 浏览器滚动条
            document.body.style.overflow = "hidden";
        } else {
            this.exitScreen();
            document.getElementById("tip").innerHTML = '全屏';
            //@13并且显示浏览器滚动条
            document.body.style.overflowY = "scroll";
        }
      },
      fullScreen(){
        //@10document就是整个html文档在js中的对象，document.documentElement就是整个文档的根节点，
        var el = document.documentElement;
        //@11.对于一个js对象，调用它的函数时不带(),就可以输出函数的定义，下面的意思是看看哪一个全屏函数存在，并把函数对象赋值给rfs
        var rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullscreen;
        //console.log(el.webkitRequestFullScreen)
        if (typeof rfs != "undefined" && rfs) {
            //@12.call就是调用rsf函数，作用于el上
            rfs.call(el);
        };
        return;
      },
      exitScreen(){
        if (document.exitFullscreen) {
            document.exitFullscreen();
        }
        else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
        }
        else if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen();
        }
        else if (document.msExitFullscreen) {
            document.msExitFullscreen();
        }
        if (typeof cfs != "undefined" && cfs) {
            cfs.call(el);
        }
      },

      //获取考勤的数据
      getSignData(){
        let _this = this;
        axios.get('http://localhost:8080/api/index/sign').then(function(response){
          let rets = response.data;
          let map = rets.data
          // console.log(map);
          let xData = [];
          Object.keys(map).forEach(key=>{
            xData.push(key);
          });

          let yData = [];
          Object.values(map).forEach(value=>{
            yData.push(value);
          });

          _this.drawSignData(xData,yData);

        },function(error){
        });



      },
      //画出考勤的图像
      drawSignData(xData,yData){
        var myChart = echarts.init(document.querySelector(".chart_kaoqing"));
        // console.log('画图函数已经执行了');
        const option = signChartOptionFunc(xData,yData);
        myChart.setOption(option);
        window.addEventListener('resize', function () {
            myChart.resize();
        });

      },

      //扬尘检测的图表
      getDustData(){
        let _this = this;
        axios.get('http://localhost:8080/api/index/dust?size=5').then(function(response){
          let rets = response.data;
          let data = rets.data
          // console.log(map);
          // console.log(data);
          let xData = [];
          let pm10 = [];
          let aqi = [];
          let pm25 = [];
          data.forEach(item=>{
            xData.push(item.timeStamp.slice(5).replaceAll('-','.'));
            pm10.push(item.pm10);
            aqi.push(item.aqi);
            pm25.push(item.pm25);
          });

         _this.drawDustData(xData.reverse(),pm10.reverse(),aqi.reverse(),pm25.reverse());
        },function(error){
        });
      },
      //画出扬尘图表的图
      drawDustData(xData,pm10,aqi,pm25){
        var myChart = echarts.init(document.querySelector(".chart_kongqi"));
        // console.log('画图函数已经执行了');
        const option = dustChartOptionFunc(xData,pm10,aqi,pm25);
        myChart.setOption(option);
        window.addEventListener('resize', function () {
            myChart.resize();
        });
      },
      
      //获取尾气的数据
      getExhaustData(){
        let _this = this;
        axios.get('http://localhost:8080/api/index/exhaust').then(function(response){
          let rets = response.data;
          let data = rets.data
          // console.log(map);
          console.log(data);
          let radarData = [];

          radarData.push(data.o3);
          radarData.push(data.hf);
          radarData.push(data.no);
          radarData.push(data.c6H6);
          radarData.push(data.so2);


          _this.drawExhaustData(radarData);
        },function(error){
        });



      },
      //画出尾气的图表
      drawExhaustData(radarData){
        var myChart = echarts.init(document.querySelector(".chart_weiqi"));
        // console.log('画图函数已经执行了');
        const option = exhaustChartOptionFunc(radarData);

        myChart.setOption(option);
        window.addEventListener('resize', function () {
            myChart.resize();
        });
      }



    }

}
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
/* 声明字体*/
.all{
  background: url(./index/img/bg.jpg) top center;
  line-height: 1.15;

}
@font-face {
  font-family: electronicFont;
  src: url(./index/font/DS-DIGIT.TTF);
}
/* body {
  background: url(./index/img/bg.jpg) top center;
  line-height: 1.15;
} */
header {
  height: 70px;
  background: url(./index/img/head_bg.png) no-repeat center;
}
h1 {
  position: relative;
  font-size: 22px;
  text-align: center;
  font-weight: bolder;
  color: #fff;
  line-height: 1.8;
}
.showTime {
  color: rgba(255, 255, 255, 0.7);
  font-size: 16px;
  top: 0px;
  text-align: center;
}
.fullscreen {
  position: absolute;
  display: flex;
  top: 15px;
  right: 100px;
  line-height: 16px;
}
/* .fullscreen .icon {
  background: url(./index/img/Full-Screen-20.png) no-repeat center;
  background-size: 100% 100%;
  width: 26px;
  height: 26px;
  flex: 1;
  margin-right: 5px;
} */
.fullscreen .tips {
  /* margin-top: 5px; */
  align-self: center;
  color: white;
}
.mainbox {
  min-width: 1024px;
  max-width: 1920px;
  margin: 0 auto;
  padding: 10px 10px 0px;
  display: flex;
}
.mainbox .column:nth-child(1) {
  flex: 3;
}
.mainbox .column:nth-child(3) {
  flex: 3;
}
.mainbox .column:nth-child(2) {
  flex: 6;
  margin: 0 10px 15px;
}
.mainbox .panel {
  position: relative;
  height: 250px;
  border: 1px solid rgba(25, 186, 139, 0.17);
  background: url(./index/img/line\(1\).png);
  padding: 0 15px 40px 15px;
  margin-bottom: 15px;
  background-color: rgba(255, 255, 255, 0.1);
}
.mainbox .panel::before {
  position: absolute;
  top: 0;
  left: 0;
  width: 10px;
  height: 10px;
  content: "";
  border-top: 2px solid #02a6b5;
  border-left: 2px solid #02a6b5;
}
.mainbox .panel::after {
  position: absolute;
  top: 0;
  right: 0;
  width: 10px;
  height: 10px;
  content: "";
  border-top: 2px solid #02a6b5;
  border-right: 2px solid #02a6b5;
}
.mainbox .panel .panel-foot {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
}
.mainbox .panel .panel-foot::before {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 10px;
  height: 10px;
  content: "";
  border-bottom: 2px solid #02a6b5;
  border-left: 2px solid #02a6b5;
}
.mainbox .panel .panel-foot::after {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 10px;
  height: 10px;
  content: "";
  border-right: 2px solid #02a6b5;
  border-bottom: 2px solid #02a6b5;
}
.mainbox .panel h1 {
  display: flex;
  align-items: center;
  height: 30px;
  line-height: 1.2;
  font-size: 16px;
  font-weight: 380;
  padding-bottom: 5px;
  border-bottom: 1px solid #02a6b5;
  margin-bottom: 5px;
}
.mainbox .panel h1 .func_words {
  flex: 6;
  font-size: 14px;
  text-align: left;
  margin-left:5px;

}
.mainbox .panel h1 .func_link {
  flex: 4;
  text-align: right;
  font-size: 6px;
  color: #02a6c0;
}
.mainbox .panel .chart_weiqi {
  height: 240px;
}
.mainbox .panel .chart_kongqi {
  height: 240px;
}
.mainbox .module_spjk {
  height: 430px;
}
.mainbox .module_clgl {
  height: 335px;
}
.mainbox .module_clgl .in_out {
  height: 150px;
}
.mainbox .module_clgl .in_out .in_title {
  display: flex;
  align-items: center;
  height: 25px;
  font-size: 14px;
}
.mainbox .module_clgl .in_out .in_title .title {
  /* flex: 1; */
  color: white;
  line-height: 135%;
}
.mainbox .module_clgl .in_out .in_title .wapper {
  /* flex: 3; */
  position: relative;
  margin-left:10px;
}
.mainbox .module_clgl .in_out .in_title .wapper .num {
  width: 35px;
  height: 25px;
  /* position: absolute; */
  color: #FFEB7B;
  font-family: electronicFont;
  text-align: center;
}
.mainbox .module_clgl .in_out .in_title .wapper .num h3 {
  line-height: 125%;
  margin-right: 3px;
  font-size:20px;
}
.mainbox .module_clgl .in_out .in_title .wapper .num::before {
  position: absolute;
  top: 0;
  left: 0;
  width: 5px;
  height: 5px;
  content: "";
  border-top: 2px solid #02a6b5;
  border-left: 2px solid #02a6b5;
}
.mainbox .module_clgl .in_out .in_title .wapper .num::after {
  position: absolute;
  top: 0;
  right: 0;
  width: 5px;
  height: 5px;
  content: "";
  border-top: 2px solid #02a6b5;
  border-right: 2px solid #02a6b5;
}
.mainbox .module_clgl .in_out .in_title .wapper .num .foot {
  background-color: purple;
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
}
.mainbox .module_clgl .in_out .in_title .wapper .num .foot::before {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 5px;
  height: 5px;
  content: "";
  border-bottom: 2px solid #02a6b5;
  border-left: 2px solid #02a6b5;
}
.mainbox .module_clgl .in_out .in_title .wapper .num .foot::after {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 5px;
  height: 5px;
  content: "";
  border-right: 2px solid #02a6b5;
  border-bottom: 2px solid #02a6b5;
}
.mainbox .module_clgl .in_out .content {
  height: 110px;
  margin-top: 5px;
  border: 1px solid #02a6b5;
  overflow: hidden;
}
.mainbox .module_clgl .in_out .content .data_title {
  display: flex;
  height: 20px;
  justify-content: space-around;
  color: white;
  text-align: center;
  font-size: 13px;
  font-weight: bold;
  background-color: #1C4996;
}

.mainbox .module_clgl .in_out .content table thead{
  /***这个选项小道一定的首就不会在校了 */
  /* height: 8px; */
  background-color: #1C4996;
}

/* th{
  padding:0px 0px;
} */
.mainbox .module_clgl .in_out .content table thead tr th{
  padding: 0px 0px;
  font-size:13px;
  font-weight: bolder;;
}

.mainbox .module_clgl .in_out .content table tbody tr td{
  padding: 0px 0px;
  font-size:13px;
}

.mainbox .module_clgl .in_out .content table tbody tr:hover{
  color:#3eff00;
}

.mainbox .module_clgl .in_out .content .data_content {
  overflow: hidden;
}
.mainbox .module_clgl .in_out .content table {
  width: 100%;
  color: white;
  text-align: center;
  font-size: 15px;
  border-spacing: 0;
}
.mainbox .module_clgl .in_out .content table tbody {
  margin-top: -30px;
  color: #4C9BFD;
}
.mainbox .module_clgl .in_out .content table tr {
  line-height: 2;
}
.mainbox .map {
  position: relative;
  height: 535px;
  margin-bottom: 15px;
  border:1px solid blue;
  overflow: hidden;
}
.mainbox .map .map1 {
  position: absolute;
  width: 518px;
  height: 518px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: url(./index/img/map.png) center;
  background-size: 100% 100%;
}
.mainbox .map .map2 {
  position: absolute;
  width: 595px;
  height: 595px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: url(./index/img/lbx.png) center;
  background-size: contain;
  animation: rotated1 15s linear infinite;
  opacity: 0.7;
}
@keyframes rotated1 {
  from {
    transform: translate(-50%, -50%) rotate(0deg);
  }
  to {
    transform: translate(-50%, -50%) rotate(360deg);
  }
}
.mainbox .map .map3 {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: url(./index/img/jt.png) center;
  background-size: contain;
  width: 566px;
  height: 566px;
  animation: rotated2 15s linear infinite;
}
@keyframes rotated2 {
  from {
    transform: translate(-50%, -50%) rotate(360deg);
  }
  to {
    transform: translate(-50%, -50%) rotate(0deg);
  }
}

/* 底部模块的css样式 -4*/
.mainbox .bottom_module {
  display: flex;
}
.mainbox .bottom_module .panel {
  /* flex: 1; */
  height: 230px;
}
.mainbox .bottom_module .module_plgl {
  flex:1;
  margin-right: 5px;
}
.mainbox .bottom_module .module_plgl .content .title {
  display: flex;
  align-items:center;
  height: 25px;
  font-size: 14px;
}
.mainbox .bottom_module .module_plgl .content .title .title_num {
  color: white;
  line-height: 135%;
}
.mainbox .bottom_module .module_plgl .content .title .wapper {
  position: relative;
  margin-left: 10px;;
}
.mainbox .bottom_module .module_plgl .content .title .wapper .num {
  width: 35px;
  height: 25px;
  /* position: absolute; */
  color: #FFEB7B;
  font-family: electronicFont;
  text-align: center;
}
.mainbox .bottom_module .module_plgl .content .title .wapper .num h3 {
  line-height: 125%;
  margin-right: 3px;
  height: 25px;
  font-size:20px;
}
.mainbox .bottom_module .module_plgl .content .title .wapper .num::before {
  position: absolute;
  top: 0;
  left: 0;
  width: 5px;
  height: 5px;
  content: "";
  border-top: 2px solid #02a6b5;
  border-left: 2px solid #02a6b5;
}
.mainbox .bottom_module .module_plgl .content .title .wapper .num::after {
  position: absolute;
  top: 0;
  right: 0;
  width: 5px;
  height: 5px;
  content: "";
  border-top: 2px solid #02a6b5;
  border-right: 2px solid #02a6b5;
}
.mainbox .bottom_module .module_plgl .content .title .wapper .num .foot {
  background-color: purple;
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
}
.mainbox .bottom_module .module_plgl .content .title .wapper .num .foot::before {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 5px;
  height: 5px;
  content: "";
  border-bottom: 2px solid #02a6b5;
  border-left: 2px solid #02a6b5;
}
.mainbox .bottom_module .module_plgl .content .title .wapper .num .foot::after {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 5px;
  height: 5px;
  content: "";
  border-right: 2px solid #02a6b5;
  border-bottom: 2px solid #02a6b5;
}
.mainbox .bottom_module .module_plgl .content .data {
  height: 155px;
  margin-top: 5px;
  border: 1px solid #02a6b5;
  overflow: hidden;
}
.mainbox .bottom_module .module_plgl .content .data .data_content {
  overflow: hidden;
}

.mainbox .bottom_module .module_plgl .content .data table {
  width: 100%;
}
.mainbox .bottom_module .module_plgl .content .data table thead{
  background-color: #1c4996;
}
.mainbox .bottom_module .module_plgl .content .data table thead tr th{
  height: 25px;
  padding:0px 0px;
  font-size:14pX;
  font-weight: bolder;
  color:white;
  text-align: center;
}

.mainbox .bottom_module .module_plgl .content .data table tbody tr td{
  height: 25px;
  padding:0px 0px;
  font-size:13pX;
  color:#3eff00;
  text-align: center;
}

.mainbox .bottom_module .module_plgl .content .data table tbody tr:hover{
  color:#02a6b5;
}

.mainbox .bottom_module .module_kqgl {
  flex:1;
  margin-left: 5px;
}
.mainbox .bottom_module .module_kqgl .chart_kaoqing {
  height: 212px;
}
.sbms {
  height: 35px;
  display: flex;
  margin-bottom: 8px;
}
.sbms .left {
  flex: 1;
  position: relative;
}
.sbms .left .run {
  position: absolute;
  width: 50%;
  height: 35px;
  left: 25%;
}
.sbms .left .run .yx {
  text-align: center;
  background: url(./index/img/Clear\ Sky.jpg) top center no-repeat;
  color: #91CC75;
  /* line-height: 28px; */
}
.sbms .left .run .num {
  font-family: electronicFont;
  text-align: center;
  /* font-size: 22px; */
  color: #FFEB7B;
}
.sbms .left .run .num::before {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 5px;
  height: 5px;
  content: "";
  border-bottom: 2px solid #02a6b5;
  border-left: 2px solid #02a6b5;
}
.sbms .left .run .num::after {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 5px;
  height: 5px;
  content: "";
  border-bottom: 2px solid #02a6b5;
  border-right: 2px solid #02a6b5;
}
.sbms .right {
  flex: 1;
  position: relative;
}
.sbms .right .closeN {
  position: absolute;
  width: 50%;
  height: 35px;
  left: 25%;
}
.sbms .right .closeN .gb {
  text-align: center;
  background: url(./index/img/Clear\ Sky.jpg) top center no-repeat;
  color: #FF7070;
  /* line-height: 28px; */
}
.sbms .right .closeN .num {
  font-family: electronicFont;
  text-align: center;
  /* font-size: 22px; */
  color: #FFEB7B;
}
.sbms .right .closeN .num::before {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 5px;
  height: 5px;
  content: "";
  border-bottom: 2px solid #02a6b5;
  border-left: 2px solid #02a6b5;
}
.sbms .right .closeN .num::after {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 5px;
  height: 5px;
  content: "";
  border-bottom: 2px solid #02a6b5;
  border-right: 2px solid #02a6b5;
}
.sbqk {
  border: 1px solid #02a6b5;
  height: 160px;
   overflow: hidden; 

  }

.sbqk .hidden {
  width: 106%;
  height: 150px;
  overflow-y: scroll;
}
.sbqk table {
  width: 100%;
  color: white;
  text-align: center;
  font-size: 13px;
  border-spacing: 0;
}
.sbqk table thead {
  width: 100%;
  background-color: #1C4996;
}
.sbqk table thead th td{ 
  height: 10px;
}
.sbqk table tbody tr:hover{ 
  color: #FFEB7B;
}


.sbqk table tbody {
  color: #4C9BFD;
}
.sbqk table tr {
  line-height: 2;
}
.onLine{
  color:#3eff00;
}
.outLine{
  color:#FF7070;
}
/**视频监控的css样式，因为是最后添加的了，所以我也不管它是不是很好看了，直接用Id添加就行了 */
</style>
