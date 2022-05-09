<template>
  <div class="lineChart" @click="resize()"></div>
  <div class="bottom">
    <div class="pieChart" @click="resize()">饼状图</div>
    <div class="radarChart" @click="resize()">雷达图</div>
  </div>
</template>
<script>
//导入echarts
import * as echarts from "echarts";
import test,{exhaustOptionFunc,partOptionFunc,radarOptionFunc} from "./chartOptionFuncs"
import axios from 'axios'


export default {
  name: "ExhaustInfo",
  //创建完对象的钩子函数
  created() {

    // this.asyncGetExhausts("http://localhost:8080/api/exhaust/day");

    //实时获取数据部分往后做。
    // console.log(this.exhausts); //居然是1个，应该是异步方法导致没有更新数据就把原来的数据给差UN过来了

  },
  //mounted完成后才能通过docuemt domjs获取到元素
  mounted() {
    this.getData();

        var socket = new WebSocket("ws://localhost:8080/ws/api/equipment");
        let _this = this;
        socket.onopen = function(){
            console.log('websocket linked');
            //成功建立链接后发送这个连接的唯一名称
            socket.send("Exhaust-Linking");
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
      exhuastChart:null,
      pieChart:null,
      radarChart:null,
    };
  },
  // watch:{
  //   containerWidth(newData,oldData){
  //   },
  //   containerHeight(newData,oldData){
  //   }
  // },
  computed: {

  },
  methods: {
    resize(){
      this.exhuastChart.resize();
      this.pieChart.resize();
      this.radarChart.resize();
    },
    //从后端获取数据：这个核心的函数解决了之前vuex仓储没有解决的问题
   async getData(){
      const {data: ret} = await axios.get("http://localhost:8080/api/exhaust/day");
      //更新本地的数据记录
      ret.data.reverse();  //数据颠倒一下，把最近时间的排到最后比较合理
      // console.log(exhausts);

      //更新所有option中需要的数据
      this.drawChart(this.updateXData(ret.data),
                     this.updateYDataObj(ret.data),
                     this.updatePieData(ret.data),
                     this.updateRadarData(ret.data) 
                    );
    },

    //渲染图表
    drawChart(xData,yDataObj,pieData,radarData){
      //实时监控图

      // console.log(this._exhausts);这里又获取不到了...无语猫猫
      let exhaustChart = echarts.init(document.querySelector(".lineChart"));
      this.exhuastChart = exhaustChart;
      let option1 = exhaustOptionFunc(xData, yDataObj);
      exhaustChart.setOption(option1);

      //成分监控图
      let percentChart = echarts.init(document.querySelector(".pieChart"));
      this.pieChart = percentChart;
      let option2 = partOptionFunc(pieData);
      percentChart.setOption(option2);

      //阈值监控图
      let radarChart = echarts.init(document.querySelector(".radarChart"));
      this.radarChart = radarChart;
      let option3 = radarOptionFunc(radarData);
      radarChart.setOption(option3);



      //解决F11的放大图片不放大的问题：
      //因为F11在放大的时候是可以命中的，但是缩小的时候就命中不到了.
      //在这个里面开启图表的联动效果和自适应性的事件监听。
       //坐标轴事件触发联动效果
      exhaustChart.on("updateAxisPointer", function (event) {
        const xAxisInfo = event.axesInfo[0];
        // console.log(xAxisInfo);
        if (xAxisInfo) {
          pieData[0].value = yDataObj.SO2[xAxisInfo.value];
          pieData[1].value = yDataObj.NO[xAxisInfo.value];
          pieData[2].value = yDataObj.O3[xAxisInfo.value];
          pieData[3].value = yDataObj.HF[xAxisInfo.value];
          pieData[4].value = yDataObj.C6H6[xAxisInfo.value];

          radarData[0] = yDataObj.SO2[xAxisInfo.value];
          radarData[1] = yDataObj.NO[xAxisInfo.value];
          radarData[2] = yDataObj.O3[xAxisInfo.value];
          radarData[3] = yDataObj.HF[xAxisInfo.value];
          radarData[4] = yDataObj.C6H6[xAxisInfo.value];

          option2 = partOptionFunc(pieData);
          percentChart.setOption(option2);

          option3 = radarOptionFunc(radarData);
          radarChart.setOption(option3);

        }
      });

      //@6当浏览器改变大小的时候，图表也跟着改变大小
      window.onresize = function () {
        // console.log('浏览器大小改变了');
        // let container = document.querySelector('.lineChart');
        // let width = container.offsetWidth;
        // let height = container.offsetHeight;
        // console.log('宽是:'+width + '高是：' + height);
        // console.log('页面大小改变了捏');
        exhaustChart.resize();
        percentChart.resize();
        radarChart.resize();
      };


    },

    //返回x轴的时间刻度数据
    updateXData(exhausts){
        let xData = [];
        for(let i=0;i < exhausts.length;i++){
            xData.push(exhausts[i].timeStamp);
        }
        return xData;
    },

    //返回series的各个项目的数据
    updateYDataObj(exhausts){
        let yDataObj = {SO2:[],NO:[],O3:[],HF:[],C6H6:[]};

        for(let i=0;i< exhausts.length;i++){
            yDataObj.SO2.push(exhausts[i].so2);
            yDataObj.NO.push(exhausts[i].no);
            yDataObj.O3.push(exhausts[i].o3);
            yDataObj.HF.push(exhausts[i].hf);
            yDataObj.C6H6.push(exhausts[i].c6H6);
        }

        return yDataObj;
    },

    updatePieData(exhausts){
        let pieData = [];
        let last = exhausts.length-1;

        pieData.push({name:'SO2',value:exhausts[last].so2});
        pieData.push({name:'NO',value:exhausts[last].no});
        pieData.push({name:'O3',value:exhausts[last].o3});
        pieData.push({name:'HF',value:exhausts[last].hf});
        pieData.push({name:'C6H6',value:exhausts[last].c6H6});

        return pieData;
    },

    updateRadarData(exhausts){
        let radarData = [];
        // console.log(this._exhausts);这里也确实获取到了exhausts的值
        let last = exhausts.length-1;

        radarData.push(exhausts[last].so2);
        radarData.push(exhausts[last].no);
        radarData.push(exhausts[last].o3);
        radarData.push(exhausts[last].hf);
        radarData.push(exhausts[last].c6H6);

        return radarData;
    },


  },
};
</script>

<style scoped>
.lineChart {
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
.pieChart {
  background: rgb(0 0 0 / 45%);
  width: 49%;
  border-radius: 10px;
  padding: 5px;
}

.radarChart {
  background: rgb(0 0 0 / 45%);
  width: 49%;
  border-radius: 10px;
  padding: 5px;
}


</style>