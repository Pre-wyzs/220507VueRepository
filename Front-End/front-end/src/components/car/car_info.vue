<template>
        <div class="container-fluid myContainer">
        <div class="row">
            <div class="col-sm">
                <div class="select">
                    <span class="span" >司机姓名</span>
                    <input type="text" class="form-control myInput" v-model="searchArg.name">
                </div>
            </div>
            <div class="col-sm">
                <div class="select">
                    <span class="span" >车架号IMEI</span>
                    <input type="text" class="form-control myInput" v-model="searchArg.imei">
                </div>
            </div>
            <div class="col-sm">
                <div class="select">
                    <span class="span" >所属物流</span>
                    <input type="text" class="form-control myInput" v-model="searchArg.team">
                </div>
            </div>
            <div class="w-100"></div>
            <div class="col-sm-4">
                <div class="select">
                    <span class="span" >车牌号</span>
                    <input type="text" class="form-control myInput" v-model="searchArg.license">
                </div>
            </div>
            <div class="col-sm-4">
                <div class="select">
                    <input type="datetime-local" style="width:40%;font-size:13px;" v-model="searchArg.startTime"/>
                    <label style="color:white;padding-top:2px;margin:0px 10px;">-至-</label>
                    <input type="datetime-local" style="width:40%;font-size:13px;" v-model="searchArg.endTime"/>
                </div>
            </div>
            <div class="w-100"></div>
            <div class="col-sm-6">
                <div class="operate">
                <div class="btn-group">
                    <button type="button" class="btn btn-outline-primary btn-sm" @click="getCurrentPageCars(1)">查询</button>
                    <!--这个新增我准备用toggle modal来做了，不然的话组件太多了，太耗费资源了-->
                    <button type="button" class="btn btn-outline-success btn-sm" data-toggle="modal" data-target="#modalBox">
                        新增
                    </button>
                        <div class="modal fade" id="modalBox" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title text-right">新增车辆</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="recipient-name" class="col-form-label" >姓名:</label>
                                    <input type="text" class="form-control" v-model="addCar.name">
                                    <label for="message-text" class="col-form-label">车架号:</label>
                                    <input type="text" class="form-control" v-model="addCar.imei">
                                    <label for="message-text" class="col-form-label">所属物流:</label>
                                    <input type="text" class="form-control" v-model="addCar.team">
                                    <label for="message-text" class="col-form-label">车牌号:</label>
                                    <input type="text" class="form-control" v-model="addCar.license">

                                    <label for="message-text" class="col-form-label">初始经度:</label>
                                    <input type="text" class="form-control" v-model="addCar.jd">

                                    <label for="message-text" class="col-form-label">初始纬度:</label>
                                    <input type="text" class="form-control" v-model="addCar.wd">
                                </div> 

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" data-dismiss="modal" @click="add(addCar)">添加</button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                            </div>
                            </div>
                        </div>
                        </div>

                    <button type="button" class="btn btn-outline-danger btn-sm" data-toggle="modal" data-target="#two">批量删除</button>
                        <div class="modal fade" id="two">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-body text-center text-secondary tips">
                                        确定要删除这些记录吗？
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-outline-warning" data-dismiss="modal" @click="delCars(ids)">确认</button>
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

                    <button type="button" class="btn btn-outline-info btn-sm" @click="getAllCarsAndExport()">导出excel</button>
                </div >
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
                <th class="text-primary">姓名</th>
                <th class="text-primary">车架号</th>
                <th class="text-primary">所属物流</th>
                <th class="text-primary">车牌号</th>
                <th class="text-primary">备案时间</th>
                <th class="text-primary">操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item,index) in cars" :key="index">
                <td><input type="checkbox" style="margin-left:10px;" v-model="ids" :value="cars[index].imei" checked></td>
                <td>{{item.name}}</td>
                <td>{{item.imei}}</td>
                <td>{{item.team}}</td>
                <td>{{item.license}}</td>
                <td>{{item.timeStamp}}</td>
                <td>
                    <!--修改的这一部分也交给那个toggle modal去做了-->
                    <button type="button" class="btn btn-outline-success btn-sm" style="margin-right: 10px" data-toggle="modal" data-target="#updateCar" @click="setCidAndCIMEI(cars[index].id,cars[index].imei)">
                        修改
                    </button>
                    <button type="button" class="btn btn-outline-danger btn-sm" data-toggle="modal" data-target="#delACar" @click="setCidAndCIMEI(cars[index].id,cars[index].imei)">
                        删除
                    </button>
                        
                </td>
            </tr>
            </tbody>
        </table>

