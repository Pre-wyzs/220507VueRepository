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
                <button type="button" class="btn btn-primary btn-sm" style="margin:0px 10px;" @click="getCurrentPageHistorys(1)">查询</button>
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
                <button type="button" class="btn btn-primary btn-sm" style="margin:0px 10px;" @click="getCurrentPageHistorys(1)">查询</button>
            </div>
        </div>
        <!--分割线，下面这部分是选项框的内容了-->
        <div style="width:40%;align-self:flex-start;padding-top:20px;">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <label class="input-group-text text-primary myLabel" for="inputGroupSelect01">在线/离线</label>
                </div>
                <select class="custom-select mySelect" id="inputGroupSelect01" v-model="status" @change="getCurrentPageHistorys(1)">
                    <option selected class="myOption text-primary" value="">所有</option>
                    <option value="1" class="myOption text-success">开启</option>
                    <option value="0" class="myOption text-danger">关闭</option>
                </select>
            </div>
            <div class="date">
            <span style="color:white;">
                <button type="button" class="btn btn-outline-info btn-sm" @click="postDateTime()">起始</button>
            </span>
            <input type="datetime-local" style="width:35%;font-size:13px;" v-model="startDate"/>
            <span style="color:white;">
                <button type="button" class="btn btn-outline-info btn-sm" @click="postDateTime()">结束</button>
            </span>
            <input type="datetime-local" style="width:35%;font-size:13px;" v-model="endDate"/>
            </div>
        </div>

    </div>
  <div style="margin: 20px 0px; background: rgb(0 0 0 / 45%); border-radius: 10px">
    <div class="btn-group" style="margin:10px 20px;">
        <button type="button" class="btn btn-outline-danger bt-sm" data-toggle="modal" data-target="#two">批量删除</button>
             <div class="modal fade" id="two">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body text-center text-secondary tips">
                            确定要删除这些记录吗？
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-warning" data-dismiss="modal" @click="delHistorys(ids)">确认</button>
                            <button type="button" class="btn btn-outline-primary" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>

        <button type="button" class="btn btn btn-outline-primary" @click="copyTable()" data-toggle="modal" data-target="#copyTable">拷贝记录</button>
            <div class="modal fade" id="copyTable">
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
        <th class="text-primary">
            选中
        </th>
        <th class="text-primary">#</th>
        <th class="text-primary">设备ID</th>
        <th class="text-primary">设备名称</th>
        <th class="text-primary">时间戳</th>
        <th class="text-primary">运行状态</th>
        <th class="text-primary">操作</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(item,index) in deviceHistoryStatusConvert" :key="index">
        <td><input type="checkbox" style="margin-left:10px;" v-model="ids" :value="deviceHistoryStatusConvert[index].id" checked></td>
        <td>{{item.id}}</td>
        <td>{{item.deviceId}}</td>
        <td>{{item.name}}</td>
        <td>{{item.dateTime}}</td>
        <td :style="deviceHistoryStatusConvert[index].status=='开启'?'color:#76FF03;':'color:red;'">{{item.status}}<div :class="deviceHistoryStatusConvert[index].status == '开启'?online:outline"></div></td>
        <td>
          <button type="button" class="btn btn-outline-danger btn-sm" data-toggle="modal" data-target="#one">
            删除
          </button>
            <div class="modal fade" id="one">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body text-center text-secondary tips">
                            确定要删除这条记录吗？
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-warning" data-dismiss="modal" @click="delHistory(deviceHistoryStatusConvert[index].id)">确认</button>
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
                <a class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
                </li>
                <li class="page-item" v-for="(item,index) in partPages(cgroup)" :key="index" @click="getCurrentPageHistorys(item)"><a class="page-link" href="javascript:;">{{item}}</a></li>
                <li class="page-item" @click="nextGroup(cgroup)">
                <a class="page-link" href="#" aria-label="Next">
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
const { mapState, mapGetters, mapActions, mapMutations } = createNamespacedHelpers('deviceHistory');;
import * as XLSX from 'xlsx'

