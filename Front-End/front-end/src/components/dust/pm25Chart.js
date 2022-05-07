function pm25OptionFunc(xData,yData){
    const option = {
        title: {
          text: "PM2.5检测实时图",
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
                fontWeight: 'bolder'
            },
        },
        series: [
          {
            name: "PM2.5指数",
            type: "line",
            data: yData,
            markLine:{
                silent: true,
                lineStyle: {
                    color: '#FFEB3B'
                },
                symbol:['none','none'],
                data: [ { yAxis: 50}],
            },
            smooth:true,
            areaStyle:{
                color:'#9FB85B',
                opacity:0.1
            },
            lineStyle:{
                color:'#9FB85B'
            }
          },
        ],       
       



    }

    return option;
}

export default pm25OptionFunc;
