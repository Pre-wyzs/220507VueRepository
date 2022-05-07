<template>
  <div class="AQI_Chart" @click="resize()" ref="AQI"></div>
  <div class="bottom">
    <div class="PM10_Chart" ref="PM10">PM10的图</div>
    <div class="PM25_Chart" ref="PM25">PM2.5的图</div>
  </div>
</template>
<script>
import * as echarts from "echarts";
import axios from "axios";
import aqiOptionFunc from "./aqiChart"
import pm10OptionFunc from "./pm10Chart"
import pm25OptionFunc from "./pm25Chart"


export default {
  name: "DustInfo",
  mounted() {
      this.getData();
        var socket = new WebSocket("ws://localhost:8080/ws/api/equipment");
        let _this = this;
        socket.onopen = function(){
            console.log('websocket linked');
            //成功建立链接后发送这个连接的唯一名称
            socket.send("Dust-Linking");
        }

        /**在这个钩子函数中开启socket的监听事件 ：当服务器的session向它发送消息的时候，自然就会调用到这个回调函数了*/
        socket.onmessage = function(evt){
            //服务端返回的就是字符串，得通过evt.data来获取
            let retStr = evt.data;

            if(retStr == 'Status_Updated'){
                //重新更新页面捏，不是从websocket接口中直接拿数据，有点捞捏!
              console.log("消息已送到");
                _this.getData();
            }

        }

        socket.onclose = function(){
            console.log('websocket closed');
        }

        socket.error = function(){
            console.log('websocket error');
        }

  },
  data() {
    return {
      AQI: null,
      PM10: null,
      PM25: null,
    };
  },
  computed: {},
  methods: {
    //渲染图表
    async getData(){
        const {data: ret} = await axios.get("http://localhost:8080/api/dust/all");
        //更新本地的数据记录
        let dusts = ret.data;  //数据颠倒一下，把最近时间的排到最后比较合理
        dusts.reverse();
        console.log(ret.data);

        this.drawCharts(this.updateXData(dusts),
                        this.updateYDataAQI(dusts),
                        this.updateYDataPM10(dusts),
                        this.updateYDataPM25(dusts)
                        );

    },


    drawCharts(xData,y1,y2,y3) {

    //   let xData = [ "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"];
    //   let yData = [ 57, 100,180, 210, 280, 300, 320, 330, 200, 150, 140, 100];

      let AQI_Chart = echarts.init(this.$refs.AQI);
      this.AQI = AQI_Chart;
      let option1 = aqiOptionFunc(xData,y1);
      AQI_Chart.setOption(option1);



        let PM10_Chart = echarts.init(this.$refs.PM10);
        this.PM10 = PM10_Chart;
        let option2 = pm10OptionFunc(xData,y2);
        PM10_Chart.setOption(option2);

        let PM25_Chart = echarts.init(this.$refs.PM25);
        this.PM25 = PM25_Chart;
        let option3 = pm25OptionFunc(xData,y3);
        PM25_Chart.setOption(option3);
    
      //@6当浏览器改变大小的时候，图表也跟着改变大小
      window.onresize = function () {
        AQI_Chart.resize();
        PM10_Chart.resize();
        PM25_Chart.resize();
      };
    },

    //返回x轴的时间刻度数据
    updateXData(dusts){
        let xData = [];
        for(let i=0;i < dusts.length;i++){
            xData.push(dusts[i].timeStamp);
        }
        return xData;
    },

    updateYDataAQI(dusts){
        let yData = [];
        for(let i=0;i < dusts.length;i++){
            yData.push(dusts[i].aqi);
        }
        return yData;
    },

    updateYDataPM10(dusts){
        let yData = [];
        for(let i=0;i < dusts.length;i++){
            yData.push(dusts[i].pm10);
        }
        return yData;
    },

    updateYDataPM25(dusts){
        let yData = [];
        for(let i=0;i < dusts.length;i++){
            yData.push(dusts[i].pm25);
        }
        return yData;
    },



    resize() {
      this.AQI.resize();
      this.PM10.resize();
      this.PM25.resize();
    },
  },
};
</script>

<style scoped>
.AQI_Chart {
  background: rgb(0 0 0 / 45%);
  height: 50%;
  border-radius: 10px;
  padding: 5px;
}
.bottom {
  display: flex;
  margin: 20px 0 0 0;
  height: 48%;
  justify-content: space-between;
}
.PM10_Chart {
  background: rgb(0 0 0 / 45%);
  width: 49%;
  border-radius: 10px;
  padding: 5px;
}

.PM25_Chart {
  background: rgb(0 0 0 / 45%);
  width: 49%;
  border-radius: 10px;
  padding: 5px;
}
</style>