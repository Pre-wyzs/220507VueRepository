<template>
  <!-- 左侧边框的容器 -->
  <div :class="isShrink?'aside-wapper shrink':'aside-wapper'">
    <div :class="isShrink?'aside-header shrink':'aside-header'">
      <div>
        <img
          src="../../assets/images/logo-icon.png"
          class="logo-icon"
          alt="logo icon"
        />
      </div>
      <div v-show="!isShrink">
        <!-- 这就是那个很奇怪的图标了！ -->
        <h4 class="logo-text">智慧工地</h4>
      </div>
      <div class="toggle-icon" @click="stretchFun" v-show="!isShrink">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="28"
          height="28"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
          class="feather feather-chevrons-left text-white"
        >
          <polyline points="11 17 6 12 11 7"></polyline>
          <polyline points="18 17 13 12 18 7"></polyline>
        </svg>
      </div>
    </div>
    <!-- end of aside-header -->

      <aside-item ref="child_1"/>
  </div>
</template>
<script>
import AsideItem from './aside_Item.vue'
export default {
  name: "NavAside",
  components: {
    AsideItem

  },
  data() {
    return {
      isShrink:false  //是否是收缩的，默认是false
    }
  },
  methods: {
    //伸缩功能的实现
    stretchFun() {
      // for(var i=0;i<7;i++){
      //   if(this.$children[0].flags[i] == true) {
      //     this.$children[0].flags[i] = false;
      //   }
      // }

      this.$refs.child_1.closeAll();
      this.isShrink = !this.isShrink;
      this.$parent.isShrink = this.isShrink;
      console.log(this.$parent.isShrink);
    }
  }
};
</script>
<style scoped>
.aside-wapper {
  width: 250px;
  height: calc(100% - 60px);
  position: fixed; /*相对于浏览器窗口进行定位：难怪组件可以一个一个的引入而不必担心他的布局了！ */
  top: 60px;
  bottom: 0;
  left: 0;
  background: rgb(0 0 0 / 15%);
  border-right: 1px solid rgb(255 255 255 / 15%);
  z-index: 11; /*z轴的高度...*/
  box-shadow: 0 0.3rem 0.8rem rgba(0, 0, 0, 0.12) !important;
  transition: all 0.2s ease-out; /*所有属性，在0.2秒内，由慢到快的变化*/


}

.aside-header {
  width: 250px;
  height: 60px;
  display: flex;
  align-items: center; /*将弹性 <div> 元素的所有项目的居中对齐： */
  position: fixed;
  top: 0;
  bottom: 0;
  padding: 0 15px;
  z-index: 5;
  background: rgb(255 255 255 / 15%);
  border-bottom: 1px solid rgb(255 255 255 / 12%);
  transition: all .2s ease-out;
}

.logo-icon {
  width: 30px;
  margin-top: 3px;
}

.logo-text {
  font-size: 22px;
  margin-left: 30px;
  margin-bottom: 0;
  letter-spacing: 1px; /*字母间距属性 */
  color: #ffffff;
}

.toggle-icon {
  cursor: pointer; /* 就是鼠标移动到这个元素上的时候的光标的样式 */
  color: #ffffff;
  position: relative;
  left: 40px;
  top: 4px;
}

.shrink {
  width: 70px;
}




</style>
