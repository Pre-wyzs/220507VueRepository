<template>
  <!-- 警告提示 -->
  <div class="alert">
    <div>
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-alert-triangle align-middle mr-2"><path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"></path><line x1="12" y1="9" x2="12" y2="13"></line><line x1="12" y1="17" x2="12.01" y2="17"></line></svg>
    </div>
    <div class="alert-content">{{errMsg}}</div>
  </div>
</template>
<script>
export default {
  name: "AlertBox",
  props:['errMsg'],  //传进来的错误参数
  components: {

  },
};
</script>
<style scoped>
.alert {
  display: flex;
  align-items: center;
  justify-content: left;
  position: relative;
  color: red;
  background-color: rgba(255, 0, 0, 0.2);
  border: 1px solid red;
  border-radius: 25px;
  height: 25px;
  width: 45%;  /*40%*/
  margin-top: 5px;
}
.alert-content {
  font-size: 10px;
}

</style>
