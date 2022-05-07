<template>
<!-- 头部的导航栏 -->
    <header>
        <div class="header">
            <div  @click="openOpt" >
                <div class="user">
                    <img src="../../assets/images/touxiang.jpeg" alt="" width="45px" height="45px">
                    <div class="user-info">
                        <div class="info-name">
                            {{userName}}
                            <!-- 章子唯 -->
                        </div>
                        <div :class="authority">
                            {{power}}
                            <!-- Super Root -->
                        </div>
                    </div>
                </div>
                <header-opt :isOpt="isOpt"/>
            </div>
        </div>
    </header>

</template>
<script>
import HeaderOpt from './header_opt.vue'

export default {
    name:'NavHeader',
    props:['userName','power'],
    components:{
        HeaderOpt
    },
    data() {
        return {
            isOpt:false,
            authority:'info-authority0',
        }
    },
   
    mounted() {
        // console.log('权限：'+this.power);
        if(this.power === '超级管理') {
            // console.log('超级用户');
        }else if (this.power === '普通管理') {
            this.authority = 'info-authority1';
        }else {
            this.authority = 'info-authority2';
        }
    },
    methods: {
        openOpt() {
            this.isOpt =! this.isOpt;
        },

    }

}
</script>
<style scoped>
.header {
    width:100%;
    height: 60px;
    background: rgb(0 0 0 / 15%);
    display: flex;
    flex-direction: row-reverse;
}
.user {
    height: 100%;
    width: 200px;
    border-left:1px solid rgb(255 255 255 / 25%);
    display: flex;
    align-items: center;
    justify-content: space-around;
    color: white;
    cursor: pointer;  /*就是鼠标指针放在该框框上会变成一个手指的形状 */
}
.user img {
    border-radius: 50%;
    caret-color: rgba(0,0,0,0); 
}
.info-name {
    caret-color: rgba(0,0,0,0);

}


/**超级用户权限的样式 */
.info-authority0{
    font-size: 18px;
    color: #FFEB3B;
    caret-color: rgba(0,0,0,0);  /*设置聚焦时候光标变为透明，这样就不会出现一闪一闪的效果了 */ 
    text-shadow:0 0 10px #FFEB3B,
                0 0 20px #FFEB3B,
                0 0 30px #FFEB3B,
                0 0 50px #FFEB3B;
}

/**普通用户的样式*/
.info-authority2{
    font-size: 18px;
    color: #03A9F4;
    caret-color: rgba(0,0,0,0);  /*设置聚焦时候光标变为透明，这样就不会出现一闪一闪的效果了 */ 
    text-shadow:0 0 10px #03A9F4,
                0 0 20px #03A9F4,
                0 0 30px #03A9F4,
                0 0 50px #03A9F4;
}
/**普通管理员权限的样式*/
.info-authority1{
    font-size: 18px;
    color: #76FF03;
    caret-color: rgba(0,0,0,0);  /*设置聚焦时候光标变为透明，这样就不会出现一闪一闪的效果了 */ 
    text-shadow:0 0 10px #76FF03,
                0 0 20px #76FF03,
                0 0 30px #76FF03,
                0 0 50px #76FF03;
}

.user img {
    width:45px;
    height: 45px;
    /**之所以在这里设置图片的大小，是因为使用了{{}}语法后，原来的img中的宽和高就不生效了...我也不知道为什么？？？ */
}
</style>
