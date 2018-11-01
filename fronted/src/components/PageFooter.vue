<!--  -->
<template>
  <div>
      <el-row style="text-align:center;margin-top:60px;">
          <span>您好，当前时间是: <span style="font-weight:bold">{{currentDate}}</span></span>
          <i class="el-icon-time"></i>
          <br><br>
          <strong>&copy;copyright www.brightblog.cn <br>author bright 2018.10</strong>
      </el-row>
  </div>
</template>

<script>
export default {
  name: '',
  data () {
    return {
      currentDate: ''
    };
  },

  components: {},

  computed: {},

  mounted() {
    this.showDate();
    setInterval(this.showDate,1000);
  },

  methods: {
    showDate(){
      let date = new Date();
      this.currentDate=date.getFullYear()+'年'+date.getMonth()+'月'+date.getDate()+'日'+date.getHours()+
        '点'+date.getMinutes()+'分'+date.getSeconds()+'秒';
    }
  }
}

</script>
<style scoped>
  div {
    color: #909399;
    font-size: 0.9em;
  }
</style>