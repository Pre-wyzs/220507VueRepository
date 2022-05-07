<template>
    <div class="search">
        <div>
            <div class="name">
                <div style="margin:0px 5px;font-size:18px;color:white">
                    设备名精确
                </div>
                <div class="icon" style="margin:0px 10px;">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search text-white" > <circle cx="11" cy="11" r="8"></circle> <line x1="21" y1="21" x2="16.65" y2="16.65"></line> </svg>
                </div>
                <div class="input-group-sm" style="margin:0px 10px;">
                    <input type="text" class="myInput form-control" v-model="nameSure"/>
                </div>
                <button type="button" class="btn btn-primary btn-sm" style="margin:0px 10px;" @click="getCurrentPageDevices(1)">查询</button>
            </div>
            <div class="name">
                <div style="margin:0px 5px;font-size:18px;color:white">
                    设备名模糊
                </div>
                <div class="icon" style="margin:0px 10px;">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search text-white" > <circle cx="11" cy="11" r="8"></circle> <line x1="21" y1="21" x2="16.65" y2="16.65"></line> </svg>
                </div>
                <div class="input-group-sm" style="margin:0px 10px;">
                    <input type="text" class="myInput form-control" v-model="nameFuzzy"/>
                </div>
                <button type="button" class="btn btn-primary btn-sm" style="margin:0px 10px;" @click="getCurrentPageDevices(1)">查询</button>
            </div>
        </div>
        <!--分割线，下面这部分是选项框的内容了-->
        <!--  -->
        <!-- <div style="color:white">你好呀{{selected}}</div> -->
        <div style="width:40%;align-self:flex-start;padding-top:20px;">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <label class="input-group-text text-primary myLabel" for="inputGroupSelect01">在线/离线</label>
                </div>
                <select class="custom-select mySelect" id="inputGroupSelect01" v-model="status" @change="getCurrentPageDevices(1)">
                    <option selected class="myOption text-primary" value="">所有</option>
                    <option value="1" class="myOption text-success">在线</option>
                    <option value="0" class="myOption text-danger">离线</option>
                </select>
            </div>

        </div>

    </div>

  <div style="margin: 20px 0px; background: rgb(0 0 0 / 45%); border-radius: 10px">
    <div class="btn-group" style="margin:10px 20px;">
        <router-link to="/home/device/add">
            <button type="button" class="btn btn btn-outline-success">新增设备</button>
        </router-link>
        <button type="button" class="btn btn btn-outline-primary" @click="copyTable()" data-toggle="modal" data-target="#copyModal">拷贝数据</button>
                    <div class="modal fade" id="copyModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body text-center text-secondary tips" data-dismiss="modal">
                            数据已拷贝到剪切板。
                        </div>
                    </div>
                </div>
            </div>
        


        <button type="button" class="btn btn btn-outline-primary" @click="exportExcel(xlsx)">Excel</button>
    </div>
  </div>


