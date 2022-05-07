import axios from 'axios';

const workerSign = {
    namespaced:true,
    /** 1、这个箭头的写法就可以看出vuex3中模块划分的骚气之处了...*/
    state:() => ({
        workerSigns:[],
        totalPages:1
    }),
    getters:{
        //提供对外的权限转换后的结果接口
        retWorkerSigns(state) {
          var rets = state.workerSigns;
          rets.forEach(item => {
              if(item.team == '1'){
                  item.team = '管理班组'
              }else{
                  item.team = '劳务班组'
              }

              if(item.sign== '1'){
                  item.sign= '已签到'
              }else if(item.sign== '2'){
                  item.sign = '迟到'
              }else if(item.sign == '3'){
                  item.sign= '补签到'
              }else if(item.sign == '4'){
                  item.sign= '早退'
              }else{
                  item.sign= '未签到'
              }


              if(item.purpose == '1'){
                  item.purpose = '入场'
              }else{
                  item.purpose = '出场'
              }
          });
          return rets;
        },


      },
      mutations:{
        getWorkerSigns(state,obj){  //TM的mutatioins中的方法只允许传递一个参数，我操了，真尼玛牛逼!!!
            state.workerSigns = obj.workerSigns;
            state.totalPages= obj.totalPages;
        },
      },
      actions:{
        asyncGetWorkerSigns(context,obj) {
          //axios是异步的方法
          axios.put(obj.apiUrl,obj.args).then(function(response){
            let rets = response.data;
            // console.log(rets.data);
            context.commit('getWorkerSigns',{workerSigns:rets.data,totalPages:rets.totalPages});
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

export default workerSign;



