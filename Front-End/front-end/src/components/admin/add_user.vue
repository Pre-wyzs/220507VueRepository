<template>
<div style="background:rgb(0 0 0 / 45%);border-radius:10px;padding:15px;">
    <div class="form-group">
      <label for="exampleInputEmail0" class="myForm">用户姓名</label>
      <input
        type="text"
        class="form-control"
        id="exampleInputEmail1"
        aria-describedby="emailHelp"
        v-model="name"
      />
      <small id="emailHelp" class="form-text text-muted"
        >请输入用户名</small
      >
    </div>
    <div class="form-group">
      <label for="exampleInputPassword1" class="myForm">用户密码</label>
      <input type="password" class="form-control" id="exampleInputPassword1" v-model="password0"/>
      <small id="emailHelp" class="form-text text-muted"
        >请输入密码</small
      >
    </div>

    <div class="form-group">
      <label for="exampleInputPassword2" class="myForm">确认密码</label>
      <input type="password" class="form-control" id="exampleInputPassword2" v-model="password1"/>
      <small id="emailHelp" class="form-text text-muted"
        >请确认密码</small
      >
    </div>
    <div class="form-group">
      <label for="exampleInputEmail" class="myForm">用户权限</label>
      <input
        type="text"
        class="form-control"
        id="exampleInputEmail1"
        aria-describedby="emailHelp"
        v-model="power"
      />
      <small id="emailHelp" class="form-text text-muted"
        >请输入用户权限</small
      >
    </div>
    <button type="submit" class="btn btn-primary" @click="addUser">提交</button>
    <router-link to="/home/admin">
    <button type="button" class="btn btn-danger" style="margin-left:20px;">返回</button>
    </router-link>
</div>
</template>
<script>
import axios from 'axios'

export default {
  name: "AddUser",
  data(){
      return {
          name:'李宇涵',
          password0:'789456',
          password1:'789456',
          power:'0'
      }
  },
  methods:{
      //判断两次输入的用户名不一致
      judgePwd(){
          if(this.password0 != this.password1){
              alert("密码不一致！");
              return false;
          }
          return true;
      },
      //
      addUser(){
          if(this.judgePwd()){  //只有两次用户输入一致时才使用

            let access_token = JSON.parse(sessionStorage.getItem('access-token'));
            // console.log(access_token.token);

            let newUser = {id:0,name:this.name,password:this.password1,power:this.power,token:access_token.token};
            let apiUrl = "http://localhost:8080/api/admin/add";  //判断token的接口
            let _this = this;
            axios.post(apiUrl,newUser).then(
            function(response){
                if(response.data.code == '0'){
                    _this.$router.push({path:'/home/admin'});
                }else {
                    alert(response.data.msg);
                }
            },function(error){

            });

          }else{
            return;
          }
      }


  }
};
</script>
<style scoped>
.myForm {
    color:white;
}
</style>
