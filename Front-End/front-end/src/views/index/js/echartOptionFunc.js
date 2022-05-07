
function signChartOptionFunc(xData,yData){
   //配置项
   const option = {
    color: ["#2f89cf"],
    width: "85%",
    height: "70%",
    grid: {
        //只能控制左右自由缩放，不能控制上下自由缩放，
        top: "10%",
    },
    //提示工具
    tooltip: {
        trigger: "axis",  //鼠标只要放到坐标轴上就会触发
        axisPointer: {
            type: 'shadow'
        }
    },

    xAxis: {
        //x轴的数据类型
        type: 'category',
        data: xData,

        //刻度相关设置：axisTick
        axisTick: {
            show: false // 去除刻度线
        },
        //刻度标签相关设置：axisLabel
        axisLabel: {
            color: 'rgb(255,255,255,.6)' // 文本颜色
        },
        axisLine: {
            show: false // 去除轴线
        },


    },
    yAxis: {
        //y轴的数据类型
        type: 'value',
        //设置y轴不出现小数的做法
        minInterval : 1,


        show: true,  //@@它决定的是整个y轴的显示
        //刻度标签的相关样式
        axisLabel: {
            color: "rgb(255,255,255,.6)",
            fontsize: "12"
        },

        //y轴线的样式
        axisLine: {
            show: true,  //@@*这个才是y轴的线
            lineStyle: {
                color: "rgb(255,255,255,.6)",
                width: "0"
            },

        },

        //y轴分割线（就是垂直与y轴的直线的样式）
        splitLine: {
            show: true,
            lineStyle: {
                color: "#012f4a",
                width: "1"
            },

        }

    },
    series: [

        {
            data: yData,
            type: 'bar',
            //修改柱子的宽度
            barWidth: "50%",
            showBackground: false,
            backgroundStyle: {
                // color: '#012f4a'
                color:'#2abe3d'
            },
            //柱子的样式修改
            itemStyle: {
                borderRadius: 5  //修改柱子的角为圆角
            },
            label:{
                show:true,
                position:'top',
                fontWeight:'bolder',
                // color:'black'
                // color:'#2abe3d'
            }
        }
    ]
};

return option;
}


function dustChartOptionFunc(xData,pm10,aqi,pm25){
    const option = {
        color: ['#79C3DF', '#EE6666', '#FAC858'],
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['PM10', 'AQI', 'PM2.5'],
            textStyle: {
                color: '#4c9bfd' // 图例文字颜色
            },
            right: "0%"
        },
        grid: {
            top: '10%',
            left: '3%',
            right: '4%',
            bottom: '15%',
            show: true,// 显示边框
            borderColor: '#012f4a',// 边框颜色
            containLabel: true // 包含刻度文字在内

        },
        toolbox: {
        },
        xAxis: {
            type: 'category',
            boundaryGap: true,  //
            data: xData,
            //刻度相关设置：axisTick
            axisTick: {
                show: false // 去除刻度线
            },
            //刻度标签相关设置：axisLabel
            axisLabel: {
                color: '#4c9bfd' // 文本颜色
            },
            axisLine: {
                show: false // 去除轴线
            },
        },
        yAxis: {
            type: 'value',
            axisTick: {
                show: false // 去除刻度线
            },
            axisLabel: {
                color: '#4c9bfd' // 文本颜色
            },
            axisLine: {
                show: false // 去除轴线
            },
            splitLine: {
                lineStyle: {
                    color: '#012f4a' // 分割线颜色
                }
            }
        },
        series: [
            {
                name: 'PM10',
                type: 'line',
                smooth: true,  //是否圆滑显示
                data: pm10,
                markLine: {
                    data: [{ type: 'average' }],
                    label: {
                        show: false
                    },
                    symbol: 'none'

                },
            },
            {
                name: 'AQI',
                type: 'line',
                smooth: true,  //是否圆滑显示
                data: aqi,
                markLine: {
                    data: [{ type: 'average' }],
                    label: {
                        show: false,
                        position:'middle',
                        fontWeight: 'lighter',
                        fontSize: "10px"
                    },
                    symbol: 'none'
                },
            },
            {
                name: 'PM2.5',
                type: 'line',
                //stack: 'total',  这个东西会导致数据的叠加，哈哈
                smooth: true,  //是否圆滑显示
                data: pm25,
                markLine: {
                    data: [{ type: 'average' }],
                    label: {
                        show: false
                    },
                    symbol: 'none'
                },
            },
        ]
    };

    return option;
}

function exhaustChartOptionFunc(radarData){
    const options = {

        //@1全局数据颜色#00FF00
        color: ["red","#00FF00"],  //对每一项数据进行颜色的分配
        //@2图例
        legend: {
            //图例名称
            data: ['阈值', '当前'],
            //@3图例的位置
            right: 0,
            //图例文字的样式
            textStyle: {
                color: 'white',
            }
        },


        tooltip: {
            trigger: "axis",  //鼠标只要放到坐标轴上就会触发

        },
        //@4规定数据图的类型，这里是雷达图
        radar: {
            //指示器：雷达图的每一个角
            indicator: [
                {
                    name: '臭氧',
                    max: 200,
                    color: "#ffeb7b",
                },
                {
                    name: '氟化氢',
                    max: 200,
                    color: "#ffeb7b",
                },
                {
                    name: '氮氧化物',
                    max: 200,
                    color: "#ffeb7b",
                },
                {
                    name: '芳香烃',
                    max: 200,
                    color: "#ffeb7b",
                },
                {
                    name: '二氧化硫',
                    max: 200,
                    color: "#ffeb7b",
                }
            ],
            center: ["50%", "50%"],  //雷达图居中显示
            radius: ["0%", "65%"],  //第二个设置外半径的大小

            axisName: {
                formatter: '【{value}】'  //指示器显示格式
            }


        },
        //@5一系列的数据
        series: [
            {
                type: 'radar',
                data: [
                    {
                        value: [160,160,160,160,160,160 ],
                        name: '阈值'
                    },
                    {
                        value: radarData,
                        name: '当前',
                        areaStyle:{}
                    }
                ],
                tooltip: {
                    trigger: 'item'

                },
                // areaStyle: {},
            }
        ]
    };  //options配置

    return options
}


export {
    signChartOptionFunc,
    dustChartOptionFunc,
    exhaustChartOptionFunc
};


