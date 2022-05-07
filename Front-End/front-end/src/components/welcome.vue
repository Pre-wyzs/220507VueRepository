<template>
    <div class="back">
        <ul>
            <li class="title">欢迎登录智慧工地后台管理系统</li>
            <li class="text">作者：唯一樟树/Pre-wyzs</li>
            <li class="text">时间：2022/4/25</li>
            <li class="text">简介：一个简单的毕设项目</li>
            <li class="text">作者邮箱：2095154174@qq.com</li>
        </ul>
    </div>
</template>
<script>

export default {
    name:'Welcome'
}
</script>
<style scoped>
.back{
    background: rgb(0 0 0 / 45%);
    border-radius: 5px;
    padding: 10px;
    height: 100%;
    display:flex;
    justify-content: center;
}

ul{
    color:white;
    list-style-type: none;
}
.title{
    font-size:20px;
    font-weight: bolder;
}
.text{
    text-align: center;
}


</style>
