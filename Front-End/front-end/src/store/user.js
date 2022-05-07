import axios from 'axios';

const user = {
    namespaced:true,
    /** 1、这个箭头的写法就可以看出vuex3中模块划分的骚气之处了...*/
    state:() => ({
        users:[],
        totalPages_user:1
    }),
    getters:{
        getUsers(state) {
          return state.users;  //返回所有用户的数组
        },
        //提供对外的权限转换后的结果接口
        powerConvert(state) {
          var convertUsers = state.users;
        //   console.log('这是convertUsers');
        //   console.log(convertUsers);
          for(var i = 0;i < convertUsers.length;i ++) {
            // console.log(convertUsers[i].power);
            if(convertUsers[i].power == 0) {
              convertUsers[i].power = '超级管理'
            }else if (convertUsers[i].power == 1) {
              convertUsers[i].power = '普通管理'
            }else {
              convertUsers[i].power = '普通用户'
            }
          } 
          //返回权限处理过的用户数据（这样就不用再每一个组件的钩子函数中去处理这些东西了...）
          return convertUsers;
        },

        totalPages(state) {
          return state.totalPages_user;
        }
      },
      mutations:{
        getUsers(state,obj){  //TM的mutatioins中的方法只允许传递一个参数，我操了，真尼玛牛逼!!!
          if(obj.users instanceof Array) {
            state.users = obj.users;
          }else {
            state.users = [obj.users];
          }
          state.totalPages_user = obj.totalPages;
        },
      },
      actions:{
        asyncGetUsers(context,apiUrl) {
          //axios是异步的方法
          axios.get(apiUrl).then(function(response){
            //console.log(response.data);
            let rets = response.data;
            let users = rets.data;
            console.log(users);
            context.commit('getUsers',{users:users,totalPages:rets.totalPages});
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

export default user;
