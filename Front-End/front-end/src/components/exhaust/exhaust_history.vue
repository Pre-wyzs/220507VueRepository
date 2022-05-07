<template>
<!-- 搜索部分。。。 -->
    <div class="search">
        <div style="width:40%;padding-top:15px;">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <label class="input-group-text text-primary myLabel" for="inputGroupSelect01">成分选择</label>
                </div>
                <select class="custom-select mySelect" id="inputGroupSelect01" v-model="partName" @change="getCurrentPageHistorys(1)">
                    <option selected class="myOption text-primary" value="">所有</option>
                    <option value="SO2" class="myOption" style="color:#FC7D02;">SO2</option>
                    <option value="NO" class="myOption" style="color:#AC3B2A;">NO</option>
                    <option value="O3" class="myOption" style="color:#AA069F;">O3</option>
                    <option value="HF" class="myOption" style="color:#FBDB0F;">HF</option>
                    <option value="C6H6" class="myOption" style="color:#FD0100;">C6H6</option>
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


        <button type="button" class="btn btn btn-outline-primary" @click="exportExcel(xlsx)">Excel</button>
        <button type="button" class="btn btn btn-outline-primary" @click="getAllHistorysAndExport()">Excel All</button>

    </div>
    </div>

    <div class="tableData">
        <table class="table table-dark table-striped table-hover my-table" id="data-table">
            <thead>
            <tr>
                <th class="text-primary">
                    选中
                </th>
                <th class="text-primary">时间戳</th>
                <th class="text-primary">成分名</th>
                <th class="text-primary">μg/m³</th>
                <th class="text-primary">状态</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item,index) in retHistorys" :key="index" :class="retHistorys[index].status=='超标'?'exceedColor':'normalColor'">
                <td><input type="checkbox" style="margin-left:10px;" v-model="ids" :value="exhaustHistorys[index].id" checked></td>
                <td>{{item.timeStamp}}</td>
                <td>{{item.partName}}</td>
                <td>{{item.value}}</td>
                <td>{{item.status}}</td>
            </tr>
            </tbody>
        </table>

        <table class="table table-dark table-striped table-hover my-table" id="">
            <thead>
            <tr>
                <th class="text-primary">时间戳</th>
                <th class="text-primary">成分名</th>
                <th class="text-primary">超标值</th>
                <th class="text-primary">标准值</th>
                <th class="text-primary">超标百分比</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item,index) in exceedHistorys" :key="index" class="exceedColor">
                <td>{{item.timeStamp}}</td>
                <td>{{item.partName}}</td>
                <td>{{item.value}}</td>
                <td>{{item.standardValue}}</td>
                <td>
                    {{item.percent}}
                </td>
            </tr>
            </tbody>
        </table>
    </div>


    <div class="partPages ">
        <div class="scroll">
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <li class="page-item" @click="upGroup(cgroup)">
                        <a class="page-link" href="javascript:;" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item" v-for="(item,index) in partPages(cgroup)" :key="index" @click="getCurrentPageHistorys(item)"><a class="page-link" href="#" >{{item}}</a></li>

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
const { mapState, mapGetters, mapActions, mapMutations } = createNamespacedHelpers('exhaustHistory');;
import * as XLSX from 'xlsx'
import ExportJsonExcel from 'js-export-excel'

