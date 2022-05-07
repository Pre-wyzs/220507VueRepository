//这是index页面的存放数据的数据仓库

import axios from 'axios'

const indexMain = {
    namespaced: true,
    state:() => ({
        // devices:[{deviceId:1,name:'尾气检测设备',startTime:'2022-3-18 15:17:00',closeTime:'',status:1}],
        /*****************************************喷淋模块**********************************************************************/
        //总的喷淋
        showers:[{}],
        //进入场地喷淋
        showersIn:[],
        //出场的喷淋
        showersOut:[],
        //进场总数
        numIn:0,
        //出场的总数
        numOut:0,
        //总数
        num:0,

        /*****************************************设备管理模块**********************************************************************/
        devices:[],
        //在线的设备的数据链
        inline:0,
        //离线的涉笔的数据量
        outline:0

    }),
    getters:{
        /*****************************************喷淋模块**********************************************************************/
        //flag的值可以有三个：'进场','出场','所有'
        showersConvert(state){
            var converts = null;
            converts = state.showers;
            converts.forEach(item=> {
                if(item.purpose == 1){
                    item.purpose = '进场';
                }else if(item.purpose == 2){
                    item.purpose = '出场';
                }

                if(item.status == 1){
                    item.status = '正常';
                }else if(item.status== 0){
                    item.status = '缺水';
                }
            });
            return converts;
        },
        showersInConvert(state){
            var converts = null;
            converts = state.showersIn;
            converts.forEach(item=> {
                if(item.purpose == 1){
                    item.purpose = '进场';
                }else if(item.purpose == 2){
                    item.purpose = '出场';
                }

                if(item.status == 1){
                    item.status = '正常';
                }else if(item.status == 0){
                    item.status = '缺水';
                }
            });
            // console.log('进场的数据',converts);
            return converts;
        },
        showersOutConvert(state){
            var converts = null;
            converts = state.showersOut;
            converts.forEach(item=> {
                if(item.purpose == 1){
                    item.purpose = '进场';
                }else if(item.purpose == 2){
                    item.purpose = '出场';
                }

                if(item.status == 1){
                    item.status = '正常';
                }else if(item.status == 0){
                    item.status = '缺水';
                }
            });
            return converts;
        },

        /*****************************************设备管理模块**********************************************************************/
        devicesConvert(state){
            var converts = [];
            let time = null;
            let status = null;
            state.devices.forEach(item=>{
                if(item.status == 1){
                    //说明设备是开着的
                    time = item.startTime;
                    status = '在线';
                }else if(item.status == 0){
                    time = item.closeTime;
                    status = '离线';
                }


                converts.push({
                    time:time,
                    name:item.name,
                    status:status
                });
            });

            return converts;
        }


    },
    mutations:{
        /*****************************************喷淋模块**********************************************************************/
        setShowers(state,showers){
            state.showers = showers.reverse();
        },
        setShowersIn(state,showersIn){
             state.showersIn = showersIn.reverse();
        },
        setShowersOut(state,showersOut){
             state.showersOut= showersOut.reverse();
        },
        setNumIn(state,map){
            state.numIn = map[0];
        },
        setNumOut(state,map){
            state.numOut = map[1];
        },
        setNum(state,map){
            state.num = map[0] + map[1];
        },


        /*****************************************设备管理模块**********************************************************************/
        setDevices(state,devices){
            state.devices = devices;
        },
        setInline(state,inline){
            state.inline = inline;
        },
        setOutline(state,outline){
            state.outline = outline;
        },
    },
    actions:{
        /*****************************************喷淋模块**********************************************************************/
        asyncGetShowers(context,obj){
            axios.get(obj.api).then(function(response){
                let rets = response.data;
                let showers = rets.data;
                //handle showers
                let sets = [];
                let tmp = null;

                showers.forEach(shower=>{
                    tmp = {
                        timeStamp:shower.timeStamp,
                        license:shower.license,
                        team:shower.team,
                        purpose:shower.purpose,
                        status:shower.status
                    };
                    sets.push(tmp);
                });
                if(obj.purpose=='进场'){
                    // console.log('进场',showers);
                    context.commit('setShowersIn',sets);
                }else if(obj.purpose=='出场'){
                    // console.log('出场',showers);
                    context.commit('setShowersOut',sets);
                }else{
                    // console.log('所有',showers);
                    //那就是更新所有的showers
                    context.commit('setShowers',sets);
                }


            },function(error){
                if(error){
                    console.log(error);
                }
            });
        },
        asyncGetShowerNum(context,api){
            axios.get(api).then(function(response){
                let rets = response.data;
                let map = rets.data;

                //这里如果map是null，进行赋值修改就会报错的捏...
                // console.log('进出场数量map',map);
                if(map!=null){
                    context.commit('setNumIn',map);
                    context.commit('setNumOut',map);
                    context.commit('setNum',map);
                }

            },function(error){
            });
        },


        /*****************************************设备管理模块**********************************************************************/
        asyncGetDevices(context,api){
            axios.get(api).then(function(response){
                let rets = response.data;
                let devices = rets.data;
                console.log(devices);

                context.commit('setDevices',devices);

                //难道执行到这利旧不会继续执行了
                let inline = 0;
                let outline = 0;
                devices.forEach(item=>{
                    if(item.status == 1){
                        inline = inline + 1;
                    }else if(item.status == 0){
                        outline = outline + 1;
                    }
                });

                context.commit('setInline',inline);
                context.commit('setOutline',outline);

            },function(error){
            });


        },


    },
    modules:{
    }
}

export default indexMain 