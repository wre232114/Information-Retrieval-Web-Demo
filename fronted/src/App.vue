<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>


export default {
  name: 'App',
  components:{
    
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
  width: 90%;
  margin: auto auto 0 auto;
}
/*全局头设置*/
.page-head{
  margin-top:20px;
  text-align: center;
}
.row-margin{
  margin-top:20px;
}

.table-header-style th{
  font-weight: bold;
  text-align: center;
}
/* 全局面包屑样式*/
.crumb{
    border-bottom:turquoise solid 5px;
}
</style>