export default {

    name:'ExhaustHistory',
    created(){
        this.asyncGetExhaustHistorys("http://localhost:8080/api/exhaustHistory/general?partName=&startTime=&endTime=");

         var socket = new WebSocket("ws://localhost:8080/ws/api/equipment");
        let _this = this;
        socket.onopen = function(){
            console.log('websocket linked');
            //成功建立链接后发送这个连接的唯一名称
            socket.send("ExhaustHistory-Linking");
        }

        /**在这个钩子函数中开启socket的监听事件 ：当服务器的session向它发送消息的时候，自然就会调用到这个回调函数了*/
        socket.onmessage = function(evt){
            //服务端返回的就是字符串，得通过evt.data来获取
            let retStr = evt.data;

            if(retStr == 'Status_Updated'){
                //重新更新页面捏，不是从websocket接口中直接拿数据，有点捞捏!
                console.log('消息了已经送达了挖');
                //因为我们知道执行到这里，一定是有数据被更新的，无论被更新的数据是不是本页面的，都刷新一下就行了
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
        return {
            cpage:1,
            cgroup:0,
            partName:'',
            startTime:'',
            endTime:'',
            startTime0:'',
            endTime0:'',
            jump:1,
            ids:[],
        }
    },
    computed:{
        ...mapState(['exhaustHistorys','totalPages']),
        ...mapGetters(['exceedHistorys','retHistorys']),
    },
    methods:{
        ...mapMutations(['getExhaustHistorys']),
        ...mapActions(['asyncGetExhaustHistorys']),
        getCurrentPageHistorys(pageNum) {
            // console.log('跳转到' + pageNum);

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

            apiUrl = "http://localhost:8080/api/exhaustHistory/general?partName="+this.partName+"&startTime="+this.startTime0
            +"&endTime="+this.endTime0+"&pageNum="+pageNum;

            this.startTime0 = '';
            this.endTime0 = '';

            console.log(apiUrl);
            let that = this;
            axios.get(apiUrl).then(
                function (response) {
                let rets = response.data;

                if (rets.data.length == 0) {
                    alert("没有记录!");
                } else {
                    //在设备信息存在时去更新仓储
                    that.getExhaustHistorys({exhaustHistorys:rets.data,totalPages:rets.totalPages});
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
            let apiUrl = "http://localhost:8080/api/exhaustHistory/dels"; //判断token的接口
            let _this = this;
            // console.log('函数已被执行');
            axios.post(apiUrl,ids).then(
                function (response) {
                    if (response.data.code == "0") {
                        apiUrl = "http://localhost:8080/api/exhaustHistory/general?partName="+_this.partName+"&startTime="+_this.startTime0
                        +"&endTime="+_this.endTime0+"&pageNum="+_this.cpage;
                        _this.asyncGetExhaustHistorys(apiUrl);
                    }else {
                        alert(response.data.msg);
                    }
                },
                function (error) {}
            );
            //置ids为空
            this.ids = [];
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

        postDateTime(){
            //先进行日期的格式转换
            if(this.startTime != '' || this.endTime != ''){
                if(this.startTime!=''){
                    this.startTime0 = this.startTime.replaceAll('T','%20').concat(':00');
                }
                if(this.endTime!= ''){
                    this.endTime0 = this.endTime.replaceAll('T','%20').concat(':00');
                }
            }

            //再去获取页面的内容捏
            this.getCurrentPageHistorys(1);
        },

        partPages(partGroup){
            let allArr = [];
            let retArr = [];
            for(let i = 0;i < this.totalPages;i++){
                allArr.push(i + 1);
            }

            if(allArr.length <=5){
                retArr = allArr;
            }else{
                retArr = allArr.slice(5*(partGroup),5*(partGroup+1));
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
        copyTable(){
            let content = '';
            for(let i=0;i<this.retHistorys.length;i++){
                let str = this.retHistorys[i].timeStamp+ '  ' + this.retHistorys[i].partName+ '  ' + 
                this.retHistorys[i].value+ '  ' + this.retHistorys[i].status;
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
        exportExcel(type, fn, dl) {
            var elt = document.getElementById('data-table');
            var wb = XLSX.utils.table_to_book(elt, {sheet:"Sheet JS"});
            return dl?XLSX.write(wb, {bookType:type, bookSST:true, type: 'base64'}):
            XLSX.writeFile(wb, fn || ('ExhaustHistorys.' + (type || 'xlsx')));
        },
        async getAllHistorysAndExport(){
            const {data: ret} = await axios.get("http://localhost:8080/api/exhaustHistory/all");
            // console.log(ret.data);
            this.exportAllExcel(ret.data);

        },
        exportAllExcel(allHistorys){
            let HeaderArr = ['序列号','时间戳','成分名','当前值','标准值'];

            //配置option选项.
            	var option={};
				option.fileName = 'ExhaustAllHistorys'
				option.datas=[
				{
					sheetData:allHistorys,
					sheetHeader:HeaderArr//前端指定的表头
				},
				];

				var toExcel=new ExportJsonExcel(option);
				toExcel.saveExcel();
        }



    }


}
</script>

<style scoped>

.partPages{
    display: flex;
    position: absolute;
    bottom:20px;
    right: 20px;
    justify-content: flex-end;

}
.search {
    display: flex;
    justify-content: space-around;
    align-items: center;
    background: rgb(0 0 0 / 45%);
    border-radius: 10px;
    padding: 5px;
    margin-bottom: 20px;
}

.normalColor{
    color:#0DC217;
}
.exceedColor{
    color:red;
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
  width:48%;

}
.tableData{
    display: flex;
    justify-content: space-between;


}
</style>