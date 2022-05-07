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
                    <span class="span" >手机号</span>
                    <input type="text" class="form-control myInput" v-model="searchArg.phone">
                </div>
            </div>
            <div class="w-100"></div>
            <div class="col-sm-3">
                <div class="select">
                    <span class="span" >员工状态</span>
                    <select class="custom-select mySelect" v-model="searchArg.status">
                        <option selected class="myOption text-primary" value="">所有</option>
                        <option value="1" class="myOption text-success">在职</option>
                        <option value="2" class="myOption text-warning">请假</option>
                        <option value="0" class="myOption text-danger">离职</option>
                    </select>
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
            <div class="col-sm-3">
                <div class="select">
                    <span class="span" >年龄</span>
                    <input type="text" class="form-control myInput" v-model="searchArg.age">
                </div>
            </div>
            <div class="col-sm-3">
                <div class="select">
                    <span class="span" >工种</span>
                    <input type="text" class="form-control myInput" v-model="searchArg.kind">
                </div>
            </div>
            <div class="w-100"></div>
            <div class="col-sm-6">
                <div class="operate">
                <div class="btn-group">
                    <button type="button" class="btn btn-outline-primary btn-sm" @click="getCurrentPageWorkers(1)">查询</button>
                    <router-link to="/home/worker/info/add">
                    <button type="button" class="btn btn-outline-success btn-sm">
                        新增
                    </button>
                    </router-link>
                    <button type="button" class="btn btn-outline-danger btn-sm" data-toggle="modal" data-target="#two">批量删除</button>
                        <div class="modal fade" id="two">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-body text-center text-secondary tips">
                                        确定要删除这些记录吗？
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-outline-warning" data-dismiss="modal" @click="delWorkers(ids)">确认</button>
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

                    <button type="button" class="btn btn-outline-info btn-sm" @click="getAllWorkersAndExport()">导出excel</button>
                </div >
                </div>
            </div>
            <!-- 上传excel数据部分 -->
            <div class="col-sm-6">
                <label class="lqwvje-btn">
                    <input type="file" hidden="hidden" ref="uploadExcel">
                    上传文件
                </label>
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
                <th class="text-primary">班组</th>
                <th class="text-primary">身份证</th>
                <th class="text-primary">年龄</th>
                <th class="text-primary">性别</th>
                <th class="text-primary">手机</th>
                <th class="text-primary">企业单位</th>
                <th class="text-primary">工种</th>
                <th class="text-primary">状态</th>
                <th class="text-primary">操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item,index) in retWorkers" :key="index">
                <td><input type="checkbox" style="margin-left:10px;" v-model="ids" :value="retWorkers[index].id" checked></td>
                <td>{{item.id}}</td>
                <td>{{item.name}}</td>
                <td>{{item.team}}</td>
                <td>{{item.idCard}}</td>
                <td>{{item.age}}</td>
                <td>{{item.sex}}</td>
                <td>{{item.phone}}</td>
                <td>{{item.enterprise}}</td>
                <td>{{item.kind}}</td>
                <td>{{item.status}}</td>
                <td>
                    <button type="button" class="btn btn-outline-success btn-sm" style="margin-right: 10px" @click="toModify(retWorkers[index].id)" >
                        修改
                    </button>
                    <button type="button" class="btn btn-outline-danger btn-sm" data-toggle="modal" data-target="#exampleModal" @click="delWorker(retWorkers[index].id)" >
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
                    <li class="page-item" v-for="(item,index) in partPages(cgroup)" :key="index" @click="getCurrentPageWorkers(item)">
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
import {stringToNull} from '../../utils/utils'
const { mapState, mapGetters, mapActions, mapMutations } = createNamespacedHelpers('worker');;
import ExportJsonExcel from 'js-export-excel'

