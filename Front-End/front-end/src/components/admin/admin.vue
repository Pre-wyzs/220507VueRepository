<template>
  <div style=" background: rgb(0 0 0 / 45%); border-radius: 10px; padding: 5px; margin-bottom: 20px; " >
    <div class="search">
      <div class="search_1">
        <div style="margin: 0px 18px">ID查询</div>
        <div style="margin-right: 20px">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search text-white" > <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
        </div>
        <input type="text" name="" id="" placeholder="" v-model="id" class="myInput" />
        <div style="margin-left: 20px">
          <button type="button" class="btn btn-primary btn-sm" @click="getCurretnPageUsers(1)" >
            查询
          </button>
        </div>
      </div>
      <div class="search_1">
        <div style="margin: 0px 10px">权限查询</div>
        <div style="margin-right: 20px">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search text-white" > <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
        </div>
        <input type="text" name="" id="" placeholder="" v-model="power" class="myInput" />
        <div style="margin-left: 20px">
          <button type="button" class="btn btn-primary btn-sm" @click="getCurretnPageUsers(1)" >
            查询
          </button>
        </div>
      </div>
    </div>

    <div class="search">
      <div class="search_1">
        <div style="margin-right: 5px">用户名精确</div>
        <div style="margin-right: 20px">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search text-white" > <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
        </div>
        <input type="text" name="" id="" placeholder="" v-model="nameSure" class="myInput" />
        <div style="margin-left: 20px">
          <button type="button" class="btn btn-primary btn-sm" @click="getCurretnPageUsers(1)" >
            查询
          </button>
        </div>
      </div>
      <div class="search_1">
        <div style="margin-right: 5px">用户名模糊</div>
        <div style="margin-right: 20px">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search text-white" > <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
        </div>
        <input type="text" name="" id="" placeholder="" v-model="nameFuzzy" class="myInput" />
        <div style="margin-left: 20px">
          <button type="button" class="btn btn-primary btn-sm" @click="getCurretnPageUsers(1)" >
            查询
          </button>
        </div>
      </div>
    </div>
  </div>

  <div style="margin: 20px 0px; background: rgb(0 0 0 / 45%); border-radius: 10px" >
    <router-link to="/home/addUser">
      <button type="button" class="btn btn-primary btn-sm" style="margin: 10px 20px; border-radius: 5px" >
        新增用户
      </button>
    </router-link>
  </div>

  <table class="table table-dark table-striped table-hover my-table">
    <thead>
      <tr>
        <th class="text-primary">ID</th>
        <th class="text-primary">用户名</th>
        <th class="text-primary">权限</th>
        <th class="text-primary">操作</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(user, index) in powerConvert" :key="index">
        <td>{{ user.id }}</td>
        <td>{{ user.name }}</td>
        <td>{{ user.power }}</td>
        <td>
          <button type="button" class="btn btn-success btn-sm" style="margin-right: 10px" @click="toModify(user.id)" >
            修改
          </button>
          <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#exampleModal" @click="delUser(user.id)" >
            删除
          </button>
        </td>
      </tr>
    </tbody>
  </table>

  <div class="partPages">
        <div class="scroll">
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <li class="page-item" @click="upGroup(cgroup)">
                        <a class="page-link" href="javascript:;" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item" v-for="(item,index) in partPages(cgroup)" :key="index" @click="getCurretnPageUsers(item)">
                        <a class="page-link" href="#" >{{item}}</a>
                    </li>
                    <li class="page-item" @click="nextGroup(cgroup)">
                        <a class="page-link" href="javascript:;" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>

        </div>
        <div style=" color: White; margin-left: 10px; align-self: flex-start; margin-top: 7px;font-size:20px;">
            当前页：{{cpage}}/{{totalPages}}
        </div>
        <div style=" color: White; margin-left: 10px; align-self: flex-start; margin-top: 8px;font-size:20px;">
            跳转到：
        </div>
        <div class="input-group-sm" style="margin:0px 0px;width:7%;padding-top:7px;">
            <input type="text" class="form-control" v-model="jump" @keydown.enter="jumpTo(jump)"/>
        </div>
  </div>
</template>
<script>
import {  createNamespacedHelpers } from 'vuex' //引入辅助函数
const { mapState, mapGetters, mapActions, mapMutations } = createNamespacedHelpers('user');;

import axios from "axios";