<!-- 因为如果要删除一辆车辆信息的话，那么相应的轨迹记录也都会被删除掉的！ -->
            <div class="modal fade" id="delACar">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body text-center text-secondary tips">
                            确定要删除这辆车吗？如果删除，该车辆的所有路线记录也都会被删除！！！
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-warning" data-dismiss="modal" @click="delCar(cimei)">确认</button>
                            <button type="button" class="btn btn-outline-primary" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>


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
                    <li class="page-item" v-for="(item,index) in partPages(cgroup)" :key="index" @click="getCurrentPageCars(item)">
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
    <!-- 修改部分的模块 -->
                        <div class="modal fade" id="updateCar" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title text-right">修改车辆</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="recipient-name" class="col-form-label" >姓名:</label>
                                    <input type="text" class="form-control" v-model="newCar.name">
                                    <label for="message-text" class="col-form-label">所属物流:</label>
                                    <input type="text" class="form-control" v-model="newCar.team">
                                    <label for="message-text" class="col-form-label">车牌号:</label>
                                    <input type="text" class="form-control" v-model="newCar.license">
                                </div> 

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" data-dismiss="modal" @click="update(cid,cimei,newCar)">修改</button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                            </div>
                            </div>
                        </div>
                        </div>


</template>
<script>
import axios from 'axios'
import {stringToNull,dateFormat} from '../../utils/utils.js'
import ExportJsonExcel from 'js-export-excel'