export default {
    name:'DeviceHistory',
    created(){
        this.asyncGetdeviceHistorys("http://localhost:8080/api/deviceHistory/general?startDate=&endDate=&nameSure=&nameFuzzy=&status=");

                /**建立和后端的一个 webSocket连接*/
        var socket = new WebSocket("ws://localhost:8080/ws/api/equipment");
        let _this = this;
        socket.onopen = function(){
            console.log('websocket连接已经建立了');
            //成功建立链接后发送这个连接的唯一名称
            socket.send("DeviceHistory-Linking");
        }

        /**在这个钩子函数中开启socket的监听事件 ：当服务器的session向它发送消息的时候，自然就会调用到这个回调函数了*/
        socket.onmessage = function(evt){
            //服务端返回的就是字符串，得通过evt.data来获取
            let retStr = evt.data;

            if(retStr == 'Status_Updated'){
                //重新更新页面捏，不是从websocket接口中直接拿数据，有点捞捏!
                //因为我们知道执行到这里，一定是有数据被更新的，无论被更新的数据是不是本页面的，都刷新一下就行了
                _this.asyncGetdeviceHistorys("http://localhost:8080/api/deviceHistory/general?nameSure="+_this.nameSure+"&nameFuzzy="+
            _this.nameFuzzy+"&status="+_this.status+"&startDate="+_this.startDate0+"&endDate="+_this.endDate0+"&pageNum="+_this.cpage);
            }

        }


    },
    data(){
        return {
            //当前页码
            cpage:1,
            cgroup:0,
            jump:1,
            //要向接口传递的参数
            nameSure:'',
            nameFuzzy:'',
            status:'',
            startDate:'',
            startDate0:'',
            endDate:'',
            endDate0:'',
            //设备开启或关闭的一个标志位
            online:'spinner-grow text-success spinner-grow-sm',
            outline:'spinner-grow text-danger spinner-grow-sm',
            //要删除的记录的id数组
            ids:[]
        }
    },
    computed:{
        ...mapState(['deviceHistorys','totalPages_deviceHistory']),
        ...mapGetters(['deviceHistoryStatusConvert','totalPages'])
    },
    methods:{
        ...mapMutations(['getdeviceHistorys']),
        ...mapActions(['asyncGetdeviceHistorys']),
        delHistorys(ids){
            let access_token = JSON.parse(sessionStorage.getItem("access-token"));
            ids.push(access_token.token);

            // console.log(ids);
            let apiUrl = "http://localhost:8080/api/deviceHistory/dels"; //判断token的接口
            let _this = this;
            // console.log('函数已被执行');
            axios.post(apiUrl, ids).then(
                function (response) {
                    if (response.data.code == "0") {
                        _this.asyncGetdeviceHistorys("http://localhost:8080/api/deviceHistory/general?startDate=&endDate=&nameSure=&nameFuzzy=&status=");
                    }else {
                        alert(response.data.msg);
                    }
                },
                function (error) {}
            );
            //置ids为空
            _this.ids = [];

        },
        postDateTime(){
            //先进行日期的格式转换
            if(this.startDate != '' || this.endDate != ''){
                if(this.startDate!=''){
                    this.startDate0 = this.startDate.replaceAll('T','%20').concat(':00');
                }
                if(this.endDate != ''){
                    this.endDate0= this.endDate.replaceAll('T','%20').concat(':00');
                }
            }

            //再去获取页面的内容捏
            this.getCurrentPageHistorys(1);
        },
        getCurrentPageHistorys(pageNum) {

                        //先进行日期的格式转换
            if(this.startDate != '' || this.endDate != ''){
                if(this.startDate!=''){
                    this.startDate0 = this.startDate.replaceAll('T','%20').concat(':00');
                }
                if(this.endDate != ''){
                    this.endDate0= this.endDate.replaceAll('T','%20').concat(':00');
                }
            }


            let apiUrl;

            // http://localhost:8080/api/deviceHistory/general?startDate=&endDate=&nameSure=&nameFuzzy=&status=
            apiUrl = "http://localhost:8080/api/deviceHistory/general?nameSure="+this.nameSure+"&nameFuzzy="+
            this.nameFuzzy+"&status="+this.status+"&startDate="+this.startDate0+"&endDate="+this.endDate0+"&pageNum="+pageNum;
            console.log(apiUrl);

            //搞好接口的url地址后要重置一下this.startDate0和this.endDate0的，不然它的影响就去除不掉了...
            this.startDate0 = '',
            this.endDate0 = ''

            let that = this;
            axios.get(apiUrl).then(
                function (response) {
                let rets = response.data;
                let deviceHistorys = rets.data;

                if (deviceHistorys.length == 0) {
                    alert("没有记录!");
                } else {
                    //在设备信息存在时去更新仓储
                    that.getdeviceHistorys({ deviceHistorys: deviceHistorys, totalPages: rets.totalPages });
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
            this.getCurrentPageHistorys(jump);
        },

        delHistory(id) {
            let access_token = JSON.parse(sessionStorage.getItem("access-token"));
            // console.log(access_token.token);

            let delHistory= {
                id:id,
                deviceId:null,
                name:'',
                dateTime:'',
                status:null,
                token: access_token.token,
            };
            let apiUrl = "http://localhost:8080/api/deviceHistory/del"; //判断token的接口
            let _this = this;
            axios.post(apiUrl, delHistory).then(
                function (response) {
                    if (response.data.code == "0") {
                    _this.asyncGetdeviceHistorys("http://localhost:8080/api/deviceHistory/general?startDate=&endDate=&nameSure=&nameFuzzy=&status=");
                    }else {
                        alert(response.data.msg);
                    }
                },
                function (error) {}
            );
        },

         //复制表格的内容到剪切板
        copyTable(){
            let content = '';
            for(let i=0;i<this.deviceHistoryStatusConvert.length;i++){
                let str = this.deviceHistoryStatusConvert[i].id+ '  ' + this.deviceHistoryStatusConvert[i].deviceId+ '  ' + 
                this.deviceHistoryStatusConvert[i].name + '  ' + this.deviceHistoryStatusConvert[i].dateTime + '  ' + this.deviceHistoryStatusConvert[i].status;
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
            XLSX.writeFile(wb, fn || ('DeviceHistoryDatas.' + (type || 'xlsx')));
        }




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
.date{
    display: flex;
    justify-content: space-between;
    align-items: center;
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
  bottom:20px;
  right: 20px;
}


</style>
