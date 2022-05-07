import axios from 'axios'

const dustHistory= {
    namespaced: true,
    state:() => ({
        dustHistorys:[{id:1,timeStamp:'2022-04-03 12:50:56',aqi:'56',pm10:'17',pm25:'24',degree:2}],
        totalPages:1,
    }),
    getters:{
      //返回正常的数据
      retHistorys(state){
        let rets= [];
        let historys = state.dustHistorys;
        for(let i=0;i<historys.length;i++){
            switch(state.dustHistorys[i].degree){
                case 1:
                    rets.push({id:historys[i].id,timeStamp:historys[i].timeStamp,aqi:historys[i].aqi,pm10:historys[i].pm10,
                    pm25:historys[i].pm25,degree:'优级'});
                    break;
                case 2:
                    rets.push({id:historys[i].id,timeStamp:historys[i].timeStamp,aqi:historys[i].aqi,pm10:historys[i].pm10,
                    pm25:historys[i].pm25,degree:'良好'});
                    break;
                case 3:
                    rets.push({id:historys[i].id,timeStamp:historys[i].timeStamp,aqi:historys[i].aqi,pm10:historys[i].pm10,
                    pm25:historys[i].pm25,degree:'轻微污染'});
                    break;
                case 4:
                    rets.push({id:historys[i].id,timeStamp:historys[i].timeStamp,aqi:historys[i].aqi,pm10:historys[i].pm10,
                    pm25:historys[i].pm25,degree:'轻度污染'});
                    break;
                case 5:
                    rets.push({id:historys[i].id,timeStamp:historys[i].timeStamp,aqi:historys[i].aqi,pm10:historys[i].pm10,
                    pm25:historys[i].pm25,degree:'中度污染'});
                    break;
                case 6:
                    rets.push({id:historys[i].id,timeStamp:historys[i].timeStamp,aqi:historys[i].aqi,pm10:historys[i].pm10,
                    pm25:historys[i].pm25,degree:'重度污染'});
                    break;
            }
        }
        return rets;
      },

      // //获取总的页数还是必须的捏
      // totalPages(state) {
      //   return state.totalPages;
      // },


    },
    mutations:{
        getDustHistorys(state,obj){  //TM的mutatioins中的方法只允许传递一个参数，我操了，真尼玛牛逼!!!
            state.dustHistorys = obj.dustHistorys;
            state.totalPages = obj.totalPages;
            // console.log(state.dustHistorys);
        }

    },
    actions:{
        asyncGetDustHistorys(context,apiUrl) {
          //axios是异步的方法
          axios.get(apiUrl).then(function(response){
            let rets = response.data;
            // console.log(rets.totalPages);
            // console.log(rets.data);
            context.commit('getDustHistorys',{dustHistorys:rets.data,totalPages:rets.totalPages});
            
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

export default dustHistory 