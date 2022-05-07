import axios from 'axios'

const device = {
    namespaced: true,
    state:() => ({
        devices:[{deviceId:1,name:'尾气检测设备',startTime:'2022-3-18 15:17:00',closeTime:'',status:1}],
        totalPages_device:1
    }),
    getters:{
        //提供对外的权限转换后的结果接口
        deviceStatusConvert(state) {
            var convertDevices = state.devices;
            for(var i = 0;i < convertDevices.length;i ++) {
                if(convertDevices[i].status == 1){
                    convertDevices[i].status = '在线'
                    convertDevices[i].closeTime = '未关闭'
                }else if(convertDevices[i].status == 0){
                    convertDevices[i].status = '离线'
                }else {
                    convertDevices[i].status = 'error'
                }
            } 
            return convertDevices;

        },

        totalPages(state) {
          return state.totalPages_device;
        },

    },
    mutations:{
        getDevices(state,obj){  //TM的mutatioins中的方法只允许传递一个参数，我操了，真尼玛牛逼!!!
            state.devices = obj.devices;
            state.totalPages_device = obj.totalPages;
          },
    },
    actions:{
        asyncGetDevices(context,apiUrl) {
          //axios是异步的方法
          axios.get(apiUrl).then(function(response){
            //console.log(response.data);
            let rets = response.data;
            let devices = rets.data;
            // console.log(devices);
            context.commit('getDevices',{devices:devices,totalPages:rets.totalPages});

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

export default device 