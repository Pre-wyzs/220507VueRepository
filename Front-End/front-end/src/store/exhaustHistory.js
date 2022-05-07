import axios from 'axios'

const exhaustHistory = {
    namespaced: true,
    state:() => ({
        exhaustHistorys:[{id:1,timeStamp:'2022-03-25 13:09:57',partName:'SO2',value:'33',standardValue:'160'}],
        totalPages:1,
    }),
    getters:{
      //返回正常的数据
      retHistorys(state){
        let historys = [];
        for(let i=0;i < state.exhaustHistorys.length;i++){

          if(Number(state.exhaustHistorys[i].value) > Number(state.exhaustHistorys[i].standardValue)){

            historys.push({timeStamp:state.exhaustHistorys[i].timeStamp,
              partName:state.exhaustHistorys[i].partName,
              value:state.exhaustHistorys[i].value,
              status:'超标'
            });

          }else {
            historys.push({timeStamp:state.exhaustHistorys[i].timeStamp,
              partName:state.exhaustHistorys[i].partName,
              value:state.exhaustHistorys[i].value,
              status:'正常'
            });
          }
        }
        return historys;
      },
      //返回超标的数据
      exceedHistorys(state){
        let exceeds = [];
        let value1 = 1;
        let value2 = 2;

        for(let i=0;i < state.exhaustHistorys.length;i++){
          value1 = Number(state.exhaustHistorys[i].value);
          value2 = Number(state.exhaustHistorys[i].standardValue);
          if( value1 > value2 ){
            exceeds.push({timeStamp:state.exhaustHistorys[i].timeStamp,
                          partName:state.exhaustHistorys[i].partName,
                          value:state.exhaustHistorys[i].value,
                          standardValue:state.exhaustHistorys[i].standardValue,
                          percent:(value1/value2*100).toFixed(2)+'%'
            });

          }
        }
        return exceeds;
      },

      // //获取总的页数还是必须的捏
      // totalPages(state) {
      //   return state.totalPages;
      // },


    },
    mutations:{
        getExhaustHistorys(state,obj){  //TM的mutatioins中的方法只允许传递一个参数，我操了，真尼玛牛逼!!!
            state.exhaustHistorys = obj.exhaustHistorys;
            state.totalPages = obj.totalPages;
        }

    },
    actions:{
        asyncGetExhaustHistorys(context,apiUrl) {
          //axios是异步的方法
          axios.get(apiUrl).then(function(response){
            let rets = response.data;
            // console.log(rets.totalPages);
            context.commit('getExhaustHistorys',{exhaustHistorys:rets.data,totalPages:rets.totalPages});
            
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

export default exhaustHistory