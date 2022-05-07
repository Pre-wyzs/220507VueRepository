<template>
    <div class="container-fluid myContainer">
        <div class="row">
            <div class="col-sm-3">
                <div class="select">
                    <span class="span" >姓名</span>
                    <input type="text" class="form-control myInput" v-model="searchArg.name">
                </div>
            </div>
            <div class="col-sm-3">
                <div class="select">
                    <span class="span" >企业单位</span>
                    <input type="text" class="form-control myInput" v-model="searchArg.enterprise">
                </div>
            </div>
            <div class="col-sm-3">
                <div class="select">
                    <span class="span" >身份证</span>
                    <input type="text" class="form-control myInput" v-model="searchArg.idCard">
                </div>
            </div>
            <div class="col-sm-3">
                <div class="select">
                    <span class="span" >所属班组</span>
                    <select class="custom-select mySelect" v-model="searchArg.team">
                        <option selected class="myOption text-primary" value="">所有</option>
                        <option value="1" class="myOption text-success">管理班组</option>
                        <option value="2" class="myOption text-warning">劳务班组</option>
                    </select>
                </div>
            </div>
            <div class="w-100"></div>
            <div class="col-sm-3">
                <div class="select">
                    <span class="span" >签到状态</span>
                    <select class="custom-select mySelect" v-model="searchArg.sign">
                        <option selected class="myOption text-primary" value="">所有</option>
                        <option value="0" class="myOption text-success">未签到</option>
                        <option value="1" class="myOption text-warning">已签到</option>
                        <option value="2" class="myOption text-warning">迟到</option>
                        <option value="3" class="myOption text-warning">补签</option>
                    </select>
                </div>
            </div>

            <div class="col-sm-3">
                <div class="select">
                    <span class="span" >通行用途</span>
                    <select class="custom-select mySelect" v-model="searchArg.purpose">
                        <option selected class="myOption text-primary" value="">所有</option>
                        <option value="1" class="myOption text-success">进场</option>
                        <option value="2" class="myOption text-warning">出场</option>
                    </select>
                </div>
            </div>
            <div class="col-sm">
                <div class="select">
                    <input type="datetime-local" style="width:35%;font-size:13px;" v-model="searchArg.startTime"/>
                    <label style="color:white;padding-top:2px;margin:0px 10px;">-至-</label>
                    <input type="datetime-local" style="width:35%;font-size:13px;" v-model="searchArg.endTime"/>
                </div>
            </div>
            <div class="w-100"></div>
            <div class="col-sm-6">
                <div class="operate">
                <div class="btn-group">
                    <button type="button" class="btn btn-outline-primary btn-sm" @click="getCurrentPageWorkerSigns(1)">查询</button>
                    <button type="button" class="btn btn-outline-danger btn-sm" data-toggle="modal" data-target="#two">批量删除</button>
                        <div class="modal fade" id="two">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-body text-center text-secondary tips">
                                        确定要删除这些记录吗？
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-outline-warning" data-dismiss="modal" @click="delWorkerSigns(ids)">确认</button>
                                        <button type="button" class="btn btn-outline-primary" data-dismiss="modal">取消</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    <button type="button" class="btn btn-outline-info btn-sm" data-toggle="modal" data-target="#copyTable" @click="copyTableData()">拷贝数据</button>
                        <div class="modal fade" id="copyTable">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-body text-center text-secondary tips" data-dismiss="modal">
                                        数据已拷贝到剪切板。
                                    </div>
                                </div>
                            </div>
                        </div>

                    <button type="button" class="btn btn-outline-info btn-sm" @click="getAllWorkerSignsAndExport()">导出excel</button>
                </div >
                </div>
            </div>
            <div class="col-sm-6">
                <div class="operate">
                     <div class="btn-group">
                        <router-link to="/camera/1">
                        <button type="button" class="btn btn-outline-success btn-sm">入场签到</button>
                        </router-link>
                        <router-link to="/camera/2">
                        <button type="button" class="btn btn-outline-success btn-sm">出场签到</button>
                        </router-link>
                        <!-- 该小模块使用模态框 -->
                        <button type="button" class="btn btn-outline-warning btn-sm" data-toggle="modal" data-target="#modalBox">补签</button>
                        <!-- <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo">Open modal for @mdo</button> -->
                        <div class="modal fade" id="modalBox" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title text-right">补签表</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="recipient-name" class="col-form-label">姓名:</label>
                                    <input type="text" class="form-control" v-model="afterSign.name">
                                    <label for="message-text" class="col-form-label">身份证号:</label>
                                    <input type="text" class="form-control" v-model="afterSign.idCard">
                                    <label for="message-text" class="col-form-label">企业单位:</label>
                                    <input type="text" class="form-control" v-model="afterSign.enterprise">
                                    <label for="message-text" class="col-form-label" style="width:10%;">班组:</label>
                                    <select class="custom-select mySelect" style="width:30%;color:black;" v-model="afterSign.team">
                                        <option selected value="1" class="myOption text-success">管理班组</option>
                                        <option value="2" class="myOption text-warning">劳务班组</option>
                                    </select>
                                    <label for="message-text" class="col-form-label" style="margin-left:20%;width:15%;">通行用途:</label>
                                    <select class="custom-select mySelect" style="width:25%;color:black;" v-model="afterSign.purpose">
                                        <option selected value="1" class="myOption text-success">进场</option>
                                        <option value="2" class="myOption text-warning">出场</option>
                                    </select>

                                    <label for="message-text" class="col-form-label">补签日期:</label>
                                    <input type="datetime-local" style="width:35%;font-size:13px;" v-model="afterSign.timeStamp"/>
                                </div> 


                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" data-dismiss="modal" @click="afterSignSubmit(afterSign)">提交</button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                            </div>
                            </div>
                        </div>
                        </div>


                    </div>
                </div>
            </div>
        </div>
    </div>
        <!-- 表格数据部分 -->
        <table class="table table-dark table-striped table-hover my-table">
            <thead>
            <tr>
                <th class="text-primary">
                    选中
                </th>
                <th class="text-primary">ID</th>
                <th class="text-primary">姓名</th>
                <th class="text-primary">身份证</th>
                <th class="text-primary">班组</th>
                <th class="text-primary">企业单位</th>
                <th class="text-primary">通行用途</th>
                <th class="text-primary">签到状态</th>
                <th class="text-primary">考勤时间</th>
                <th class="text-primary">操作</th>

            </tr>
            </thead>
            <tbody>
            <tr v-for="(item,index) in retWorkerSigns" :key="index">
                <td><input type="checkbox" style="margin-left:10px;" v-model="ids" :value="retWorkerSigns[index].id" checked></td>
                <td>{{item.id}}</td>
                <td>{{item.name}}</td>
                <td>{{item.idCard}}</td>
                <td>{{item.team}}</td>
                <td>{{item.enterprise}}</td>
                <td>{{item.purpose}}</td>
                <td>
                    <div :class="retWorkerSigns[index].sign == '已签到'?'btn btn-outline-success btn-sm':'btn btn-outline-warning btn-sm'">
                        {{item.sign}}
                    </div>
                </td>
                <td>{{item.timeStamp}}</td>
                <td>
                    <button type="button" class="btn btn-outline-danger btn-sm" data-toggle="modal" data-target="#exampleModal" @click="delWorkerSign(retWorkerSigns[index].id)" >
                        删除
                    </button>
                </td>
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
                    <li class="page-item" v-for="(item,index) in partPages(cgroup)" :key="index" @click="getCurrentPageWorkerSigns(item)">
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
        <div class="input-group-sm" style="margin:0px 0px;width:10%;padding-top:7px;">
            <input type="text" class="myInput form-control" v-model="jump" @keydown.enter="jumpTo(jump)"/>
        </div>
    </div>






