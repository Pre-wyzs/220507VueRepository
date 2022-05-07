import { createStore } from 'vuex'
import user from './user.js'
import  device  from './device.js'
import deviceHistory from './deviceHistory.js'
import exhaustHistory from './exhaustHistory.js'
import dustHistory from './dustHistory.js'
import worker from './worker.js'
import workerSign from './workerSign.js'
import indexMain from './indexMain'


//createStore 函数可以返回一个数据仓储对象
export default createStore({

  modules: {  //子模块
    user,
    device,
    deviceHistory,
    exhaustHistory,
    dustHistory,
    worker,
    workerSign,
    indexMain
  }
})
