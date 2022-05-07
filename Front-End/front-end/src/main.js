import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import '../public/css/global.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle'

/**这些是excel文件的导出要用到的js文件 */
import VueAxios from 'vue-axios'  //vueAxios
import axios from 'axios'

/**引入element - ui */
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'


let token = ''; 
//路由守卫：
router.beforeEach((to,from,next) => {
    token =  sessionStorage.getItem('access-token');
    if(to.path == '/'){
        next();
    }else if(token || to.path == '/login') { 

        if(token == null){  //没有token说明是去login的
            next();
        }else if(to.path == '/login'){  //这说明是有token的了，然后还要去/login的话
            next({path:'/home'});
        }else if(to.path == '/logout'){
            sessionStorage.clear();
            next('/login');
        }else{
            next();
        }
        // if(token && to.path == '/login') {  //如果用户已经登录就不要让它去登录页面了
        //     next({path:'/home'});
        // }else if(token && to.path == '/logout'){
        //     sessionStorage.clear();  //清除会话中保存的登录信息
        //     next({path:'/login'});
        // }else {
        //     next();
        // }
    }else{
        next('/login');
    }
})

const app = createApp(App);
app.use(store).use(router).use(VueAxios,axios).use(ElementPlus).mount('#app');
