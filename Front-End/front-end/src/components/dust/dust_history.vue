<template>
    <!-- 搜索部分。。。 -->
    <div class="search">
        <div style="width:40%;padding-top:15px;">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <label class="input-group-text text-primary myLabel" for="inputGroupSelect01">空气质量</label>
                </div>
                <select class="custom-select mySelect" v-model="degree" @change="getCurrentPageHistorys(1)">
                    <option selected class="myOption text-primary" value="">所有</option>
                    <option value="1" class="myOption text-success">优级</option>
                    <option value="2" class="myOption text-info">良好</option>
                    <option value="3" class="myOption text-warning">轻微污染</option>
                    <option value="4" class="myOption text-danger">轻度污染</option>
                    <option value="5" class="myOption text-danger">中度污染</option>
                    <option value="6" class="myOption text-danger">重度污染</option>
                </select>
            </div>

        </div>
        <div class="date">
            <span style="color:white;">
                <button type="button" class="btn btn-outline-info btn-sm" @click="postDateTime()">起始</button>
            </span>
            <input type="datetime-local" style="width:35%;font-size:13px;" v-model="startTime"/>
            <span style="color:white;">
                <button type="button" class="btn btn-outline-info btn-sm" @click="postDateTime()">结束</button>
            </span>
            <input type="datetime-local" style="width:35%;font-size:13px;" v-model="endTime"/>
        </div>
    </div>

    <!-- 操作部分 -->
        <!-- 操作部分。。。 -->
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


        <button type="button" class="btn btn btn-outline-primary" @click="exportExcel()">Excel</button>
        <button type="button" class="btn btn btn-outline-primary" @click="getAllHistorysAndExport()">Excel All</button>

    </div>
    </div>

    <!-- 表格数据部分 -->
        <table class="table table-dark table-striped table-hover my-table" id="data-table">
            <thead>
            <tr>
                <th class="text-primary">
                    选中
                </th>
                <th class="text-primary">时间戳</th>
                <th class="text-primary">AQI指数</th>
                <th class="text-primary">PM10浓度</th>
                <th class="text-primary">PM2.5浓度</th>
                <th class="text-primary">污染程度</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item,index) in retHistorys" :key="index">
                <td><input type="checkbox" style="margin-left:10px;" v-model="ids" :value="dustHistorys[index].id" checked></td>
                <td>{{item.timeStamp}}</td>
                <td>{{item.aqi}}</td>
                <td>{{item.pm10}}</td>
                <td>{{item.pm25}}</td>
                <td>{{item.degree}}</td>
            </tr>
            </tbody>
        </table>

        <!-- 分页操作 -->
            <div class="partPages ">
        <div class="scroll">
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <li class="page-item" @click="upGroup(cgroup)">
                        <a class="page-link" href="javascript:;" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item" v-for="(item,index) in partPages(cgroup)" :key="index" @click="getCurrentPageHistorys(item)">
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
        <div class="input-group-sm" style="margin:0px 0px;width:5%;padding-top:7px;">
            <input type="text" class="myInput form-control" v-model="jump" @keydown.enter="jumpTo(jump)"/>
        </div>
    </div>






</template>
<script>
import axios from 'axios'
import {  createNamespacedHelpers } from 'vuex' //引入辅助函数
const { mapState, mapGetters, mapActions, mapMutations } = createNamespacedHelpers('dustHistory')
import ExportJsonExcel from 'js-export-excel'

