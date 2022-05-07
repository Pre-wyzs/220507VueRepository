import { createRouter, createWebHistory } from 'vue-router'
import Index from '../views/Index.vue'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Setting from '../views/Setting.vue'
import Admin from '../components/admin/admin.vue'
import AddUser from '../components/admin/add_user.vue'
import UpdateUser from '../components/admin/update_user.vue'
import NotFound from '../components/error/not_found.vue'
import DeviceHistory from '../components/device/device_history.vue'
import DeviceInfo from '../components/device/device_info.vue'
import DeviceAdd from '../components/device/device_add.vue'
import DeviceModify from '../components/device/device_modify.vue'
import ExhaustInfo from '../components/exhaust/exhaust_info.vue'
import ExhaustHistory from '../components/exhaust/exhaust_history.vue'
import DustInfo from '../components/dust/dust_info.vue'
import DustHistory from '../components/dust/dust_history.vue'
import WorkerInfo from '../components/worker/worker_info.vue'
import AddWorker from '../components/worker/add_worker.vue'
import UpdateWorker from '../components/worker/update_worker.vue'
import WorkerSign from '../components/worker/worker_sign.vue'
import SignConfig from '../components/worker/config/sign_config'
import Camera from '../views/Camera'
import MapContainer from '../components/map/map_container'
import CarInfo from '../components/car/car_info'
import CarHistory from '../components/car/car_history'
import CarPL from '../components/car/car_pl'
import VideoInfo from '../components/video/video_info'
import Welcome from '../components/welcome'





const routes = [
  {
    path: '/home',
    name: 'home',
    component: Home,
    children:[
      {path:'/home/device/info',name:'DeviceInfo',component:DeviceInfo},
      {path:'/home/device/history',name:'DeviceHistory',component:DeviceHistory},
      {path:'/home/device/add',name:'DeviceAdd',component:DeviceAdd},
      {path:'/home/device/modify/:deviceId',name:'DeviceModify',component:DeviceModify},
      {path:'/home/exhaust/info',name:'ExhaustInfo',component:ExhaustInfo},
      {path:'/home/exhaust/history',name:'ExhaustHistory',component:ExhaustHistory},
      {path:'/home/dust/info',name:'DustInfo',component:DustInfo},
      {path:'/home/dust/history',name:'DustHistory',component:DustHistory},
      {path:'/home/worker/info',name:'WorkerInfo',component:WorkerInfo},
      {path:'/home/worker/info/add',name:'AddWorker',component:AddWorker},
      {path:'/home/worker/info/update/:id',name:'UpdateWorker',component:UpdateWorker},
      {path:'/home/worker/sign',name:'WorkerSign',component:WorkerSign},
      {path:'/home/worker/setting',name:'SignConfig',component:SignConfig},
      {path:'/home/map/info',name:'MapContainer',component:MapContainer},
      {path:'/home/car/info',name:'CarInfo',component:CarInfo},
      {path:'/home/car/history',name:'CarHistory',component:CarHistory},
      {path:'/home/car/pl',name:'CarPL',component:CarPL},
      {path:'/home/video/info',name:'VideoInfo',component:VideoInfo},

      { path:'/home/admin', name:'home-admin', component:Admin },
      { path:'/home/addUser', name:'home-addUser', component:AddUser },
      { path:'/home/updateUser/:id', name:'home-updateUser', component:UpdateUser },
      {path:'/home/',name:'Welcome',component:Welcome},


    ]
  },
  {
    path:'/login',
    name:'login',
    component:Login
  },
  {
    path:'/setting',
    name:'setting',
    component:Setting
  },
  {
    path:'/camera/:code',
    name:'Camera',
    component:Camera
  },
  // {  注销不需要做任何的配置的！
  //   path:'/logout',
  //   redirect:'/login'
  // }
  {
    path:'/logout'  //这样防止说/logout报警告就行了
  },
  {
    path:'/',
    name:'Index',
    component:Index
  },
  {
    path:'/:pathMatch(.*)',
    name:'NotFound',
    component:NotFound
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