</template>
<script>
import axios from "axios";
import {  createNamespacedHelpers } from 'vuex' //引入辅助函数
import {stringToNull,dateFormat} from '../../utils/utils'
const { mapState, mapGetters, mapActions, mapMutations } = createNamespacedHelpers('workerSign');;
import ExportJsonExcel from 'js-export-excel'


export default {
    name:'WorkerSign',
    created(){
        /*-----------------------初始化数据------------------------------------------------------------------------------------ */
        let obj = {
            apiUrl:'http://localhost:8080/api/worker/sign/general',
            //后端在接受json对象的时候如果一个键没有设置过，那它的键值就是null!
            args:{
                pageNum:1,
                pageSize:6,
            }
        }
        this.asyncGetWorkerSigns(obj);
        /*----------------------------------------------------------------------------------------------------------- */

        /*-----------------------实时更新签到数据------------------------------------------------------------------------------------ */
        var socket = new WebSocket("ws://localhost:8080/ws/api/equipment");
        let _this = this;
        socket.onopen = function(){
            console.log('websocket linked');
            //成功建立链接后发送这个连接的唯一名称
            socket.send("WorkerSign-Linking");
        }
        /**在这个钩子函数中开启socket的监听事件 ：当服务器的session向它发送消息的时候，自然就会调用到这个回调函数了*/
        socket.onmessage = function(evt){
            //服务端返回的就是字符串，得通过evt.data来获取
            let retStr = evt.data;

            if(retStr == 'Status_Updated'){
                //重新更新页面捏，不是从websocket接口中直接拿数据，有点捞捏!
              console.log("消息已送到");
                _this.getCurrentPageWorkerSigns(_this.cpage);
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
        /*------批量删除ids---------------------------------------------------------------------------------------------------- */
            ids:[],

        /*------分页变量----------------------------------------------------------------------------------------------------- */
            cpage:1,
            cgroup:0,
            jump:1,

        /*------查询参数----------------------------------------------------------------------------------------------------- */
            searchArg:{
                name:null,  //null原来也可以绑定空的框框，这蛮好的
                enterprise:null,
                idCard:null,
                team:null,
                purpose:null, //通行用途
                startTime:null,
                endTime:null,
                sign:null,  //签到状态

                token:null,
                pageNum:1,
                pageSize:6
            },

        /*------补签到的提交参数----------------------------------------------------------------------------------------------------- */
            afterSign:{
                name:'',
                idCard:'',
                enterprise:'',
                team:1,  //这里写'1'也是没事的，因为后端可以把'1'接受成1的，哈哈
                purpose:1,
                timeStamp:''
            }
        


        }
    },
    computed:{
        /*------仓储拓展----------------------------------------------------------------------------------------------------- */
        ...mapState(['workerSigns','totalPages']),
        ...mapGetters(['retWorkerSigns'])

    },
    methods:{
        /*------仓储拓展----------------------------------------------------------------------------------------------------- */
        ...mapMutations(['getWorkerSigns']),
        ...mapActions(['asyncGetWorkerSigns']),
        afterSignSubmit(obj){
            if(obj.name == '' || obj.idCard == '' || obj.enterprise == '' || obj.timeStamp == ''){
                alert("请填写所有的字段值！");
                return;
            }

            //调整一下日期的格式
            // obj.timeStamp = obj.timeStamp.replaceAll('T',' ').concat(':00');
            let args = obj;
            args.timeStamp = args.timeStamp.replaceAll('T',' ').concat(':00');
            console.log(args);
            let apiUrl = "http://localhost:8080/api/worker/sign/add";
            axios.post(apiUrl,args).then(function(response){
                let rets = response.data;
                console.log(rets.data);

            },function(error){

            });

            //置为空
            this.afterSign = {
                name:'',
                idCard:'',
                enterprise:'',
                team:1,
                purpose:1,
                timeStamp:''
            };
        },
                /*------导出allExcel----------------------------------------------------------------------------------------------------- */
        async getAllWorkerSignsAndExport(){
            const {data: ret} = await axios.get('http://localhost:8080/api/worker/sign/all');

            ret.data.forEach(item => {
                if(item.team == '1'){
                    item.team = '管理班组'
                }else{
                    item.team = '劳务班组'
                }
                if(item.sign== '1'){
                    item.sign= '已签到'
                }else if(item.sign== '2'){
                    item.sign = '迟到'
                }else if(item.sign == '3'){
                    item.sign= '补签到'
                }else{
                    item.sign= '未签到'
                }
                if(item.purpose == '1'){
                    item.purpose = '入场'
                }else{
                    item.purpose = '出场'
                }
            });
            
            this.allWorkerSignsExport(ret.data);
        },

        /*------真正执行导出的函数----------------------------------------------------------------------------------------------------- */
        allWorkerSignsExport(allWorkerSigns){
            let HeaderArr = ['ID','姓名','身份证','班组','企业单位','通行用途','签到状态','照片','时间戳'];
            //配置option选项.
            var option={};
            option.fileName = 'AllSignData'
            option.datas=[
                {
                    sheetData:allWorkerSigns,
                    sheetHeader:HeaderArr//前端指定的表头
                },
            ];
            var toExcel=new ExportJsonExcel(option);
            toExcel.saveExcel();
        },



        /*------拷贝数据到剪切板----------------------------------------------------------------------------------------------------- */
        copyTableData(){
            let content = '';
            for(let i=0;i<this.retWorkerSigns.length;i++){
                let str = this.retWorkerSigns[i].id + ' ' + this.retWorkerSigns[i].name + ' ' + this.retWorkerSigns[i].idCard+ ' '
                + this.retWorkerSigns[i].enterprise+ ' ' + this.retWorkerSigns[i].team+ ' ' + this.retWorkerSigns[i].purpose+ ' ' +
                this.retWorkerSigns[i].sign+ ' ' + this.retWorkerSigns[i].timeStamp;
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

                /*------主动获取数据总接口----------------------------------------------------------------------------------------------------- */
        getCurrentPageWorkerSigns(pageNum) {
            //这个参数是说明分页的具体类型的..
            let apiUrl = 'http://localhost:8080/api/worker/sign/general';

            //这个基本上就是标准的构造args的形式了，因为刚开始这些属性确实是null没有错，但是
            //当一个输入框输入内容，然后删除内容后，又会变成 '' 了，所以要把所有的''变成null才行！
            let args = {
                name:stringToNull(this.searchArg.name),  //null原来也可以绑定空的框框，这蛮好的
                idCard:stringToNull(this.searchArg.idCard),
                team:stringToNull(this.searchArg.team),
                enterprise:stringToNull(this.searchArg.enterprise),

                purpose:stringToNull(this.searchArg.purpose),
                sign:stringToNull(this.searchArg.sign),

                //开始时间和结束时间的格式转换好像还需要考虑一下，哈哈
                startTime:dateFormat(this.searchArg.startTime),
                endTime:dateFormat(this.searchArg.endTime),
                token:null,
                pageNum:pageNum,
                pageSize:6
            };
            console.log(args);

            let that = this;
            axios.put(apiUrl,args).then(
                function (response) {
                    let rets = response.data;
                    if (rets.data.length == 0) {
                        alert("无签到记录！");
                    } else {
                        //记录存在时去更新vuex中的仓储数据
                        that.getWorkerSigns({ workerSigns: rets.data, totalPages: rets.totalPages });
                        //并且更新页码。。。
                        that.cpage = pageNum;
                    }
                },
                function (error) {}
            );
        },


        /*------分页实现函数Start----------------------------------------------------------------------------------------------------- */
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
            this.getCurrentPageWorkers(jump);
        },
        /*------分页实现函数End----------------------------------------------------------------------------------------------------- */


                /*------删除一个员工----------------------------------------------------------------------------------------------------- */
        delWorkerSign(id){
            let access_token = JSON.parse(sessionStorage.getItem("access-token"));
            // console.log(access_token.token);

            let delWorkerSign = {
                id:id,
                token:access_token.token
            };

            let apiUrl = "http://localhost:8080/api/worker/sign/del"; //判断token的接口
            let _this = this;
            axios.post(apiUrl,delWorkerSign).then(
                function (response) {
                    if (response.data.code == "0") {
                        let obj = {
                            apiUrl:'http://localhost:8080/api/worker/sign/general',
                            //后端在接受json对象的时候如果一个键没有设置过，那它的键值就是null!
                            args:{
                                name:stringToNull(_this.searchArg.name),  //null原来也可以绑定空的框框，这蛮好的
                                enterprise:stringToNull(_this.searchArg.enterprise),
                                idCard:stringToNull(_this.searchArg.idCard),
                                team:stringToNull(_this.searchArg.team),

                                purpose:stringToNull(_this.searchArg.purpose),
                                sign:stringToNull(_this.searchArg.sign),
                                //开始时间和结束时间的格式转换好像还需要考虑一下，哈哈
                                startTime:dateFormat(_this.searchArg.startTime),
                                endTime:dateFormat(_this.searchArg.endTime),

                                pageNum:_this.cpage,
                            }
                        };
                        _this.asyncGetWorkerSigns(obj);


                    } else {
                        alert(response.data.msg);
                    }
                },
                function (error) {}
            );

        },

        /*------删除一堆员工----------------------------------------------------------------------------------------------------- */
        delWorkerSigns(ids){
            let access_token = JSON.parse(sessionStorage.getItem("access-token"));
            ids.push(access_token.token);

            console.log(ids);
            let apiUrl = "http://localhost:8080/api/worker/sign/dels"; //判断token的接口
            let _this = this;
            axios.post(apiUrl,ids).then(
                function (response) {
                    if (response.data.code == "0") {
                        let obj = {
                            apiUrl:'http://localhost:8080/api/worker/sign/general',
                            //后端在接受json对象的时候如果一个键没有设置过，那它的键值就是null!
                                args:{
                                name:stringToNull(_this.searchArg.name),  //null原来也可以绑定空的框框，这蛮好的
                                enterprise:stringToNull(_this.searchArg.enterprise),
                                idCard:stringToNull(_this.searchArg.idCard),
                                team:stringToNull(_this.searchArg.team),

                                purpose:stringToNull(_this.searchArg.purpose),
                                sign:stringToNull(_this.searchArg.sign),
                                //开始时间和结束时间的格式转换好像还需要考虑一下，哈哈
                                startTime:dateFormat(_this.searchArg.startTime),
                                endTime:dateFormat(_this.searchArg.endTime),

                                pageNum:_this.cpage,
                            }
                        };
                        _this.asyncGetWorkerSigns(obj);

                    }else {
                        alert(response.data.msg);
                    }
                },
                function (error) {}
            );
            //置ids为空
            this.ids = [];
        },





    }
}
</script>

<style scoped>
.myContainer{
    background: rgb(0 0 0 / 45%);
    border-radius: 10px;
    padding: 5px; 
    margin-bottom: 20px; 
}
.select{
    display: flex;
    align-items: center;
    /* justify-content: space-between; */
    padding: 10px 10px;
}
.span{
    width:30%;
    color:white;
    font-size: 16px;
    text-align: justify;/**实现分散对齐的技巧 */
    /* text-align-last:justify; */

}
.myInput{
    width:70%;
    /* height: 0%;  *只要设置了这个就会缩小一点固定的高度，倒不是真的直接变成0%了... */
    height: 25px;
    background:rgb(0 0 0 / 10%);
    color:white;

}
.operate{
    padding: 10px 10px;
}
.mySelect{
    background-color:rgb(0 0 0 / 10%);
    color:white;
    width:65%;
    height: 25px;
    padding: 0px 12px;
    font-size: 14px;
    text-align: justify;
}
.myOption{
    background-color:rgb(0 0 0 / 80%);
    font-size: 16px;
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