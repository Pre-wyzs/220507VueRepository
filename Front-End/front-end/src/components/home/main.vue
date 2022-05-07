<template>
  <div :class="isShrink ? 'main shrink' : 'main'">

    <router-view></router-view>
  </div>
</template>
<script>
import Admin from "../admin/admin.vue";

export default {
  name: "Main",
//   beforeRouteEnter:(to,from,next)=>{  路由钩子要生效必须要满足它在路由配置用有存在啊，我的吗这都不懂...
//       console.log('到哪里去>' + to.path);
//       next();
//   },
  props: ["isShrink"],
  data() {
      return {
          currentPath:{pPath:'视频监控',cPath:'实时监控'}
      }
  },
  components: {
    Admin,
  },
};
</script>
<style scoped>
.main {
  /* display: fixed; 不知道为什么这个会被覆盖掉的...*/
  /* display: relative; 这个也会被覆盖掉*/
  /* display: flex;  *这个是唯一有效的布局？？为什么呢？ */
  height: calc(100vh - 60px);
  margin-left: 250px;
  transition: all 0.2s ease-out;
  padding: 20px;
}
.shrink {
  margin-left: 70px;
}


</style>
