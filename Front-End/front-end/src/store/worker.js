import axios from 'axios';

const worker = {
    namespaced:true,
    /** 1、这个箭头的写法就可以看出vuex3中模块划分的骚气之处了...*/
    state:() => ({
        workers:[],
        totalPages:1
    }),
    getters:{
        //提供对外的权限转换后的结果接口
        retWorkers(state) {
          var rets = state.workers;
          rets.forEach(item => {
              if(item.team == '1'){
                  item.team = '管理班组'
              }else{
                  item.team = '劳务班组'
              }

              if(item.sex == 1){
                  item.sex = '男'
              }else{
                  item.sex = '女'
              }
              
              if(item.status == 1){
                  item.status = '在职'
              }else if(item.status == 2){
                  item.status = '请假' 
              }else{
                  item.status = '离职'
              }
          });
          return rets;
        },


      },
      mutations:{
        getWorkers(state,obj){  //TM的mutatioins中的方法只允许传递一个参数，我操了，真尼玛牛逼!!!
            state.workers = obj.workers;
            state.totalPages= obj.totalPages;
        },
      },
      actions:{
        asyncGetWorkers(context,obj) {
          //axios是异步的方法
          axios.put(obj.apiUrl,obj.args).then(function(response){
            let rets = response.data;
            // console.log(rets.data);
            context.commit('getWorkers',{workers:rets.data,totalPages:rets.totalPages});
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

export default worker;

