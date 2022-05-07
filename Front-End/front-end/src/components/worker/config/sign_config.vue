<template>
    <div class="container-fluid myContainer">
        <div class="row">
            <div class="col-sm-6">
                <div class="select">
                    <span class="span">设置入场考勤时间</span>
                    <input type="time" class="form-control myInput" v-model="config.inTime">
                </div>
                <div class="select">
                    <span class="span">设置出场考勤时间</span>
                    <input type="time" class="form-control myInput" v-model="config.outTime">
                </div>
            </div>
            <div class="w-100"></div>
            <div class="col-sm-6">
                <div class="operate">
                <div class="btn-group">
                    <button type="button" class="btn btn-outline-primary btn-sm" @click="setSignTimes()">应用</button>
                    <router-link to="/home/worker/sign">
                    <button type="button" class="btn btn-outline-success btn-sm">
                        返回
                    </button>
                    </router-link>
                </div >
                </div>
            </div>
        </div>
    </div>


    
</template>
<script>
import axios from 'axios'

export default {
    name:'SignConfig',
    created(){
        if(localStorage.getItem('inTime')!=null&&localStorage.getItem('outTime')!=null){
            this.config.inTime = localStorage.getItem('inTime');
            this.config.outTime = localStorage.getItem('outTime');
        }
    },
    data(){
        return {
            config:{
                inTime:'08:30',
                outTime:'21:30',
            }
        }
    },
    methods:{
        setSignTimes(){
            console.log(this.config.inTime);
            console.log(this.config.outTime);

//清除缓存
            if(localStorage.getItem('inTime')!=null&&localStorage.getItem('outTime')!=null){
                localStorage.removeItem('inTime');
                localStorage.removeItem('outTime');
            }

            //1、验证参数
            if(this.config.inTime == '' || this.config.outTime == ''){
                alert("字段不能为空！");
                return;
            }

            this.config.inTime = this.config.inTime.concat(':00');
            this.config.outTime = this.config.outTime.concat(':00');
            let _this = this;

            axios.post("http://localhost:8080/api/worker/setting/sign",this.config).then(function(response){
                let rets = response.data;
                console.log(rets);
                if(rets.code == '0'){
                    alert("设置成功");
                    localStorage.setItem('inTime',_this.config.inTime);
                    localStorage.setItem('outTime',_this.config.outTime);

                }else{
                    alert("设置失败");
                }

            },function(error){}
            );
        }
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
.myInput{
    width:70%;
    /* height: 0%;  *只要设置了这个就会缩小一点固定的高度，倒不是真的直接变成0%了... */
    height: 25px;
    background:rgb(0 0 0 / 10%);
    color:white;

}
.operate{
    padding: 10px 10px;
}
.span{
    width:30%;
    color:white;
    font-size: 16px;
    text-align: justify;/**实现分散对齐的技巧 */
    /* text-align-last:justify; */

}

</style>
