<!--  -->
<template>
  <div>
    <el-row class="userInfo">
      <img src="../assets/portrait.jpg">
      <span>{{userName}}</span>
      <el-button type="text" @click="logout">注销</el-button>
      <a :href="targetLocation"><i class="el-icon-more"></i></a>
    </el-row>
    <el-row>
      <el-col>
        <el-menu :default-active="activeIndex" router
          >
            <el-menu-item index="/main/index-page" class="text-size">
              <i class="el-icon-menu"></i>
              <span slot="title">首页</span>
            </el-menu-item>
            <el-menu-item index="/main/file-manager" class="text-size">
              <i class="el-icon-document"></i>
              <span slot="title">文件</span>
            </el-menu-item>
            <el-menu-item index="/main/inverted-index" class="text-size">
              <i class="el-icon-view"></i>
              <span slot="title">索引</span>
            </el-menu-item>
            <el-menu-item index="/main/setting" class="text-size">
              <i class="el-icon-setting"></i>
              <span slot="title">设置</span>
            </el-menu-item>
            <el-menu-item index="/main/help" class="text-size">
              <i class="el-icon-question"></i>
              <span slot="title">帮助</span>
            </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import types from '@/types/types'
export default {
  name: '',
  data () {
    return {
        activeIndex:this.$route.path,
        userName:'',
        targetLocation: this.axios.defaults.baseURL+'/main/setting'
    };
  },

  components: {},

  computed: {},

  mounted() {
    if(this.$store.state.user.userName)
      this.userName = this.$store.state.user.userName
  },

  methods: {
    logout(){
      this.axios.get('/logout')
      .then(res=>{
        this.$alert('注销成功！',{
                    confirmButtonText: '确定',
                    callback: action => {
                        
                    }
                })
        this.$router.push('/')
        this.$store.commit(types.OP_DELETE_TOKEN)
      }).catch(error=>{
        this.$alert('服务器错误！'+error,{
                    confirmButtonText: '确定',
                    callback: action => {
                        
                    }
                })
      })
    }
  }
}

</script>
<style scoped>
.text-size {
  font-size:110%;
}

.userInfo {
  display: flex;
  flex-flow: row wrap;
  justify-content: flex-start;
}
.userInfo > img {
  margin-left: 4em;
  width: 30px;
  height: 30px;
  border-radius: 50%;
}
.userInfo > span {
  align-self: center;
  text-align: start;
  flex: 2;
  font-weight: bold;
}
.userInfo > .el-button {
  flex: 1;
}
.userInfo > a {
  flex: 1;
  align-self: center;
}
</style>