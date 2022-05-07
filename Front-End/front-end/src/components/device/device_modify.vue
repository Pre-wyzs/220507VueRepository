<template>
<div style="background:rgb(0 0 0 / 45%);border-radius:10px;padding:15px;">

    <div class="form-group">
      <label for="exampleInputEmail0" class="myForm">设备ID</label>
      <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" v-model="$route.params.deviceId" readonly/>
      <small id="emailHelp" class="form-text text-muted"
        >设备ID</small
      >
    </div>

    <div class="form-group">
      <label for="exampleInputEmail0" class="myForm">设备名</label>
      <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" v-model="name"/>
      <small id="emailHelp" class="form-text text-muted"
        >输入新增的设备名称</small
      >
    </div>
    <button type="submit" class="btn btn-primary" @click="modifyDevice">提交</button>
    <router-link to="/home/device/info">
    <button type="button" class="btn btn-danger" style="margin-left:20px;">返回</button>
    </router-link>
</div>

</template>
<script>
import axios from 'axios'

export default {
    name:'DeviceModify',
    data(){
        return {
            deviceId:'',
            name:''
        }
    },
    methods:{
        //修改设备名的接口
        modifyDevice(){
            //把用户的token拿出来，传到后台去判断权限后再执行操作捏
            let access_token = JSON.parse(sessionStorage.getItem('access-token'));
            // console.log(access_token.token);

            let newDevice = {deviceId:this.$route.params.deviceId,name:this.name,status:null,startTime:'',closeTime:'',token:access_token.token};
            let apiUrl = "http://localhost:8080/api/deviceInfo/update";  //判断token的接口
            let _this = this;
            axios.put(apiUrl,newDevice).then(
            function(response){
                if(response.data.code == '0'){
                    _this.$router.push({path:'/home/device/info'});
                }else {
                    alert(response.data.msg);
                }
            },function(error){

            });


        }



    }

}
</script>
<style scoped>
.myForm{
    color:white;
}

</style>
