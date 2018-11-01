<!--  -->
<template>
  <div>
      <page-login-header />
      <el-card class="page-head">
          <div slot = "header" style="text-align:center;font-weight:bold;font-size:18px;">
              请输入注册信息
          </div>
          <el-row>
              <el-col :span="1" :offset="9">
                  <p>用户名:</p>
              </el-col>
              <el-col :span="4">
                  <el-input
                    placeholder="请输入用户名"
                    v-model="username"
                    clearable>
                  </el-input>
              </el-col>
          </el-row>
          <el-row>
              <el-col :span="1" :offset="9">
                  <p>密码:</p>
              </el-col>
              <el-col :span="4">
                  <el-input
                    type="password"
                    placeholder="请输入密码"
                    v-model="password"
                    clearable>
                  </el-input>
              </el-col>
          </el-row>
          <el-row>
              <el-col :span="1" :offset="9">
                  <p>确认密码:</p>
              </el-col>
              <el-col :span="4">
                  <el-input
                    type="password"
                    placeholder="请再次输入密码"
                    v-model="confirm_password"
                    @blur="isPasswordSame"
                    clearable>
                  </el-input>
              </el-col>
          </el-row>
          <el-row>
              <el-col :span="1" :offset="9">
                  <p>邮箱:</p>
              </el-col>
              <el-col :span="4">
                  <el-input
                    placeholder="请输入邮箱"
                    v-model="email"
                    clearable>
                  </el-input>
              </el-col>
          </el-row>
          <el-row>
              <el-col :span="2" :offset="11">
                  <el-button type="primary" @click="submitForm">提交</el-button>
              </el-col>
          </el-row>
      </el-card>
  </div>
</template>

<script>
import PageLoginHeader from '@/components/PageLoginHeader'
import types from '@/types/types'
export default {
  name: '',
  data () {
    return {
        username: '',
        password: '',
        confirm_password: '',
        email: '',
    };
  },

  components: {
      PageLoginHeader
  },

  computed: {},

  mounted: {},

  methods: {
      //提交信息表单
      submitForm(){
          this.axios.post('/register',{
                  userName: this.username,
                  psword: this.password,
                  email: this.email
          }).then(res=>{
              if(res.data.status == types.REGISTER_SUCCESS){
                  alert('注册成功，请登陆！')
                  this.$router.push('/')
              }
          }).catch(error=>{
              alert(error)
          })
      },
      //判断两次输入的密码是否相同
      isPasswordSame(){
        
      }
  }
}

</script>
<style scoped>
</style>