export default {
  name: "Admin",
  data() {
    return {
      PPath: "用户管理",
      CPath: "管理员信息",
      cpage: "1",
      cgroup:0,
      jump:1,
      id: "", //用户id信息
      power: "",
      nameSure: "",
      nameFuzzy: "",
    };
  },

  /**OOOOO当网页每次重新加载的时候，这个对象本身都会重新生成并且执行这个函数*/
  created() {
    this.asyncGetUsers("http://localhost:8080/api/admin/all");
  },

  // beforeRouteEnter:(to,from,next)=>{   //1、注意在进入前，我devue实例还没有被初始化,所以不能直接调用自己的属性和方法！！！
  //   //console.log(to.path);
  //   next(vm => {  //2、这就是说当页面渲染完成后再拿到自己的vm实例，才能调用自己的其他方法！
  //       vm.deliveryPath(to.path);
  //   });
  // },

  computed: {

    ...mapState(["users", "totalPages_user"]),
    ...mapGetters(["powerConvert", "totalPages"]),
  },
  methods: {
    ...mapMutations(["getUsers"]),
    ...mapActions(["asyncGetUsers"]),
    delUser(id) {
      let access_token = JSON.parse(sessionStorage.getItem("access-token"));
      // console.log(access_token.token);

      let delUser = {
        id: id,
        name: null,
        password: null,
        power: null,
        token: access_token.token,
      };
      let apiUrl = "http://localhost:8080/api/admin/del"; //判断token的接口
      let _this = this;
      axios.post(apiUrl, delUser).then(
        function (response) {
          if (response.data.code == "0") {

           _this.asyncGetUsers("http://localhost:8080/api/admin/general?id=" 
           + _this.id + "&power=" + _this.power + "&nameSure=" + _this.nameSure + "&nameFuzzy=" + 
           _this.nameFuzzy + "&pageNum=" + _this.cpage);


          } else {
            alert(response.data.msg);
          }
        },
        function (error) {}
      );
    },

    getCurretnPageUsers(pageNum) {
      //这个参数是说明分页的具体类型的..
      let apiUrl;
      apiUrl = "http://localhost:8080/api/admin/general?id=" + this.id + "&power=" + this.power + "&nameSure=" + this.nameSure + "&nameFuzzy=" + this.nameFuzzy + "&pageNum=" + pageNum;
      console.log(apiUrl);

      let that = this;
      axios.get(apiUrl).then(
        function (response) {
          let rets = response.data;
          let users = rets.data;

          if (users.length == 0) {
            alert("该用户不存在！");
          } else {
            //用户存在时才去更新vuex中的仓储数据
            that.getUsers({ users: users, totalPages: rets.totalPages });
            //并且更新页码。。。
            that.cpage = pageNum;
          }
        },
        function (error) {}
      );
    },
    toModify(id) {
      this.$router.push({ name: "home-updateUser", params: { id: id } });
    },
  partPages(cgroup){
            let allArr = [];
            let retArr = [];
            for(let i = 0;i < this.totalPages;i++){
                allArr.push(i + 1);
            }

            if(allArr.length <=5){
                retArr = allArr;
            }else{
                retArr = allArr.slice(5*(cgroup),5*(cgroup+1));
            }
            return retArr;
        },
        nextGroup(cgroup){
            let allGroup = -1;
            //先计算出allGroup所有的组数
            if(this.totalPages%5 == 0){
                allGroup = this.totalPages/5;
            }else{
                allGroup = this.totalPages/5 + 1;
            }
            //再判断当前组数+1后是否会大于总组数
            if(cgroup+1 > allGroup-1){
                this.cgroup = cgroup;
            }else{
                this.cgroup = cgroup + 1;
            }
        },
        upGroup(cgroup){
            if(cgroup-1 < 0){
                this.cgroup = cgroup;
            }else{
                this.cgroup = cgroup - 1;
            }
        }, 
        jumpTo(jump){
            //如果要跳转的话先要判断一下要跳转到的页码的是否在范围内
            //不在范围内的话就退出函数吧
            if(jump < 1 || jump > this.totalPages){
                alert('页码超出范围!');
                return;
            }

            let groupIndex = 0;
            //要求页面跳转的时候，页码组也要跟着联动是吧...
            //那得先确定它是哪一个组的...
            if(jump%5==0){
                groupIndex = Math.floor(jump/5) - 1;
            }else{
                groupIndex = Math.floor(jump/5);
            }

            // console.log(groupIndex);
            this.cgroup = groupIndex;
            this.getCurretnPageUsers(jump);
        },



    // //这个接口是用来根据id查询用户的
    // selectUserById(id){
    //   let apiUrl = "http://localhost:8080/api/admin/id/" + id;
    //   let that = this;
    //   axios.get(apiUrl).then(
    //     function (response) {
    //       console.log(response);
    //       let rets = response.data;
    //       let user = rets.data;
    //       //2、错误打印对象
    //       console.log('你好' + user);  //一个josn对象再加上字符串的话就显示不出来了
    //       that.getUsers({users:user,totalPages:rets.totalPages});
    //     },
    //     function (error) {
    //       console.log(error);
    //     }
    //   );

    // }
  },
  components: {
  },
};
</script>

<style scoped>
.my-table {
  border-radius: 10px;
  background: rgb(0 0 0 / 65%);
  border: 2px solid rgba(0, 0, 0, 0.12);
  box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.12);
  caret-color: rgba(0, 0, 0, 0);
}
.scroll {
  overflow-x: auto;
  max-width: 100%;
}

div.scroll::-webkit-scrollbar {
  width: 3px;
  /* background: linear-gradient(  //这段样式的设置是无效的，有Bug的操！！！
    hsl(0deg,100%,50%,0.4) 0%,
    hsl(40deg,100%,50%) 20%,
    hsl(80deg,100%,50%) 30%,
    hsl(120deg,100%,50%) 40%,
    hsl(180deg,100%,50%) 50%,
    hsl(250deg,100%,50%) 80%,
    hsl(320deg,100%,50%,0.4) 100%,
  ); */
  background: rgba(0, 0, 0, 0.3);
  border-radius: 4px;
}

div.scroll::-webkit-scrollbar-thumb {
  /* background:linear-gradient(
    hsl(0deg,100%,50%,0) 0%,
    hsl(0deg,100%,100%,0.9) 20%,
    hsl(0deg,100%,100%,0.9) 80%,
    hsl(0deg,100%,50%,0) 100%,
  ); */
  background: rgba(255, 255, 255, 0.3);
  border-radius: 5%;
}

.partPages {
  display: flex;
  justify-content: flex-end;
  position: absolute;
  bottom:20px;
  right: 20px;
}

.search {
  display: flex;
  justify-content: space-around;
  margin: 20px 0px;
}
.search_1 {
  color: white;
  font-size: 18px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.myInput {
  background: rgb(255 255 255 / 10%);
  color: white;
}
</style>