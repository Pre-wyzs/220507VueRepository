<template>
    <nav aria-label="breadcrumb">
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">主页</a></li>
        <li class="breadcrumb-item"><a href="#">{{currentPPath}}</a></li>
        <li class="breadcrumb-item active" aria-current="page">{{currentCPath}}</li>
      </ol>
    </nav>
</template>
<script>

export default {
    name:'Nav',
    props:['currentPPath','currentCPath']
}
</script>
<style scoped>

</style>