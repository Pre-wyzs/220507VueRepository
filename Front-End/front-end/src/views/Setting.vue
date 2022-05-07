<template>
    <div class="box" ref="box">
        <div class="bgSet" >
            <div class="list-group list-group-flush">
                <a class="list-group-item title" style="border:1px solid gray;text-decoration:none;">背景设置</a>
                <a class="list-group-item list-group-item-action myItem" @click="toggleBg(0)" id="one">蔚蓝科技风</a>
                <a class="list-group-item list-group-item-action myItem" @click="toggleBg(1)" id="two">赛博朋克风</a>
                <a class="list-group-item list-group-item-action myItem" @click="toggleBg(2)" id="three">清新自然风</a>
                <router-link to="/home" class="list-group-item list-group-item-action myItem" style="color:white;">返回</router-link>
            </div>
        </div>
        <div class="cache">
            <div class="list-group list-group-flush">
                <a class="list-group-item title" style="border:1px solid gray;text-decoration:none;">数据缓存</a>
                <a class="list-group-item list-group-item-action myItem" @click="clearLoginName" id="">清除登录缓存</a>
                <router-link to="/home" class="list-group-item list-group-item-action myItem" style="color:white;">返回</router-link>
            </div>

        </div>

    </div>

</template>
<script>

export default {
    name:'Setting',
    mounted(){
        var eStyle = this.$refs.box.style;
        let url = sessionStorage.getItem('bgUrl');
        if(url == null){
            url = '/img/bg.jpg';
        }
        eStyle.setProperty('--bgUrl','url('+url+') center');
    },
    data(){
        return{
            urls:['/img/bg.jpg','/img/good.jpg','/img/bg3.jpg'],
        }
    },
    computed:{
    },
    methods:{
        toggleBg(index){
            var eStyle = this.$refs.box.style;
            eStyle.setProperty('--bgUrl','url('+this.urls[index]+') center');
            sessionStorage.setItem('bgUrl',this.urls[index]);
            // let dom = JSON.parse(localStorage.getItem('dom'));
            // console.log(dom);
            // var el =  JSON.parse(localStorage.getItem('dom')).style;
            // console.log(el);
            // el.setProperty('--bgImg','url('+this.urls[index]+') center');
        },
        clearLoginName(){
            if(localStorage.getItem('loginNameBuffer')!=null&&localStorage.getItem('loginPwdBuffer')!=null){
                localStorage.removeItem('loginNameBuffer');
                localStorage.removeItem('loginPwdBuffer');
            }
        }
    }
}
</script>
<style scoped>
*{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
.title{
    font-size: 24px;
    font-weight: 400;
    /* color:red; */
    background-color:rgba(0,0,0,0.2); /*这里使用rgb(0 0 0 / 15%)这样的格式的话会被屏蔽掉的 */

    color:white;
}
.bgSet{
    width: 30%;
    padding-top:20px;
    margin-left: auto;
    margin-right: auto;
    text-align: center;
}
.cache{
    width: 30%;
    padding-top:30px;
    margin-left: auto;
    margin-right: auto;
    text-align: center;
}
.box{
  background: var(--bgUrl);
  background-size:100% 100%;
  background-color: #2e2f3f;
  width: 100%;
  height: 100vh;
}

.myItem{
    color:white;
    background-color:rgba(0,0,0,0.2); 
    cursor: pointer;  /*就是鼠标指针放在该框框上会变成一个手指的形状 */
    caret-color: rgba(0,0,0,0);  /*设置聚焦时候光标变为透明，这样就不会出现一闪一闪的效果了 */ 
    font-weight: bolder;

}

.myItem:hover{
    color:#0DC217;
    font-weight: bolder;
    background-color:rgba(0,0,0,0.5); 
}
/* .list-group-item.myItem:focus{
    color:red;
    background-color:rgba(0,0,0,0.5); 

} */

/* #one:focus{
    color:red;
    background-color:rgba(0,0,0,0.5); 
} */
/* #one:focus-within{
    color:red;
    background-color:rgba(0,0,0,0.5); 
}
#one:focus-visible{
    color:red;
    background-color:rgba(0,0,0,0.5); 
} */
/* #one:active{
    color:red;
    background-color:rgba(0,0,0,0.5); 
} */


</style>
