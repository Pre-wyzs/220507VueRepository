<template>
  <div class="home" ref="home">
    <Aside/>
    <Header :userName="userName" :power="power"/>
    <Main :isShrink="isShrink"/>
  </div>

  

</template>

<script>
// @ is an alias to /src
import Aside from '../components/home/aside.vue'  //大小写要注意
import Header from '../components/home/header.vue'
import Main from '../components/home/main.vue'

export default {
  name: 'Home',
  data() {
    return {
      userName:'',  //用户名
      power:'',  //用户权限
      isShrink:false,
      elStyle:null,
    }
  },
  created(){
    this.userName = sessionStorage.getItem('loginName');
    this.power = sessionStorage.getItem('power');
  },
  mounted(){
    let el = this.$refs.home.style;
    this.elStyle = el;
    //把这个el对象存储到本地的变量中去
    // console.log(el);
    // console.log(this.$refs.home);
    // localStorage.setItem('dom',JSON.stringify(this.$refs.home));
    // console.log(JSON.stringify(this.$refs.home));
    // console.log(localStorage.getItem('dom'));
    // console.log(this.getElStyle);
    let url = sessionStorage.getItem('bgUrl');
    if(url == null){
      url = '/img/bg.jpg';
    }
    el.setProperty('--bgImg','url('+url+') center');
  },

  computed:{
  },
  methods:{
  },
  // beforeRouteEnter:(to,from,next) => {
  //   userName = sessionStorage.getItem('userName');
  //   console.log(userName);
  //   next();
  // },
  components: {
    Aside,
    Header,
    Main
  }
}
</script>
<style scoped>
.home {
  background:var(--bgImg); 
  background-size:100% 100%;
  width: 100%;
  height: 100vh;
}

</style>