<table class="table table-dark table-striped table-hover my-table" id="data-table">
    <thead>
      <tr>
        <th class="text-primary">设备ID</th>
        <th class="text-primary">设备名称</th>
        <th class="text-primary">运行状态</th>
        <th class="text-primary">启动时间</th>
        <th class="text-primary">关闭时间</th>
        <th class="text-primary">操作</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(item,index) in deviceStatusConvert" :key="index">
        <td>{{item.deviceId}}</td>
        <td>{{item.name}}</td>
        <!-- <div style="display:none">{{item.status == 1?status=1:status=0}}</div> -->
        <td :style="item.status=='在线'?'color:#76FF03;':'color:red;'">{{item.status}} <div :class="deviceStatusConvert[index].status=='在线'?online:outline"></div></td>
        <td>{{item.startTime}}</td>
        <td>{{item.closeTime}}</td>
        <td>
              <router-link :to="{name:'DeviceModify',params:{deviceId:deviceStatusConvert[index].deviceId}}">
          <button type="button" class="btn btn-outline-success btn-sm" style="margin-right: 10px">
                修改
          </button>
              </router-link>
          <button type="button" class="btn btn-outline-danger btn-sm" data-toggle="modal" data-target="#one">
            删除
          </button>
            <div class="modal fade" id="one">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body text-center text-secondary tips">
                            你确定要删除这条记录吗？
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-warning" data-dismiss="modal" @click="delDevice(deviceStatusConvert[index].deviceId)">确认</button>
                            <button type="button" class="btn btn-outline-primary" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>

        </td>
      </tr>
    </tbody>
  </table>

  <div class="partPages ">
    <div class="scroll">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item" @click="upGroup(cgroup)">
                    <a class="page-link" href="javascript:;" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li class="page-item" v-for="(item,index) in partPages(cgroup)" :key="index">
                    <a class="page-link" href="#" @click="getCurrentPageDevices(item)">{{item}}
                    </a></li>
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
import axios from 'axios'
import {  createNamespacedHelpers } from 'vuex' //引入辅助函数
const { mapState, mapGetters, mapActions, mapMutations } = createNamespacedHelpers('device');;
import * as XLSX from 'xlsx'