export default {
    name:'CarInfo',
    created(){
        this.getCurrentPageCars(1);
    },
    data(){
        return {
        /*------批量删除ids---------------------------------------------------------------------------------------------------- */
            ids:[],
            //当前id
            cid:null,
            //当前imei,
            cimei:null,

        /*------分页变量----------------------------------------------------------------------------------------------------- */
            cpage:1,
            cgroup:0,
            jump:1,

        /*------查询参数----------------------------------------------------------------------------------------------------- */
            searchArg:{
                name:null,  //null原来也可以绑定空的框框，这蛮好的
                imei:null,
                team:null,
                license:null,
                startTime:null,
                endTime:null,
                jd:null,
                wd:null,

                token:null,
                pageNum:1,
                pageSize:6
            },

        /*------放弃vuex的仓储了，一个是代码量多，编译麻烦，还有一个就是运行效率比较低下，因为vue组件数据可以和后端直接交互的，现在加了一个中间层，
        就导致效率低下了，而且数据并不复杂，所以没必要用仓储的，仓储的目的没有达到就不需要它了----------------------------------------------------------------------------------------------------- */
            cars:[],
            totalPages:1,

        /*------新增参数----------------------------------------------------------------------------------------------------- */
            addCar:{
                name:'',
                imei:'',
                license:'',
                team:'',
                //车辆的初始点位就是杭钢图书馆
                jd:120.181174,
                wd:30.350655,
                token:''
            },

        /*------更改的参数----------------------------------------------------------------------------------------------------- */
        //这个参数里面的imei号是没有办法改变的，因为要更新一辆车辆的信息，那么对应的车辆历史记录表中的车辆相应的信息也是要改变的才对，
        //如果连imei设备号都改变了，那就无法对应了。。。
            newCar:{
                id:'',
                imei:'',
                name:'',
                license:'',
                team:'',
                token:''
            },


        }
    },
    computed:{

    },
    methods:{
        /*------添加一辆新车----------------------------------------------------------------------------------------------------- */
        setCidAndCIMEI(id,imei){
            this.cid = id;
            this.cimei = imei;
        },
        add(addCar){
            //参数不为''的判断
            if(addCar.name=='' || addCar.imei=='' || addCar.license == '' ||addCar.team == ''){
                alert('请填写所有字段！');
                return false;
            }
            let access_token = JSON.parse(sessionStorage.getItem("access-token"));
            let that = this;
            addCar.token = access_token.token;
            axios.post("http://localhost:8080/api/car/info/add",addCar).then(function(response){
                if(response.data.code == '0'){
                    that.getCurrentPageCars(1);
                }
            },function(error){});
        },
        /*------修改一辆车的信息----------------------------------------------------------------------------------------------------- */
        update(id,imei,newCar){
            //参数不为''的判断
            if(newCar.name=='' ||  newCar.license == '' || newCar.team == ''){
                alert('请填写所有字段！');
                return false;
            }
            let access_token = JSON.parse(sessionStorage.getItem("access-token"));
            let that = this;
            newCar.token = access_token.token;
            newCar.id = id;
            newCar.imei = imei;

            axios.post("http://localhost:8080/api/car/info/update",newCar).then(function(response){
                if(response.data.code == '0'){
                    that.getCurrentPageCars(1);
                }
            },function(error){});
        },

        /*------导出allExcel----------------------------------------------------------------------------------------------------- */
        async getAllCarsAndExport(){
            const {data: ret} = await axios.get('http://localhost:8080/api/car/info/all');
            console.log('导出数据：',ret.data);
            //对导出的数据不能直接放到excel中的，不然就会导致excel表字段对应的很乱..
            let all = [] 
            ret.data.forEach(item=>{
                all.push({
                    id:item.id,
                    name:item.name,
                    team:item.team,
                    license:item.license,
                    timeStamp:item.timeStamp,
                    jd:item.jd,
                    wd:item.wd,
                    imei:item.imei
                });
            });
            this.allExport(all);

        },

        /*------真正执行导出的函数----------------------------------------------------------------------------------------------------- */
        allExport(all){

            let HeaderArr = ['ID','姓名','所属物流','车牌号','时间戳','经度','维度','IMEI'];

            //配置option选项.
            var option={};
            option.fileName = 'AllCarsData'
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
            this.cars.forEach(item => {
                str = item.id+' '+item.name+' '+item.imei+' '+item.team+' '+item.license+' '+item.timeStamp+' '+item.jd+' '+item.wd;
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
        getCurrentPageCars(pageNum) {
            //这个参数是说明分页的具体类型的..
            let apiUrl = 'http://localhost:8080/api/car/info/general';

            //这个基本上就是标准的构造args的形式了，因为刚开始这些属性确实是null没有错，但是
            //当一个输入框输入内容，然后删除内容后，又会变成 '' 了，所以要把所有的''变成null才行！
            let args = {
                name:stringToNull(this.searchArg.name),  //null原来也可以绑定空的框框，这蛮好的
                imei:stringToNull(this.searchArg.imei),
                team:stringToNull(this.searchArg.team),
                license:stringToNull(this.searchArg.license),
                startDate:dateFormat(this.searchArg.startTime),
                endDate:dateFormat(this.searchArg.endTime),
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
                        alert("该车辆不存在!");
                    } else {
                        //用户存在时才去更新本地对应的数据
                        that.cars = rets.data;
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


        
        /*------修改车辆信息----------------------------------------------------------------------------------------------------- */



        /*------删除一辆车----------------------------------------------------------------------------------------------------- */
        delCar(imei){
            let access_token = JSON.parse(sessionStorage.getItem("access-token"));
            // console.log(access_token.token);
            let apiUrl = "http://localhost:8080/api/car/info/del"; //判断token的接口
            let _this = this;

            axios.post(apiUrl,{imei:imei,token:access_token.token}).then(
                function (response) {
                    if (response.data.code == "0") {
                        //更新数据：妈的这样写多勾八简洁，还要用vuex那么麻烦的东西吗？给爷整笑啦
                        _this.getCurrentPageCars(_this.cpage);
                    } else {
                        alert(response.data.msg);
                    }
                },
                function (error) {}
            );
        },

        /*------删除一堆员工----------------------------------------------------------------------------------------------------- */
        delCars(ids){
            let access_token = JSON.parse(sessionStorage.getItem("access-token"));
            ids.push(access_token.token);

            console.log(ids);
            let apiUrl = "http://localhost:8080/api/car/info/dels"; //判断token的接口
            let _this = this;
            axios.post(apiUrl,ids).then(
                function (response) {
                    if (response.data.code == "0") {
                        _this.getCurrentPageCars(_this.cpage);
                    }else {
                        alert(response.data.msg);
                    }
                },
                function (error) {}
            );
            //置ids为空
            this.ids = [];
        },



    },
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
