<!--  -->
<template>
  <div>
      <el-row class="row-header">
        <el-col :span="10" :offset="2">
            <P>您好，欢迎来到个人信息检索系统！</P>
        </el-col>
        <el-col :span="5" :offset="7" class="my-logo">
            <el-row><i>brightblog.cn</i></el-row>
            <el-row class="my-logo-attach">try hard to make life better</el-row>
        </el-col>
     </el-row>
  </div>
</template>

<script>
export default {
  name: '',
  data () {
    return {
        
    };
  },

  components: {},

  computed: {},

  mounted() {
    
  },

  methods: {
  }
}

</script>
<style scoped>
  .row-header {
    background:#409EFF;
    color:white;
    border-radius: 5px;
    box-shadow: #409EFF 2px 2px 1px 1px;
  }

  .my-logo {
      font-size: 1.5em;
      text-decoration: underline;
  }
  .my-logo-attach {
      font-size: 0.5em;
      margin-left: 1em;
  }
</style>