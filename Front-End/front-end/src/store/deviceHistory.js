import axios from "axios";
const deviceHistory = {
    namespaced: true,
    state:() => ({
        deviceHistorys:[{id:1,deviceId:1,name:'尾气检测设备',dateTime:'2022-3-18 15:17:00',status:1}],
        totalPages_deviceHistory:1
    }),
     getters:{
        deviceHistoryStatusConvert(state) {
            var convertdeviceHistorys = state.deviceHistorys;
            for(var i = 0;i < convertdeviceHistorys.length;i ++) {
                if(convertdeviceHistorys[i].status == 1){
                    convertdeviceHistorys[i].status = '开启'
                }else if(convertdeviceHistorys[i].status == 0){
                    convertdeviceHistorys[i].status = '关闭'
                }else {
                    convertdeviceHistorys[i].status = 'error'
                }
            } 
            return convertdeviceHistorys;
        },

        totalPages(state) {
          return state.totalPages_deviceHistory;
        },
    },
    mutations:{
        getdeviceHistorys(state,obj){  //TM的mutatioins中的方法只允许传递一个参数，我操了，真尼玛牛逼!!!
            state.deviceHistorys = obj.deviceHistorys;
            state.totalPages_deviceHistory = obj.totalPages;
          },
    },
    actions:{
        asyncGetdeviceHistorys(context,apiUrl) {

          //axios是异步的方法
          axios.get(apiUrl).then(function(response){
            //console.log(response.data);
            let rets = response.data;
            let deviceHistorys = rets.data;
            console.log(deviceHistorys);
            context.commit('getdeviceHistorys',{deviceHistorys:deviceHistorys,totalPages:rets.totalPages});
          },function(error){
            if(error) {
              console.log(error);
            }
          });
        },
    },
    modules:{
    }
}

export default deviceHistory;