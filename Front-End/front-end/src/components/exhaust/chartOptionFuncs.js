// let xData = ['2022/3/25 10:00:00', '2022/3/25 10:05:00',
//     '2022/3/25 10:10:00', '2022/3/25 10:15:00', '2022/3/25 10:20:00', '2022/3/25 10:25:00',
//     '2022/3/25 10:30:00', '2022/3/25 10:35:00', '2022/3/25 10:40:00', '2022/3/25 10:45:00',
//     '2022/3/25 10:50:00', '2022/3/25 10:55:00', '2022/3/25 11:00:00', '2022/3/25 11:05:00',
// ];
// //so2的->0.5ppm；o3的->0.08ppm：100ug/m^3；NO的->30mg/m^3 ... 差距就非常的大...

// //那就把y轴的单位定位为 ug/m^3（微克每立方米，这是最通用的单位了。）
// let yDataObj = {
//     SO2: [25, 58, 26, 74, 58, 46, 42, 78, 54, 24, 59, 72, 87, 96],
//     NO: [19, 24, 32, 31, 37, 40, 74, 85, 27, 10, 74, 96, 42, 63],
//     O3: [80, 75, 73, 74, 78, 76, 78, 45, 65, 75, 78, 25, 63, 14],
//     HF: [47, 45, 48, 51, 54, 49, 74, 52, 74, 62, 89, 54, 28, 98],
//     C6H6: [31, 35, 39, 37, 34, 37, 74, 10, 85, 59, 56, 21, 74, 36]
// }


//导出配置项的函数
function exhaustOptionFunc(xData, yDataObj) {
    const option = {
        color: ["#FC7D02", "#AC3B2A", "#AA069F", "#FBDB0F", "#FD0100"],
        title: {
            text: '尾气排放实时图',
            left: 'center',
            top: 5,
            textStyle: {
                color: 'blue'
            }
        },
        legend: {
            type: 'scroll',
            data: ['SO2', 'NO', 'O3', 'HF', 'C6H6'],
            top: 5,
            right: '5%',
            formatter: function (name) {
                if (name == 'SO2') {
                    // console.log('这是二氧化硫');
                    return '{a|' + name + '}'
                } else if (name == 'NO') {
                    return '{b|' + name + '}'
                } else if (name == 'O3') {
                    return '{c|' + name + '}'
                } else if (name == 'HF') {
                    return '{d|' + name + '}'
                } else {
                    return '{e|' + name + '}'
                }
            },
            textStyle: {
                // color:'#76FF03',
                // fontWeight:'bolder',
                //使用富文本的模式
                rich: {
                    a: {
                        color: '#FC7D02',
                        fontWeight: 'bolder',
                    },
                    b: {
                        color: '#AC3B2A',
                        fontWeight: 'bolder',
                    },
                    c: {
                        color: '#AA069F',
                        fontWeight: 'bolder',
                    },
                    d: {
                        color: '#FBDB0F',
                        fontWeight: 'bolder',
                    },
                    e: {
                        color: '#FD0100',
                        fontWeight: 'bolder',
                    }
                }
            },
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
            trigger: 'axis'
        },
        dataZoom: {
            type: 'slider',
            xAxisIndex: 0,
            top: '92%',
            height: 15,
            start: 80,
            end: 100
        },
        grid: {
            show: true,
            borderWidth: 1,
            borderColor: 'rgb(0 0 0 / 30%)',
            top: '20%',
            bottom: '20%',
            left: '10%',
            right: '10%'
            // width:600,
            // height:280

        },
        xAxis: {
            type: 'category',
            data: xData,
            boundaryGap: false, //设置紧贴边缘
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
            axisTick: {
                show: false
            }

        },
        yAxis: {
            type: 'value',
            //设置y轴的值不从0开始
            scale: true,
            // name:'μg/m³',
            // nameLocation:'end',
            // nameTextStyle:{
            //     color:'white',
            //     fontWeight:'bolder'

            // },

            axisLine: {
                show: false,
                lineStyle: {
                    color: '#03A9F4'
                }
            },
            axisLabel: {
                margin: 15,
                formatter: '{value}μg/m³',
                fontWeight: 'bolder'
            },
            splitLine: {
                show: false,

            }

        },
        series: [
            {
                name: 'SO2',
                type: 'line',
                smooth: true,
                stack: 'all',
                areaStyle: {},
                label: {
                    show: true,
                    position: 'right'
                },
                data: yDataObj.SO2,

            },
            {
                name: 'NO',
                type: 'line',
                smooth: true,
                stack: 'all',
                areaStyle: {},
                label: {
                    show: true,
                    position: 'right'
                },
                data: yDataObj.NO,
            },
            {
                name: 'O3',
                type: 'line',
                smooth: true,
                stack: 'all',
                areaStyle: {},
                label: {
                    show: true,
                    position: 'right'
                },

                data: yDataObj.O3,

            },
            {
                name: 'HF',
                type: 'line',
                smooth: true,
                stack: 'all',
                areaStyle: {},
                label: {
                    show: true,
                    position: 'right'
                },
                data: yDataObj.HF,

            },
            {
                name: 'C6H6',
                type: 'line',
                smooth: true,
                stack: 'all',
                areaStyle: {},
                label: {
                    show: true,
                    position: 'right',
                },
                data: yDataObj.C6H6,

            },
        ]

    };

    return option;
}


