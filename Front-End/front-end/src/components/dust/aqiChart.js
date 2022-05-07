

function aqiOptionFunc(xData,yData){
    const option = {

        title: {
          text: "AQI实时检测图",
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
            left: '8%',
            right: '15%'
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
        visualMap: {
            top: 50,
            right: 10,
            textStyle:{
                color:'#0DC116',
                fontWeight:'bolder',
            },
            pieces: [
            {
                gt: 0,
                lte: 50,
                color: '#93CE07'
            },
            {
                gt: 50,
                lte: 100,
                color: '#FBDB0F'
            },
            {
                gt: 100,
                lte: 150,
                color: '#FC7D02'
            },
            {
                gt: 150,
                lte: 200,
                color: '#FD0100'
            },
            {
                gt: 200,
                lte: 300,
                color: '#AA069F'
            },
            {
                gt: 300,
                color: '#AC3B2A'
            }
            ],
            outOfRange: {
                color: '#999'
            }
        },
        series: [
          {
            name: "AQI指数",
            type: "line",
            data: yData,
            markLine:{
                silent: true,
                lineStyle: {
                    color: '#FFEB3B'
                },
                symbol:['none','none'],
                
                data: [ { yAxis: 50 }, { yAxis: 100 }, { yAxis: 150 }, { yAxis: 200 }, { yAxis: 300 }],

            }
          },
        ],       

    }

    return option

}

export default aqiOptionFunc;

