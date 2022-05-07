
<template>
        <div class="container-fluid myContainer">
        <div class="row">
            <div class="col-sm-4">
                <div class="select">
                    <span class="span" >车牌号</span>
                    <input type="text" class="form-control myInput" v-model="license">
                </div>
            </div>
            <div class="col-sm-4">
                <div class="select">
                    <span class="span" >车架号IMEI</span>
                    <input type="text" class="form-control myInput" v-model="imei">
                </div>
            </div>
            <div class="w-100"></div>
            <div class="col-sm-6">
                <div class="operate">
                <button type="button" class="btn btn-outline-success btn-sm" @click="getCurrentPageCarHistorys(1)">查询</button>
                <div class="btn-group">
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
                    <button type="button" class="btn btn-outline-info btn-sm" @click="getAllCarHistorysAndExport()">导出excel</button>
                </div >
                </div>
            </div>
        </div>
    </div>


        <!-- 表格数据部分 -->
        <table class="table table-dark table-striped table-hover my-table">
            <thead>
            <tr>
                <th class="text-primary">车牌号</th>
                <th class="text-primary">车架号</th>
                <th class="text-primary">经度</th>
                <th class="text-primary">维度</th>
                <th class="text-primary">时间戳</th>
                <th class="text-primary">车辆状态</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item,index) in carHistorys" :key="index">
                <td>{{item.license}}</td>
                <td>{{item.imei}}</td>
                <td>{{item.jd}}</td>
                <td>{{item.wd}}</td>
                <td>{{item.timeStamp}}</td>
                <td :class="carHistorys[index].carStatus==0?'stop':'run'">{{item.carStatus==0?'已熄火':'行驶中'}}</td>
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
                    <li class="page-item" v-for="(item,index) in partPages(cgroup)" :key="index" @click="getCurrentPageCarHistorys(item)">
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
import axios from 'axios'
import ExportJsonExcel from 'js-export-excel'
import {stringToNull,dateFormat} from '../../utils/utils.js'

export default {
    name:'CarHistory',
    created(){
        this.getCurrentPageCarHistorys(1);

                // console.log("已从后端获取数据");
        var socket = new WebSocket("ws://localhost:8080/ws/api/equipment");
        let _this = this;
        socket.onopen = function(){
            console.log('websocket linked');
            //成功建立链接后发送这个连接的唯一名称
            socket.send("Car-Linking");
        }
        /**在这个钩子函数中开启socket的监听事件 ：当服务器的session向它发送消息的时候，自然就会调用到这个回调函数了*/
        socket.onmessage = function(evt){
            //服务端返回的就是字符串，得通过evt.data来获取
            let retStr = evt.data;

            if(retStr == 'Status_Updated'){
                //重新更新页面捏，不是从websocket接口中直接拿数据，有点捞捏!
              console.log("消息已送到");
                _this.getCurrentPageCarHistorys(_this.cpage);
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

        /*------分页变量----------------------------------------------------------------------------------------------------- */
            cpage:1,
            cgroup:0,
            jump:1,

        /*------查询参数：只有两个----------------------------------------------------------------------------------------------------- */
            license:null,
            imei:null,

        /*------放弃vuex的仓储了，一个是代码量多，编译麻烦，还有一个就是运行效率比较低下，因为vue组件数据可以和后端直接交互的，现在加了一个中间层，
        就导致效率低下了，而且数据并不复杂，所以没必要用仓储的，仓储的目的没有达到就不需要它了----------------------------------------------------------------------------------------------------- */
            carHistorys:[],
            totalPages:1,

        }
    },
    methods:{
                /*------导出allExcel----------------------------------------------------------------------------------------------------- */
        async getAllCarHistorysAndExport(){
            const {data: ret} = await axios.get('http://localhost:8080/api/car/history/all');
            console.log('导出数据：',ret.data);
            //对导出的数据不能直接放到excel中的，不然就会导致excel表字段对应的很乱..
            let all = [] 
            ret.data.forEach(item=>{
                all.push({
                    id:item.id,
                    license:item.license,
                    imei:item.imei,
                    jd:item.jd,
                    wd:item.wd,
                    timeStamp:item.timeStamp,
                    carStatus:item.carStatus==0?'已熄火':'行驶中'
                });
            });
            this.allExport(all);

        },

        /*------真正执行导出的函数----------------------------------------------------------------------------------------------------- */
        allExport(all){

            let HeaderArr = ['ID','车牌号','IMEI','经度','纬度','时间戳','车辆状态'];

            //配置option选项.
            var option={};
            option.fileName = 'AllCarHistorysData'
            option.datas=[
                {
                    sheetData:all,
                    sheetHeader:HeaderArr//前端指定的表头
                },
            ];
            var toExcel=new ExportJsonExcel(option);
            toExcel.saveExcel();
        },
        
                /*------拷贝数据到剪切板----------------------------------------------------------------------------------------------------- */
        copyTableData(){
            let content = '';
            let str = '';
            this.carHistorys.forEach(item => {
                if(item.carStatus==0){
                    str = item.id+' '+item.license+' '+item.imei+' '+item.jd+' '+item.wd+' '+item.timeStamp+' '+'已熄火';
                }else{
                    str = item.id+' '+item.license+' '+item.imei+' '+item.jd+' '+item.wd+' '+item.timeStamp+' '+'行驶中';
                } 
                content += str + '\n';
            });

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
        getCurrentPageCarHistorys(pageNum) {
            //这个参数是说明分页的具体类型的..
            let apiUrl = 'http://localhost:8080/api/car/history/general';

            //这个基本上就是标准的构造args的形式了，因为刚开始这些属性确实是null没有错，但是
            //当一个输入框输入内容，然后删除内容后，又会变成 '' 了，所以要把所有的''变成null才行！
            let args = {
                license:stringToNull(this.license),
                imei:stringToNull(this.imei),
                token:null,
                pageNum:pageNum,
                pageSize:6
            };
            console.log("查询参数",args);

            let that = this;
            axios.put(apiUrl,args).then(
                function (response) {
                    let rets = response.data;
                    if (rets.data.length == 0) {
                        alert("无记录!");
                    } else {
                        //数据存在时才去更新本地缓存
                        that.carHistorys = rets.data;
                        // console.log(that.cars);
                        that.totalPages = rets.totalPages;
                        // console.log('总页数：',that.totalPages);
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
            this.getCurrentPageCars(jump);
        },
        /*------分页实现函数End----------------------------------------------------------------------------------------------------- */



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

.run{
    color:#76FF03
}
.stop{
    color:#FA0027;
    
}
</style>


