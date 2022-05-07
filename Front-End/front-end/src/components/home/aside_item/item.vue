<template>
     <!-- 项目一 -->
      <li class="li-1" id="li-1">
        <a href="javascript:;" :class="isList0?'menu-btn menu-btn-list':'menu-btn'" @click="listItems">
          <div class="list-icon" >
              <slot></slot>
          </div>
          <div class="list-title">{{title}}</div>
          <div class="open-icon" :style="isList0?'transform:rotate(-90deg);':''">
            <svg v-if="!this.$parent.$parent.isShrink" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-chevron-left text-white" >
              <polyline points="15 18 9 12 15 6"></polyline>
            </svg>
          </div>
        </a>
        <!-- 子项目-start -->
        <transition>
            <ul class="sub-menu" id="sub-menu" v-show="isList0">
                <li class="li-2" id="li-2" v-for="(item,index) in subItems" :key="item"> <!--:key这一段必须要加上，不然会报错，在2.2.0+以后的强制设置-->
                    <a :href="item.path" class="sub-menu-btn" :id="'a'+index" @click="this.$parent.saveListStatus()">
                        <div class="sub-list-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-more-horizontal text-white"><circle cx="12" cy="12" r="1"></circle><circle cx="19" cy="12" r="1"></circle><circle cx="5" cy="12" r="1"></circle></svg>
                        </div>
                        <div class="sub-list-title">
                            {{item.module}}
                        </div>
                    </a>
                </li>
            </ul>
        </transition>
        <!-- 子项目-end -->
      </li>

</template>

<script>

export default {
    name:'Item',
    props:['title','subItems','id0','isList0'],
    data() {
        return {
            isList:this.isList0,
            id:this.id0  
        }
    },
    computed:{
        // listItems(){
        //     //this.isList0 = ! this.isList0;
        //     this.$parent.handle(this.id);  //2、通过这种方式向父组件传值是最快的
        // }
    },
    methods:{
        listItems(){
            var e =document.getElementsByClassName('sub-menu')[0].style;
            e.setProperty('--height',this.subItems.length*40+'px');
            // console.log(e);

            // console.log(this.subItems.length*40);
            if(this.$parent.$parent.isShrink == true) {
                let grandPa = this.$parent.$parent;
                grandPa.isShrink = false;
                grandPa.$parent.isShrink = false;

            }
            //console.log(this.isList);
            //this.$emit('myevent','I was opened!');
            this.$parent.handle(this.id);  //2、通过这种方式向父组件传值是最快的
            // if(this.isList == true) {
            //     setTimeout(function () {
            //         //在子项目加载完成后，模拟点击第一个子项目
            //         //document.getElementById('a0').focus();
            //         //console.log('延迟执行');
            //     }, 500);
            // }


        },

    },
    components:{
    }

}
</script>
<style scoped>

.li-1 {
    margin-top:5px;
}  
.li-1 .menu-btn {
  display: flex;
  text-decoration: none;
  color: white;
  height: 45px;
  border: 1px solid #ffffff00;
  transition: all 0.3s ease-out;
  align-items: center;

}

.li-1 a:focus {  /*a:focus表示获得焦点的时候 or 鼠标松开时显示的颜色 */
  color: #ffffff;
  text-decoration: none;
  background: rgb(255 255 255 / 15%);
}

.li-1 a:hover {  /*a:hover表示鼠标悬浮在标签上时候的样式 */
  color: #ffffff;
  text-decoration: none;
  background: rgb(255 255 255 / 15%);
}
/*这个样式在展开子列表的时候进行展示！ */
.menu-btn-list {
  color: #ffffff;
  text-decoration: none;
  background: rgb(255 255 255 / 15%);
}


.menu-btn .list-icon {
  position: relative;
  left: 10px;
  /* padding-top: 10px; */
}

.menu-btn .list-title {
  position: relative;
  left: 20px;
  /* padding-top: 10px; */
}

.menu-btn .open-icon {
  position: relative;
  left: 50%;
  /* padding-top: 10px; */
  transform: rotate(0deg);
  transition: transform .3s ease-out;
}

.sub-menu {
    border: 1px solid rgb(255 255 255 / 12%);
    background: rgb(255 255 255 / 10%);
    transition: all .2s ease-out; 
    /** */
}


.sub-menu a {
    display: flex;
    color: white;
    text-decoration: none;
    align-items: center;
    /* justify-content: center; */
    height: 30px;
    border: 1px solid #ffffff00;
    /* /transition: all 0.3s ease-out; */
    margin-top:5px;
    margin-bottom:5px;
}
.sub-menu  a .sub-list-icon {
    position: relative;
    left: 15px;
}
.sub-menu  a .sub-list-title {
    position: relative;
    left: 20px;
    font-size: 14px;
}

/**v-if：过渡动画 */
.v-enter-from {
    opacity: 0;
    height: 0px;
}

/* .v-enter-active {
    /* 这部分它自己已经定义了 */
    /* transition: all 10 linear; 
} */

.v-enter-to {
    opacity: 1;
    height: var(--height);
    border: 1px solid rgb(255 255 255 / 20%);
}
/**v-if：过渡动画 */
.v-leave-from {
    opacity: 1;
    height: 80px;
}

/* .v-leave-active {
    transition: all .1s ease-out;
} */

.v-leave-to {
    opacity: 0;
    height: 0px;
}

</style>
