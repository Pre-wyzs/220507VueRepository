<template>
  <div class="login">
    <div class="infos">
      <h2 class="title">智慧工地后台</h2>
      <div class="center-auto pd-sm-50 pd-xs-20 main_content">
        <p class="title_login">用户登录</p>
        <div>
          <div class="form-group mg-t20">
            <div class="icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-user align-middle mr-2"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>
            </div>
            <input
              type="text"
              class="login_input"
              id="Email1"
              placeholder="请输入用户名"
              style="color: white"
              v-model="userName"
              @focus="inputFocusOnName"
            />
          </div>
          <alert-box errMsg="用户名不能空!" v-if="flagName"/>
          <div class="form-group mg-t20">
            <div class="icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-lock align-middle mr-2"><rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect><path d="M7 11V7a5 5 0 0 1 10 0v4"></path></svg>
            </div>
            <input
              type="password"
              class="login_input"
              id="Password1"
              placeholder="请输入密码"
              style="color: white"
              v-model="userPwd"
              @focus="inputFocusOnPwd"
              @keydown.enter="loginSubmit"
            />
          </div>
          <alert-box errMsg="密码不能为空!" v-if="flagPwd"/>
          <div class="remember"><input type="checkbox" v-model="check"/>记住我的登录信息</div>
          <div style="display:flex;justify-content:space-between;">
            <input type="submit" class="login_btn" @click="loginSubmit" value="登录">
            <input type="button" class="login_btn" @click="retIndex" value="返回">
          </div>

        </div>
      </div>
    </div>
    <!--row end-->
    <!--container end-->
  </div>
</template>
<script>
import AlertBox from "../components/login/alert_box.vue";
import { mapState,mapGetters,mapMutations,mapActions } from 'vuex';
import axios from 'axios'
import { getAES, getDAES } from '../crypto/crypto'

export default {

  name: "Login",
  components: {
    AlertBox,
  },
  created(){

    //这个是给Home组件的背景图片的路径
    sessionStorage.setItem('bgUrl','/img/bg.jpg');

    if(localStorage.getItem('loginNameBuffer')&&localStorage.getItem('loginPwdBuffer')){
      this.userName = localStorage.getItem('loginNameBuffer');
      this.userPwd = localStorage.getItem('loginPwdBuffer');
    }
  },
  data() {
      return {
          message:'',
          userName:'',
          userPwd:'',

          //判断是否存在的标志位
          flagPwd:false,
          flagName:false,

          check:false

      }
  },
  computed: {
        ...mapState(['users']),  //把全局变量映射为计算属性
  },
  methods:{
      loginSubmit(){

        //1、先处理输入为空的情况
        if(this.userName == '' || this.userPwd == '') {
          if(this.userName == ''){
              this.flagName = true;
          }
          if(this.userPwd == '') {
            this.flagPwd = true;
          }
          return;
        }

        //2、判断一下用户是否要保存登录信息
        if(this.check == true){
          localStorage.setItem('loginNameBuffer',this.userName);
          localStorage.setItem('loginPwdBuffer',this.userPwd);
        }

        let apiUrl = "http://localhost:8080/api/login";
        let that = this;

        /**
         * 对密码数据进行AES加密处理
         */
        let encrypted = getAES(this.userPwd);
        // console.log(encrypted);
        // console.log(getDAES(encrypted));

        //构造表单形式的请求体参数构造
        axios
        .post(apiUrl,{name:this.userName,password:encrypted})
        .then(function(response){
          // console.log(response.data);

          if(response.data.code == '0'){  //说明登录成功了
            sessionStorage.setItem('loginName',that.userName)
            sessionStorage.setItem('power',that.convertPower(response.data.data.power));
            /**
             * 保存传回来的token。
             */
            sessionStorage.setItem('access-token',JSON.stringify(response.data.data));
            that.$router.push({name:'Welcome'});
          }else{
            alert('用户名或密码错误！');
          }
        },
        function(error){});
        

      },
      inputFocusOnName(){
        if(this.flagName == true) {
          this.flagName = false;  //输入框获取焦点的时候重置一下
        }
      },
      inputFocusOnPwd(){
        if(this.flagPwd== true) {
          this.flagPwd= false;  //输入框获取焦点的时候重置一下
        }
      },
      /**
       * 权限转换接口
       * 
       */
      convertPower(power){
        if(power == '0'){
          return "超级管理"
        }else if(power == '1'){
          return "普通管理"
        }else {
          return "普通用户"
        }
      },

      retIndex(){
        this.$router.push({name:'Index'});

      }
  }
};
</script>

<style scoped>
.login {
  background: url(../assets/images/bg.jpg) center no-repeat;
  width: 100%;
  height: 100vh; /*高就是浏览器头底部到最底部的高 */
}
.title {
  text-align: center;
  color: white;
  line-height: 2;
}
.infos {
  position: absolute;
  width: 30%;
  /* left: 35%;
  margin-top:20vh; */
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.title_login {
  text-align: center;
  color: white;
  font-size: 16px;
}
.remember {
  margin: 10px 0;
  color: white;
}
.remember input {
  position: relative;
  top: 2px;
  margin-right: 10px;
}

/*把整个屏幕真正撑开--而且能自己实现居中*/
.main_content {
  background: url(../assets/images/main_bg.png) repeat;
  text-align: left;
  float: none;
  border-radius: 8px;
}

.form-group {
  position: relative;
}

.login_btn {
  display: block;
  background: #3872f6;
  color: #fff;
  font-size: 15px;
  width: 45%;
  line-height: 50px;
  border-radius: 3px;
  border: none;
}

.login_input {
  width: 100%;
  border: 1px solid #3872f6;
  border-radius: 3px;
  line-height: 40px;
  padding: 2px 5px 2px 30px;
  background: none;
}

.icon {
  position: absolute;
  color:white;
  top: 8px;
  left: 5px;
}

.icon_font {
  position: absolute;
  bottom: 15px;
  left: 10px;
  font-size: 18px;
  color: #3872f6;
}

.mg-t20 {
  margin-top: 20px;
}

@media (min-width: 200px) {
  .pd-xs-20 {
    padding: 20px;
  }
}

@media (min-width: 768px) {
  .pd-sm-50 {
    padding: 50px;
  }
}
</style>