export default {
    name:'WorkerInfo',
    created(){
        /*-----------------------初始化数据------------------------------------------------------------------------------------ */
        let obj = {
            apiUrl:'http://localhost:8080/api/worker/info/general',
            //后端在接受json对象的时候如果一个键没有设置过，那它的键值就是null!
            args:{
                pageNum:1,
                pageSize:6,
            }
        }
        this.asyncGetWorkers(obj);
        /*----------------------------------------------------------------------------------------------------------- */

    },
    mounted(){
        /*------开启文件上传监听事件：因为函数中要调用dom所以必须在mounted中调用该函数---------------------------------------------------------------------------------------------------- */
        this.uploadExcel();
    },
    data(){
        return {

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
                phone:null,
                status:null,
                team:null,
                age:null,
                kind:null,
                token:null,
                pageNum:1,
                pageSize:6
            }

        }
    },
    computed:{

        /*------仓储拓展----------------------------------------------------------------------------------------------------- */
        ...mapState(['workers','totalPages']),
        ...mapGetters(['retWorkers'])
    },
    methods:{
        /*------仓储拓展----------------------------------------------------------------------------------------------------- */
        ...mapMutations(['getWorkers']),
        ...mapActions(['asyncGetWorkers']),

        /*------通过excel导入员工数据----------------------------------------------------------------------------------------------------- */
        uploadExcel(){

            //首先监听input框的变动，选中一个新的文件会触发change事件
            let dom = this.$refs.uploadExcel;

            dom.addEventListener('change',function(){
                console.log('监听事件已经开启');
                //判断文件大小
                var file = dom.files[0];
                if(file.size > 1024*1024){
                    alert("上传文件不能大于1Mb");
                    return false;
                }

                var type = file.name.split('.');
                if(type[type.length-1]!='xlsx'&&type[type.length-1]!='xls'){
                    alert('文件类型错误,请选择excel文件导入!');
                    return false
                }

                const reader = new FileReader();
                reader.readAsBinaryString(file);
                reader.onload = function(e){
                    const data = e.target.result;
                    const zzexcel = window.XLS.read(data,{
                        type:'binary'
                    });

                    const result = [];
                    for (let i = 0; i < zzexcel.SheetNames.length; i++) {
                        
                        const newData = window.XLS.utils.sheet_to_json(zzexcel.Sheets[zzexcel.SheetNames[i]]);
                        result.push(...newData)
                    }
                    
                    let json = JSON.stringify(result);  //获取到的json串
                    //改变键名
                    let temp = json.replaceAll('姓名','name');
                    temp = temp.replaceAll('班组类别','team');
                    temp = temp.replaceAll('身份证','idCard');
                    temp = temp.replaceAll('年龄','age');
                    temp = temp.replaceAll('性别','sex');
                    temp = temp.replaceAll('手机','phone');
                    temp = temp.replaceAll('照片','photo');
                    temp = temp.replaceAll('工种','kind');
                    temp = temp.replaceAll('企业单位','enterprise');
                    let tempObj = JSON.parse(temp);
                    tempObj.forEach(item => {
                        if(item.team == '管理班组'){
                            item.team = 1
                        }else{
                            item.team = 2
                        }

                        if(item.sex == '男'){
                            item.sex = 1
                        }else{
                            item.sex = 2
                        }
                    })

                    // console.log(tempObj);
                    axios.post("http://localhost:8080/api/worker/info/adds",tempObj).then(function(response){
                        if(response.data.code == '0'){
                            alert("导入成功!");
                        }else{
                            alert("导入失败!");
                        }

                    },function(error){
                    });

                }
            });


        },



        /*------导出allExcel----------------------------------------------------------------------------------------------------- */
        async getAllWorkersAndExport(){
            const {data: ret} = await axios.get('http://localhost:8080/api/worker/info/all');

            ret.data.forEach(item => {
                if(item.team == '1'){
                    item.team = '管理班组'
                }else{
                    item.team = '劳务班组'
                }

                if(item.sex == 1){
                    item.sex = '男'
                }else{
                    item.sex = '女'
                }
                
                if(item.status == 1){
                    item.status = '在职'
                }else if(item.status == 2){
                    item.status = '请假' 
                }else{
                    item.status = '离职'
                }
            });
            
            this.allWorkersExport(ret.data);

        },

        /*------真正执行导出的函数----------------------------------------------------------------------------------------------------- */
        allWorkersExport(allWorkers){
            let HeaderArr = ['ID','姓名','班组','身份证','年龄','性别','手机','企业单位','工种','照片','状态'];
            //配置option选项.
            var option={};
            option.fileName = 'AllWorkersData'
            option.datas=[
                {
                    sheetData:allWorkers,
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
            for(let i=0;i<this.retWorkers.length;i++){
                str = this.retWorkers[i].id + ' ' + this.retWorkers[i].name + ' ' + this.retWorkers[i].team + ' '
                + this.retWorkers[i].idCard + ' ' + this.retWorkers[i].age + ' ' + this.retWorkers[i].sex + ' ' +
                this.retWorkers[i].phone + ' ' + this.retWorkers[i].enterprise + ' ' + this.retWorkers[i].kind + ' ' + 
                this.retWorkers[i].status;

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
        getCurrentPageWorkers(pageNum) {
            //这个参数是说明分页的具体类型的..
            let apiUrl = 'http://localhost:8080/api/worker/info/general';

            //这个基本上就是标准的构造args的形式了，因为刚开始这些属性确实是null没有错，但是
            //当一个输入框输入内容，然后删除内容后，又会变成 '' 了，所以要把所有的''变成null才行！
            let args = {
                name:stringToNull(this.searchArg.name),  //null原来也可以绑定空的框框，这蛮好的
                enterprise:stringToNull(this.searchArg.enterprise),
                idCard:stringToNull(this.searchArg.idCard),
                phone:stringToNull(this.searchArg.phone),
                status:stringToNull(this.searchArg.status),
                team:stringToNull(this.searchArg.team),
                age:stringToNull(this.searchArg.age),
                kind:stringToNull(this.searchArg.kind),
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
                        alert("该员工不存在！");
                    } else {
                        //用户存在时才去更新vuex中的仓储数据
                        that.getWorkers({ workers: rets.data, totalPages: rets.totalPages });
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


        
        /*------修改员工信息----------------------------------------------------------------------------------------------------- */
        toModify(id){
            this.$router.push({name:'UpdateWorker',params:{id:id}});
        },


        /*------删除一个员工----------------------------------------------------------------------------------------------------- */
        delWorker(id){
            let access_token = JSON.parse(sessionStorage.getItem("access-token"));
            // console.log(access_token.token);

            let delWorker = {
                id:id,
                token:access_token.token
            };

            let apiUrl = "http://localhost:8080/api/worker/info/del"; //判断token的接口
            let _this = this;
            axios.post(apiUrl, delWorker).then(
                function (response) {
                    if (response.data.code == "0") {
                        let obj = {
                            apiUrl:'http://localhost:8080/api/worker/info/general',
                            //后端在接受json对象的时候如果一个键没有设置过，那它的键值就是null!
                            args:{
                                name:stringToNull(_this.searchArg.name),  //null原来也可以绑定空的框框，这蛮好的
                                enterprise:stringToNull(_this.searchArg.enterprise),
                                idCard:stringToNull(_this.searchArg.idCard),
                                phone:stringToNull(_this.searchArg.phone),
                                status:stringToNull(_this.searchArg.status),
                                team:stringToNull(_this.searchArg.team),
                                age:stringToNull(_this.searchArg.age),
                                kind:stringToNull(_this.searchArg.kind),
                                pageNum:_this.cpage,
                            }
                        };
                        _this.asyncGetWorkers(obj);


                    } else {
                        alert(response.data.msg);
                    }
                },
                function (error) {}
            );

        },

        /*------删除一堆员工----------------------------------------------------------------------------------------------------- */
        delWorkers(ids){
            let access_token = JSON.parse(sessionStorage.getItem("access-token"));
            ids.push(access_token.token);

            console.log(ids);
            let apiUrl = "http://localhost:8080/api/worker/info/dels"; //判断token的接口
            let _this = this;
            axios.post(apiUrl,ids).then(
                function (response) {
                    if (response.data.code == "0") {
                        let obj = {
                            apiUrl:'http://localhost:8080/api/worker/info/general',
                            //后端在接受json对象的时候如果一个键没有设置过，那它的键值就是null!
                            args:{
                                name:stringToNull(_this.searchArg.name),  //null原来也可以绑定空的框框，这蛮好的
                                enterprise:stringToNull(_this.searchArg.enterprise),
                                idCard:stringToNull(_this.searchArg.idCard),
                                phone:stringToNull(_this.searchArg.phone),
                                status:stringToNull(_this.searchArg.status),
                                team:stringToNull(_this.searchArg.team),
                                age:stringToNull(_this.searchArg.age),
                                kind:stringToNull(_this.searchArg.kind),
                                pageNum:_this.cpage,
                            }
                        };
                        _this.asyncGetWorkers(obj);

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

/*------导入excel的input框样式----------------------------------------------------------------------------------------------------- */
.lqwvje-btn {
    display: inline-block;
    height: 38px;
    line-height: 38px;
    padding: 0 18px;
    background-color: #009688;
    color: #fff;
    white-space: nowrap;
    text-align: center;
    font-size: 14px;
    border: none;
    border-radius: 2px;
    cursor: pointer;
    margin-top:5px;
}


</style>