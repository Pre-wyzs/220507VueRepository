<template>
<div style="background:rgb(0 0 0 / 45%);border-radius:10px;padding:15px;">
    <div class="form-group">
      <label for="exampleInputEmail1" class="myForm">用户ID</label>
      <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" :value="$route.params.id" readonly />
      <small id="emailHelp" class="form-text text-muted"
        >用户ID</small
      >
    </div>

    <div class="form-group">
      <label for="exampleInputEmail1" class="myForm">用户姓名</label>
      <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" v-model="name" />
      <small id="emailHelp" class="form-text text-muted"
        >请输入用户名</small
      >
    </div>
    <div class="form-group">
      <label for="exampleInputPassword1" class="myForm">用户密码</label>
      <input type="password" class="form-control" id="exampleInputPassword1" v-model="password"/>
      <small id="emailHelp" class="form-text text-muted"
        >请输入密码</small
      >
    </div>

    <div class="form-group">
      <label for="exampleInputEmail1" class="myForm">用户权限</label>
      <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" v-model="power" />
      <small id="emailHelp" class="form-text text-muted" >请输入用户权限</small
      >
    </div>
    <button type="submit" class="btn btn-primary" @click="updateUser">提交</button>
    <router-link to="/home/admin">
    <button type="button" class="btn btn-danger" style="margin-left:20px;">返回</button>
    </router-link>
</div>
</template>
<script>
import axios from 'axios'

export default {
  name: "UpdateUser",
  data(){
      return{
          id:'',
          name:'',
          password:'',
          power:''
      }
  },
  methods:{
      updateUser(){
            let access_token = JSON.parse(sessionStorage.getItem('access-token'));
            // console.log(access_token.token);
            // console.log(this.$route.params.id);

            let updateUser = {id:this.$route.params.id,name:this.name,password:this.password,power:this.power,token:access_token.token};
            let apiUrl = "http://localhost:8080/api/admin/update";  //判断token的接口
            let _this = this;
            axios.put(apiUrl,updateUser).then(
            function(response){
                if(response.data.code == '0'){
                    _this.$router.push({path:'/home/admin'});
                }else {
                    alert(response.data.msg);
                }
            },function(error){

            });

      }


  }
};
</script>
<style scoped>
.myForm {
    color:white;
}
</style>
