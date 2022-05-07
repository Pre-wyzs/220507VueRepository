<template>

     <!--navigation-->
    <ul class="menu" id="menu">
        <item :title="this.$parent.isShrink?'':'视频监控'" :subItems="subItems1" :id0="0" :isList0="flags[0]">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-video text-white" > <polygon points="23 7 16 12 23 17 23 7"></polygon> <rect x="1" y="5" width="15" height="14" rx="2" ry="2"></rect> </svg>
        </item>
        <item :title="this.$parent.isShrink?'':'车辆管理'" :subItems="subItems2" :id0="1" :isList0="flags[1]">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-map-pin text-white"><path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path><circle cx="12" cy="10" r="3"></circle></svg>
        </item>
        <item :title=" this.$parent.isShrink?'':'考勤管理'" :subItems="subItems3" :id0="2" :isList0="flags[2]">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-check-square text-white"><polyline points="9 11 12 14 22 4"></polyline><path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"></path></svg>
        </item>
        <item :title=" this.$parent.isShrink?'':'设备管理'" :subItems="subItems4" :id0="3" :isList0="flags[3]">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-hard-drive text-white"><line x1="22" y1="12" x2="2" y2="12"></line><path d="M5.45 5.11L2 12v6a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2v-6l-3.45-6.89A2 2 0 0 0 16.76 4H7.24a2 2 0 0 0-1.79 1.11z"></path><line x1="6" y1="16" x2="6.01" y2="16"></line><line x1="10" y1="16" x2="10.01" y2="16"></line></svg>
        </item>
        <item :title=" this.$parent.isShrink?'':'空气扬尘'" :subItems="subItems5" :id0="4" :isList0="flags[4]">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-hexagon text-white"><path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"></path></svg>
        </item>
        <item :title=" this.$parent.isShrink?'':'尾气监测'" :subItems="subItems6" :id0="5" :isList0="flags[5]">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-pocket text-white"><path d="M4 3h16a2 2 0 0 1 2 2v6a10 10 0 0 1-10 10A10 10 0 0 1 2 11V5a2 2 0 0 1 2-2z"></path><polyline points="8 10 12 14 16 10"></polyline></svg>
        </item>
        <item :title=" this.$parent.isShrink?'':'用户管理'" :subItems="subItems7" :id0="6" :isList0="flags[6]">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users text-white"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
        </item>

    </ul>
    <!-- end of aside-menu -->

</template>
<script>
import Item from './aside_item/item.vue'

export default {
    name:'AsideItem',
    created(){
        if(sessionStorage.getItem('listFlags')!=null){
            var arr = sessionStorage.getItem('listFlags').split(',');
            for(var i=0;i < arr.length;i ++){
                if(arr[i] == 'true'){
                    arr[i] = true;
                }else {
                    arr[i] = false;
                }
            }
            this.flags = arr;
            console.log(this.flags);
        }
    },
    data() {
        return {
            subItems1:[{module:'实时监控',path:'/home/video/info'}],
            subItems2:[{module:'路线跟踪',path:'/home/map/info'},
                       {module:'跟踪记录',path:'/home/car/history'},
                       {module:'车辆信息',path:'/home/car/info'},
                       {module:'喷淋记录',path:'/home/car/pl'}
                       ],
            subItems3:[
                {module:'员工管理',path:'/home/worker/info'},
                {module:'考勤记录',path:'/home/worker/sign'},
                {module:'考勤设置',path:'/home/worker/setting'},
            ],
            subItems4:[{module:'设备信息',path:'/home/device/info'},{module:'历史记录',path:'/home/device/history'}],
            subItems5:[{module:'实时监测',path:'/home/dust/info'},{module:'监测记录',path:'/home/dust/history'}],
            subItems6:[{module:'实时监测',path:'/home/exhaust/info'}, {module:'监测记录',path:'/home/exhaust/history'}],
            subItems7:[{module:'管理员信息',path:'/home/admin'}],
            flags:[false,false,false,false,false,false,false]
        }
    },
    methods: {
        handle(e) {
            this.flags[e] = true;
            //console.log(this.flags);
            for(var i=0;i<7;i++){
                if(i!=e){
                    this.flags[i] = false;
                }
            }
            //console.log(this.flags);

        },
        //关闭所有子列表的 接口
        closeAll() {
            this.flags = [false,false,false,false,false,false,false]
        },
        //保存当前标志位的接口
        saveListStatus(){
            sessionStorage.setItem('listFlags',this.flags);
            // console.log(sessionStorage.getItem('listFlags').split(','));
        }
    },
    components:{
        Item,
    }
}
</script>
<style scoped>
.menu {
  padding: 10px;
  position: relative;
}

</style>