<template>
    <div class="container-fluid myContainer">
        <div class="row">
            <div class="col-sm-4">
                <div class="select">
                    <span class="span" >姓名</span>
                    <input type="text" class="form-control myInput" v-model="submitArgs.name">
                </div>
            </div>
            <div class="col-sm-4">
                <div class="select">
                    <span class="span" >班组</span>
                    <select class="custom-select mySelect" v-model="submitArgs.team">
                        <option selected class="myOption text-primary" value="1">管理班组</option>
                        <option value="2" class="myOption text-success">劳务班组</option>
                    </select>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="select">
                    <span class="span" >身份证号</span>
                    <input type="text" class="form-control myInput" v-model="submitArgs.idCard">
                </div>
            </div>
            <div class="col-sm-4">
                <div class="select">
                    <span class="span" >年龄</span>
                    <input type="text" class="form-control myInput" v-model="submitArgs.age">
                </div>
            </div>
            <div class="col-sm-4">
                <div class="select">
                    <span class="span" >性别</span>
                    <select class="custom-select mySelect" v-model="submitArgs.sex">
                        <option selected class="myOption text-primary" value="1">男</option>
                        <option value="0" class="myOption text-success">女</option>
                    </select>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="select">
                    <span class="span" >手机号</span>
                    <input type="text" class="form-control myInput" v-model="submitArgs.phone">
                </div>
            </div>
            <div class="col-sm-4">
                <div class="select">
                    <span class="span" >企业单位</span>
                    <input type="text" class="form-control myInput" v-model="submitArgs.enterprise">
                </div>
            </div>
            <div class="col-sm-4">
                <div class="select">
                    <span class="span" >工种</span>
                    <input type="text" class="form-control myInput" v-model="submitArgs.kind">
                </div>
            </div>
            <div class="w-100"></div>
            <div class="col-sm-4">
                <div class="select" style="height:140px;">
                    <span class="span" style="align-self:flex-start">个人照片</span>
                    <el-upload action="http://localhost:8080/api/worker/info/upload"
                                :limit="1" 
                                accept=".jpg"
                                :on-success="handleSuccess"
                                :on-error="handleError"
                                :file-list="fileList"
                                list-type="picture"
                                style="align-self:flex-start;width:70%;"
                    ><!--最多允许上传一个-->
                        <el-button size="small" type="primary">点击上传</el-button>

                    </el-upload>
                </div>
            </div>
            <div class="w-100"></div>
            <div class="col-sm-4">
                <i style="font-size:13px;color:white;margin-left:10px;">只支持上传.jpg格式，大小不超过20MB</i>
            </div>

            <div class="w-100"></div>
            <div class="col-sm">
            <div class="operate">
                <button type="button" class="btn btn-outline-primary" @click="updateWorker">
                    提交
                </button>

                <router-link to="/home/worker/info">
                <button type="button" class="btn btn-outline-danger" style="margin-left:15px;">
                    返回
                </button>
                </router-link>
            </div>
            </div>
        </div>
    </div>

</template>
<script>
import axios from 'axios'

export default {
    name:'UpdateWorker',
    data() {
      return {
          fileList:[],
          submitArgs:{
                id:0,
                name:'',  //null原来也可以绑定空的框框，这蛮好的
                enterprise:'',
                idCard:'',
                phone:'',
                team:'',
                age:'',
                kind:'',
                sex:'',
                photo:'',
                token:null,
                status:1
          }
      };
    },
    methods: {
        handleSuccess(response, file, fileList){
            let url = response.data;
            // console.log(url);
            //当文件上传成功后就会返回文件在服务器上的本地路径，然后把这个路径赋值给提交上去的对象。
            console.log(response);
            this.submitArgs.photo = url;
        },
        handleError(error,file,fileList){
            console.log(error);
        },
        judgeEmpty(){
            let args = this.submitArgs;
            if(args.name == '' || args.enterprise == '' || args.idCard==''||args.phone==''||args.team==''||
            args.age==''||args.kind==''||args.sex==''||args.photo==''){
                alert("请填写所有字段！");
                return false;
            }
            return true;
        },
        updateWorker(){
          if(this.judgeEmpty()){  

            let access_token = JSON.parse(sessionStorage.getItem('access-token'));
            // console.log(access_token.token);
            this.submitArgs.id = this.$route.params.id;  //获取到id捏
            this.submitArgs.token = access_token.token;

            let apiUrl = "http://localhost:8080/api/worker/info/update";  //判断token的接口
            let _this = this;

            console.log(this.submitArgs);

            axios.post(apiUrl,this.submitArgs).then(
            function(response){
                if(response.data.code == '0'){
                    _this.$router.push({path:'/home/worker/info'});
                }else {
                    alert(response.data.msg);
                }
            },function(error){

            });
          }else{
            return;
          }
      }



        
    },
    components:{
    }

}
</script>
<style scoped>
.myContainer{
    background: rgb(0 0 0 / 45%);
    border-radius: 10px;
    padding: 5px; 
    margin-bottom: 20px; 
}
.select{
    display: flex;
    align-items: center;
    /* justify-content: space-between; */
    padding: 10px 10px;
}
.span{
    width:30%;
    color:white;
    font-size: 18px;
    text-align: justify;/**实现分散对齐的技巧 */
    /* text-align-last:justify; */

}
.myInput{
    width:70%;
    /* height: 0%;  *只要设置了这个就会缩小一点固定的高度，倒不是真的直接变成0%了... */
    height: 30px;
    background:rgb(0 0 0 / 10%);
    color:white;

}
.operate{
    padding:10px 10px;
}
.mySelect{
    background-color:rgb(0 0 0 / 10%);
    color:white;
    width:65%;
    height: 30px;
    padding: 0px 12px;
    font-size: 14px;
    text-align: justify;
}
.myOption{
    background-color:rgb(0 0 0 / 80%);
    font-size: 16px;
}
</style>
