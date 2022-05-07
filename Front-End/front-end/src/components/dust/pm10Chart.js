
function pm10OptionFunc(xData,yData){
    const option = {

        title: {
          text: "PM10检测实时图",
          left: "center",
          top: 5,
          textStyle: {
            color: "blue",
          },
        },
        toolbox: { feature: { saveAsImage: { iconStyle: { borderColor: "white", }, }, restore: { iconStyle: { borderColor: "white", }, },           }, }, 
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            show: true,
            borderWidth: 1,
            borderColor: 'rgb(0 0 0 / 30%)',
            top: '20%',
            bottom: '20%',
            left: '13%',
            right: '10%'
        },
        dataZoom: {
            type: 'slider',
            xAxisIndex: 0,
            top: '92%',
            height: 15,
            start: 0,
            end: 100
        },

        xAxis: {
          type: "category",
          data: xData,
          boundaryGap: false,
            axisLine: {
                show: false,
                lineStyle: {
                    color: '#FFEB3B',
                },
            },
            axisLabel: {
                margin: 18,
                fontWeight: 'bolder'
            },
            axisTick:{
                show:false
            }
        },
        yAxis: {
            name:'μg/m³',
            nameTextStyle:{
                fontWeight:'bolder',
                align:'right',
            },
          type: "value",
        //   scale: true,
            splitLine: {
                show: false,
            },
            axisLine: {
                show: false,
                lineStyle: {
                    color: '#03A9F4'
                }
            },
            axisLabel: {
                margin: 15,                
                // formatter: '{value}μg/m³',
                fontWeight: 'bolder'
            },
        },
        series: [
          {
            name: "PM10指数",
            type: "line",
            data: yData,
            markLine:{
                silent: true,
                lineStyle: {
                    color: '#FFEB3B'
                },
                symbol:['none','none'],
                data: [ { yAxis: 100 }],
            },
            smooth:true,
            areaStyle:{
                color:'#BE4D43',
                opacity:0.1
            },
            lineStyle:{
                color:'#BE4D43'
            }
          },
        ],       
       


    }
    return option
}

export default pm10OptionFunc;