export default {
    name:'DustHistory',
    created(){
        this.asyncGetDustHistorys("http://localhost:8080/api/dust/general?startTime=&endTime=&degree=");
        // console.log("已从后端获取数据");
        var socket = new WebSocket("ws://localhost:8080/ws/api/equipment");
        let _this = this;
        socket.onopen = function(){
            console.log('websocket linked');
            //成功建立链接后发送这个连接的唯一名称
            socket.send("Dust-Linking");
        }
        /**在这个钩子函数中开启socket的监听事件 ：当服务器的session向它发送消息的时候，自然就会调用到这个回调函数了*/
        socket.onmessage = function(evt){
            //服务端返回的就是字符串，得通过evt.data来获取
            let retStr = evt.data;

            if(retStr == 'Status_Updated'){
                //重新更新页面捏，不是从websocket接口中直接拿数据，有点捞捏!
              console.log("消息已送到");
                _this.getCurrentPageHistorys(_this.cpage);
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
        return{
            degree:'',
            cpage:1,
            cgroup:0,
            startTime:'',
            endTime:'',
            startTime0:'',
            endTime0:'',
            jump:1,
            ids:[],
        }
    },
    computed:{
        ...mapState(['dustHistorys','totalPages']),
        ...mapGetters(['retHistorys']),

    },
    methods:{
        ...mapMutations(['getDustHistorys']),
        ...mapActions(['asyncGetDustHistorys']),
        getCurrentPageHistorys(pageNum) {
            // console.log('跳转到' + pageNum);
                        //先进行日期的格式转换
            if(this.startTime != '' || this.endTime != ''){
                if(this.startTime!=''){
                    this.startTime0 = this.startTime.replaceAll('T','%20').concat(':00');
                }
                if(this.endTime!= ''){
                    this.endTime0 = this.endTime.replaceAll('T','%20').concat(':00');
                }
            }

            let apiUrl;
            apiUrl = "http://localhost:8080/api/dust/general?degree="+this.degree+"&startTime="+this.startTime0
            +"&endTime="+this.endTime0+"&pageNum="+pageNum;

            this.startTime0 = '';
            this.endTime0 = '';

            // console.log(apiUrl);
            let that = this;
            axios.get(apiUrl).then(
                function (response) {
                let rets = response.data;
                if (rets.data.length == 0) {
                    alert("没有记录!");
                } else {
                    //在设备信息存在时去更新仓储
                    that.getDustHistorys({dustHistorys:rets.data,totalPages:rets.totalPages});
                    //并且更新页码。。。
                    that.cpage = pageNum;
                }
                },
                function (error) {}
            );
        },
        delHistorys(ids){
            let access_token = JSON.parse(sessionStorage.getItem("access-token"));
            ids.push(access_token.token);

            console.log(ids);
            let apiUrl = "http://localhost:8080/api/dust/dels"; //判断token的接口
            let _this = this;
            // console.log('函数已被执行');
            axios.post(apiUrl,ids).then(
                function (response) {
                    if (response.data.code == "0") {
                        apiUrl = "http://localhost:8080/api/dust/general?degree="+_this.degree+"&startTime="+_this.startTime0
                        +"&endTime="+_this.endTime0+"&pageNum="+_this.cpage;
                        _this.asyncGetDustHistorys(apiUrl);
                    }else {
                        alert(response.data.msg);
                    }
                },
                function (error) {}
            );
            //置ids为空
            this.ids = [];
        },
        postDateTime(){
            //先进行日期的格式转换
            // if(this.startTime != '' || this.endTime != ''){
            //     if(this.startTime!=''){
            //         this.startTime0 = this.startTime.replaceAll('T','%20').concat(':00');
            //     }
            //     if(this.endTime!= ''){
            //         this.endTime0 = this.endTime.replaceAll('T','%20').concat(':00');
            //     }
            // }

            //再去获取页面的内容捏
            this.getCurrentPageHistorys(1);
        },
        copyTable(){
            let content = '';
            for(let i=0;i<this.retHistorys.length;i++){
                let str = this.retHistorys[i].timeStamp+ '  ' + this.retHistorys[i].aqi+ '  ' + 
                this.retHistorys[i].pm10+ '  ' + this.retHistorys[i].pm25 + ' ' +this.retHistorys[i].degree;
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



        async getAllHistorysAndExport(){
            const {data: ret} = await axios.get("http://localhost:8080/api/dust/all");
            // console.log(ret.data);
            this.exportAllExcel(ret.data);

        },
        exportAllExcel(allHistorys){
            let HeaderArr = ['序列号','时间戳','污染程度','AQI','PM10','PM2.5'];

            //配置option选项.
            var option={};
            option.fileName = 'DustAllHistory'
            option.datas=[
                {
                    sheetData:allHistorys,
                    sheetHeader:HeaderArr//前端指定的表头
                },
            ];

            var toExcel=new ExportJsonExcel(option);
            toExcel.saveExcel();
        },
        exportExcel() {
            let HeaderArr = ['序列号','时间戳','污染程度','AQI','PM10','PM2.5'];
            //配置option选项.
            var option={};
            option.fileName = 'DustAllHistory'
            option.datas=[
                {
                    sheetData:this.retHistorys,
                    sheetHeader:HeaderArr//前端指定的表头
                },
            ];

            var toExcel=new ExportJsonExcel(option);
            toExcel.saveExcel();

        },


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
    width:45%;
}
.my-table {
  border-radius: 10px;
  background: rgb(0 0 0 / 65%);
  border: 2px solid rgba(0, 0, 0, 0.12);
  box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.12);
  caret-color: rgba(0, 0, 0, 0);

}
.partPages{
    display: flex;
    justify-content: flex-end;
    position: absolute;
    bottom:20px;
    right:20px;
    z-index:0;
}



</style>