// let pieData = [{ name: 'SO2', value: 96 }, { name: 'NO', value: 63 }, { name: 'O3', value: 14 }, { name: 'HF', value: 98 }, { name: 'C6H6', value: 36 }];
function partOptionFunc(pieData) {
    const option = {
        color: ["#FC7D02", "#AC3B2A", "#AA069F", "#FBDB0F", "#FD0100"],
        title: {
            text: '成分占比实时图',
            left: 'center',
            top: 5,
            textStyle: {
                color: 'blue',
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
        series: [
            {
                type: 'pie',
                data: pieData,
                label: {
                    show: true,
                    formatter: function (arg) {
                        if (arg.name == 'SO2') {
                            return '{a|' + arg.name + '}' + '{public|：' + arg.value + 'μg/m³ ' + arg.percent + '%}';
                        } else if (arg.name == 'NO') {
                            return '{b|' + arg.name + '}' + '{public|：' + arg.value + 'μg/m³ ' + arg.percent + '%}';
                        } else if (arg.name == 'O3') {
                            return '{c|' + arg.name + '}' + '{public|：' + arg.value + 'μg/m³ ' + arg.percent + '%}';
                        } else if (arg.name == 'HF') {
                            return '{d|' + arg.name + '}' + '{public|：' + arg.value + 'μg/m³ ' + arg.percent + '%}';
                        } else {
                            return '{e|' + arg.name + '}' + '{public|：' + arg.value + 'μg/m³ ' + arg.percent + '%}';
                        }


                    },
                    rich: {
                        //公共的样式
                        public: {
                            color: '#03A9F4',
                            fontWeight: 'bolder'
                        },
                        a: {
                            color: '#FC7D02',
                            fontWeight: 'bolder',
                        },
                        b: {
                            color: '#AC3B2A',
                            fontWeight: 'bolder',
                        },
                        c: {
                            color: '#AA069F',
                            fontWeight: 'bolder',
                        },
                        d: {
                            color: '#FBDB0F',
                            fontWeight: 'bolder',
                        },
                        e: {
                            color: '#FD0100',
                            fontWeight: 'bolder',
                        }
                    }
                    // color:'#03A9F4',
                    // fontWeight:'bolder'
                },
                radius: ['35%', '60%'],
                center: ['50%', '60%'],
                selectedMode: 'single',
                selectedOffset: 10

            }
        ]
    }


    return option;
}


// let radarData = [96, 63, 14, 98, 36];

function radarOptionFunc(radarData) {

    let dimData = [
        {
            name: 'SO2',
            max:200 
        },
        {
            name: 'NO',
            max: 200
        },
        {
            name: 'O3',
            max: 200
        },
        {
            name: 'HF',
            max: 200
        },
        {
            name: 'C6H6',
            max: 200
        },
    ];

    const option = {
        color: ['#00FF00', 'red'],
        title: {
            text: '超标检测实时图',
            left: 'center',
            top: 5,
            textStyle: {
                color: 'blue',
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
        radar: {
            indicator: dimData,
            center: ['50%', '60%'],
            radius: '65%',
            axisTicks:{
                show:false
            },
            axisName: {
                // color: '#03A9F4',
                // fontWeight: 'bolder'
                formatter: function (name) {
                    // console.log(name);
                    if (name == 'SO2') {
                        // console.log('这是二氧化硫');
                        return '{a|' + name + '}'
                    } else if (name == 'NO') {
                        return '{b|' + name + '}'
                    } else if (name == 'O3') {
                        return '{c|' + name + '}'
                    } else if (name == 'HF') {
                        return '{d|' + name + '}'
                    } else {
                        return '{e|' + name + '}'
                    }
                },
                rich: {
                    a: {
                        color: '#FC7D02',
                        fontWeight: 'bolder',
                    },
                    b: {
                        color: '#AC3B2A',
                        fontWeight: 'bolder',
                    },
                    c: {
                        color: '#AA069F',
                        fontWeight: 'bolder',
                    },
                    d: {
                        color: '#FBDB0F',
                        fontWeight: 'bolder',
                    },
                    e: {
                        color: '#FD0100',
                        fontWeight: 'bolder',
                    }
                }


            }
        },
        series: [
            {
                type: 'radar',
                data: [
                    {
                        name: '实时数据',
                        value: radarData,
                        areaStyle: {}
                    },
                    {
                        name: '阈值',
                        value: [160, 160, 160, 160, 160],
                    }
                ]
            }
        ]

    }

    return option
}

const test = -1;

//导出这个配置项的函数
export {
    exhaustOptionFunc,
    partOptionFunc,
    radarOptionFunc
};

export default test;
