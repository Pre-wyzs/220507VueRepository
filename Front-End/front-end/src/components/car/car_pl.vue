<template>
    <div class="pl_chart" @click="resize()" ref="chart"></div>
    
</template>
<script>
import axios from 'axios'
import * as echarts from 'echarts'
import plOptionFunc from './plOptionFunc'

export default {
    name:'CarPL',
    mounted(){
        this.getData();
                        // console.log("已从后端获取数据");
        var socket = new WebSocket("ws://localhost:8080/ws/api/equipment");
        let _this = this;
        socket.onopen = function(){
            console.log('websocket linked');
            //成功建立链接后发送这个连接的唯一名称
            socket.send("PL-Linking");
        }
        /**在这个钩子函数中开启socket的监听事件 ：当服务器的session向它发送消息的时候，自然就会调用到这个回调函数了*/
        socket.onmessage = function(evt){
            //服务端返回的就是字符串，得通过evt.data来获取
            let retStr = evt.data;
            console.log('返回函数已经触发了');

            if(retStr == 'Status_updated'){
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
    data(){
        return{
            plChart:null,  
        }
    },
    computed:{

    },
    methods:{
        resize(){
            this.plChart.resize();
        },
        async getData(){
            const {data:ret} = await axios.get("http://localhost:8080/api/car/pl/chart");
            // console.log(ret.data);

            let xData = [];
            let yDataIn = [];
            let yDataOut = [];

            Object.keys(ret.data).forEach(item=>{
                xData.push(item);
            });
            Object.values(ret.data).forEach(item=>{
                yDataIn.push(item[0]);
                yDataOut.push(item[1]);
            });
            console.log(xData,yDataIn,yDataOut);

            this.drawChart(xData,yDataIn,yDataOut);

        },
        drawChart(xData,yDataIn,yDataOut){
            let plChart= echarts.init(this.$refs.chart);
            this.plChart = plChart;
            let option = plOptionFunc(xData,yDataIn,yDataOut);
            plChart.setOption(option);

            // window.onresize = function () {
            //     _this.plChart.resize();
            // };

            window.addEventListener('resize',function(){
                plChart.resize();
            })

        }
    }
}
</script>
<style scoped>
.pl_chart{
    background: rgb(0 0 0 / 45%);
    border-radius: 5px;
    padding: 10px;
    height: 100%;

}
</style>
