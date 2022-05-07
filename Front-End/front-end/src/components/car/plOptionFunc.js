
import * as echarts from 'echarts'
function plOptionFunc(xData,yDataIn,yDataOut){
    const option = {
        // backgroundColor: '#0f375f',
        title: {
            text: '车辆喷淋实时监控',
            left: 'center',
            top: 5,
            textStyle: {
                color: 'blue'
            }
        },
        toolbox: {
            feature: {
                saveAsImage: {
                    iconStyle: {
                        borderColor: 'white'
                    }
                },  //开启导出图片的功能
                restore: {
                    iconStyle: {
                        borderColor: 'white'
                    }
                }  //开启重置的功能
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow',
                label: {
                        show: true
                }
            }
        },
        dataZoom: {
            type: 'slider',
            xAxisIndex: 0,
            height: 15,
            start: 0,
            end: 100
        },
        xAxis:{
            type:'category',
            axisLine: {
                show: true,
                lineStyle: {
                    color: '#FFEB3B',
                },
            },
            axisLabel: {
                margin: 8,
                fontWeight: 'bolder'
            },
            data:xData
        },
        yAxis:{
            type:'value',
            axisLine: {
                show: false,
                lineStyle: {
                    color: '#03A9F4'
                }
            },
            axisLabel: {
                margin: 15,
                formatter: '{value}次',
                fontWeight: 'bolder'
            },
            splitLine: {
                show: false,
            }
        },
        series:[
            {
                name:'入场喷淋',
                type:'bar',
                barWidth: 15,
                label:{
                    show:true,
                    color:'#14c8d4',
                    fontWeight:'bolder',
                    position: 'top'
                },
                itemStyle: {
                    borderRadius: 10,
                    //渐变色：https://blog.csdn.net/weixin_44897255/article/details/96877562
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    { offset: 0, color: '#14c8d4' },
                    { offset: 1, color: '#43eec6' }
                    ])
                },
                barGap:'80%',  //不同柱子之间的间距：30是指柱子本身宽度的30%；
                data:yDataIn

            },{
                name:'出场喷淋',
                type:'bar',
                barWidth: 15,
                label:{
                    show:true,
                    color:'#66ff33',
                    fontWeight:'bolder',
                    position: 'top'
                },
                itemStyle:{
                    borderRadius: 10,
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    { offset: 0, color: '#66ff33' },
                    { offset: 1, color: '#8cff66' }
                    ])

                },
                barGap:'80%',  //不同柱子之间的间距
                data:yDataOut
            }
        ]

    };
    return option;
}

export default plOptionFunc;



