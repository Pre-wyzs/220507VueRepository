<template>
    
    <transition name="openOpt">
        <div class="opt" v-show="isOpt">
            <ul>
                <li>
                    <svg style="margin:0 10px;" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-user text-white"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>
                    <span>我的</span>
                </li>
                <router-link to="/setting" style="text-decoration: none;">
                <li>
                    <svg  style="margin:0 10px;" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-settings text-white"><circle cx="12" cy="12" r="3"></circle><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path></svg>
                    <span>
                        设置
                    </span>
                </li>
                </router-link>

                <router-link to="/" style="text-decoration: none;">
                <li style="rgb(255 255 255 / 25%);">
                    <svg style="margin:0 10px;" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-aperture text-white"><circle cx="12" cy="12" r="10"></circle><line x1="14.31" y1="8" x2="20.05" y2="17.94"></line><line x1="9.69" y1="8" x2="21.17" y2="8"></line><line x1="7.38" y1="12" x2="13.12" y2="2.06"></line><line x1="9.69" y1="16" x2="3.95" y2="6.06"></line><line x1="14.31" y1="16" x2="2.83" y2="16"></line><line x1="16.62" y1="12" x2="10.88" y2="21.94"></line></svg>
                    <span>
                        <div style="color:white;text-decoration:none;">
                            主页
                        </div>
                    </span>
                </li>
                </router-link>

                <li style="border-top:1px solid rgb(255 255 255 / 25%);" @click="logout">
                    <svg  style="margin:0 10px;" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-power text-white"><path d="M18.36 6.64a9 9 0 1 1-12.73 0"></path><line x1="12" y1="2" x2="12" y2="12"></line></svg>
                    <span>
                        <div style="color:white;text-decoration:none;">
                            退出
                        </div>
                    </span>
                </li>

            </ul>
            <!-- 模态框 -->
            <!-- Modal -->


        </div>

    </transition>

</template>
<script>

export default {
    name:'HeaderOpt',
    props:['isOpt'],
    data() {
        return {
        }
    },
    methods:{
        logout(){
            this.$router.push({path:'/logout'});

        }
    }
}
</script>
<style scoped>
.opt {
    position: relative;
    width: 70%;
    left: 15%;
    background-color: #1E1E1E;
    border-radius: 5px;
    padding:10px;
    margin-top:10px;
    z-index: 11;
}

.opt ul li {
    color: white;
    list-style: none;
    text-align: left;
    font-weight: 400;
    font-size: 17px;
    margin: 5px 0;
    cursor: pointer;
    caret-color: rgba(0,0,0,0);  /*设置聚焦时候光标变为透明，这样就不会出现一闪一闪的效果了 */ 

    display: flex;
    align-items:center;

}
/* li:focus-within {
    color:red;
    background: rgb(255 255 255 / 15%);
} */
.opt ul li:hover {
    background: rgb(255 255 255 / 15%);
}

.opt::before{
    position: absolute;
    content:'';
    width:15px;
    height:15px;
    background:#1E1E1E;
    transform:rotate(45deg);
    top:-8px;
    left:70%;
    border-top:1px solid white;
    border-left:1px solid white;

}

/**动画过渡效果的实现 */
.openOpt-enter-from,.openOpt-leave-to {
    opacity:0;
    height: 1px;
}
.openOpt-enter-active,.openOpt-leave-active {
    transition:all .3s ease-out;
}
/* .openOpt-leave-from,.openOpt-enter-to {
    opacity:1;
} */



</style>