export default {
    name:'DeviceInfo',
    created() {
        this.asyncGetDevices("http://localhost:8080/api/deviceInfo/general?nameSure=&nameFuzzy=&status=");

        //测试能不能在axios后就立即获取到数据；答案是不能的...因为是异步的方法；


        /**建立和后端的一个 webSocket连接*/
        var socket = new WebSocket("ws://localhost:8080/ws/api/equipment");
        let _this = this;
        socket.onopen = function(){
            console.log('websocket linked');
            //成功建立链接后发送这个连接的唯一名称
            socket.send("DeviceInfo-Linking");
        }

        /**在这个钩子函数中开启socket的监听事件 ：当服务器的session向它发送消息的时候，自然就会调用到这个回调函数了*/
        socket.onmessage = function(evt){
            //服务端返回的就是字符串，得通过evt.data来获取
            let retStr = evt.data;

            if(retStr == 'Status_Updated'){
                //重新更新页面捏，不是从websocket接口中直接拿数据，有点捞捏!

                //因为我们知道执行到这里，一定是有数据被更新的，无论被更新的数据是不是本页面的，都刷新一下就行了
                _this.asyncGetDevices("http://localhost:8080/api/deviceInfo/general?nameSure="+_this.nameSure+"&nameFuzzy="+
            _this.nameFuzzy+"&status="+_this.status+"&pageNum="+_this.cpage);
            }

        }

        socket.onclose = function(){
            console.log('websocket closed');
        }

        socket.error = function(){
            console.log('websocket error');
        }

    },
    data(){
        return {
            //当前的页码
            cpage:1,
            //当前页组数
            cgroup:0,
            //设备在线和离线的一个标志位
            online:'spinner-grow text-success spinner-grow-sm',
            outline:'spinner-grow text-danger spinner-grow-sm',
            //接口参数的设置
            nameSure:'',
            nameFuzzy:'',
            status:'',  /**这个值不能传null，不然会报400的错误！！ */
            //下拉列表是否选中的标志位
            selected:'',

            //跳转到第几页
            jump:1

        }
    },
    computed:{
        ...mapState(["devices", "totalPages_device"]),
        ...mapGetters(["deviceStatusConvert", "totalPages"]),
    },
    methods:{
        ...mapMutations(["getDevices"]),
        ...mapActions(["asyncGetDevices"]),

        getCurrentPageDevices(pageNum) {
            let apiUrl;
            apiUrl = "http://localhost:8080/api/deviceInfo/general?nameSure="+this.nameSure+"&nameFuzzy="+
            this.nameFuzzy+"&status="+this.status+"&pageNum="+pageNum;
            console.log(apiUrl);

            let that = this;
            axios.get(apiUrl).then(
                function (response) {
                let rets = response.data;
                let devices = rets.data;

                if (devices.length == 0) {
                    alert("该设备不存在");
                } else {
                    //在设备信息存在时去更新仓储
                    that.getDevices({ devices: devices, totalPages: rets.totalPages });
                    //并且更新页码。。。
                    that.cpage = pageNum;
                }
                },
                function (error) {}
            );
        },
                //通过函数确定上一页或下一页的页码值捏
        // upPage(cpage){
        //     if(cpage > 1){
        //         return cpage - 1;
        //     }else {
        //         return 1;
        //     }
        // },
        // nextPage(cpage){
        //     if(cpage < this.totalPages){
        //         return cpage + 1;
        //     }else {
        //         return this.totalPages;
        //     }
        // },

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
            this.getCurrentPageDevices(jump);
        },



        getOption(option){
            if(option == -1){
                this.status = ''
            }else if(option == 1){
                this.status = '1'
            }else{
                this.status = '0'
            }
            this.getCurrentPageDevices(1);
        },

        delDevice(deviceId) {
            let access_token = JSON.parse(sessionStorage.getItem("access-token"));
            // console.log(access_token.token);

            let delDevice = {
                deviceId:deviceId,
                name:'',
                status:null,
                startTime:'',
                endTime:'',
                token: access_token.token,
            };
            let apiUrl = "http://localhost:8080/api/deviceInfo/del"; //判断token的接口
            let _this = this;
            axios.post(apiUrl, delDevice).then(
                function (response) {
                if (response.data.code == "0") {
                _this.asyncGetDevices("http://localhost:8080/api/deviceInfo/general?nameSure=&nameFuzzy=&status=");
                } else {
                    alert(response.data.msg);
                }
                },
                function (error) {}
            );
        },

        //复制表格的内容到剪切板
        copyTable(){
            let content = '';
            for(let i=0;i<this.deviceStatusConvert.length;i++){
                let str = this.deviceStatusConvert[i].deviceId + '  ' + this.deviceStatusConvert[i].name + '  ' + 
                this.deviceStatusConvert[i].status + '  ' + this.deviceStatusConvert[i].startTime + '  ' + this.deviceStatusConvert[i].closeTime;
                content += str + '\n';
            }
            const el = document.createElement("textarea");
            el.value = content;
            //把这个dom添加到页面上
            document.body.appendChild(el);
            //选中这个el dom 
            el.select();
            //执行拷贝的命令
            document.execCommand("copy");
            //已经通过el复制到剪切板上了，移除这个dom就行了
            document.body.removeChild(el);
        },

        //导出excel数据
        exportExcel(type, fn, dl) {
            var elt = document.getElementById('data-table');
            var wb = XLSX.utils.table_to_book(elt, {sheet:"Sheet JS"});
            return dl?XLSX.write(wb, {bookType:type, bookSST:true, type: 'base64'}):
            XLSX.writeFile(wb, fn || ('DeviceDatas.' + (type || 'xlsx')));
        },





    },
    components:{

    }

}
</script>
<style scoped>
.search {
    display: flex;
    justify-content: space-around;
    align-items: center;
    background: rgb(0 0 0 / 45%);
    border-radius: 10px;
    padding: 5px;
    margin-bottom: 20px;
}
.name {
    display: flex;
    justify-content: center;
    align-items: center;
    margin:20px 0px;

}
.myInput {
    background:rgb(0 0 0 / 10%);
    color:white;
}
.myLabel {
    background-color:rgb(0 0 0 / 10%);
}
.mySelect{
    background-color:rgb(0 0 0 / 10%);
    color:white;
}
.myOption{
    background-color:rgb(0 0 0);
    color:white;
}
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
  background: rgba(0, 0, 0, 0.3);
  border-radius: 4px;
}

div.scroll::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.3);
  border-radius: 5%;
}

.partPages {
  display: flex;
  justify-content: flex-end;
  position: absolute;
  bottom: 20px;
  right:25px;


}
.tips{
    font-size:20px;
}
.online{
    width:5px;
    height: 5px;
    border: 1px solid green;
}

</style>

