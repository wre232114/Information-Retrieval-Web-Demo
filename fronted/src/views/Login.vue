<!--  -->
<template>
  <div>
      <page-login-header />
      <section class="page-head">
        <el-row>
            <el-col :span="2" :offset="8">
                <p>用户名：</p>
            </el-col>
            <el-col :span="4" >
                <el-input
                    placeholder="请输入用户名"
                    v-model="userName"
                    clearable>
                </el-input>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="2" :offset="8">
                <p>密码：</p>
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
      </section>
      <el-row>
          <el-col :span="2" :offset="10">
              <el-button type="primary" @click="handleLogin">登陆</el-button>
          </el-col>
          <el-col :span="1">
              <el-button type="primary" @click="handleRegister">注册</el-button>
          </el-col>
      </el-row>
  </div>
</template>

<script>
import PageLoginHeader from '@/components/PageLoginHeader'
import types from '@/types/types'
import axios from '@/router/http'
export default {
  name: '',
  data () {
    return {
        userName: '',
        password: ''
    };
  },

  components: {
      PageLoginHeader
  },

  computed: {},

  mounted: {},

  methods: {
      handleLogin(){
          this.axios.post('/login',{
                  userName: this.userName,
                  psword: this.password
          }).then(res=>{//请求成功，将token放到全局的Headers中
            if(res.data.status == types.LOGIN_SUCCESS){
                this.$store.commit(types.OP_GET_TOKEN,res.data.content)
                this.$router.push('/main/index-page'); //只有用name才可以用params传参数
            }else if(res.data.status == types.LOGIN_FAIL){
                this.$alert('用户名或者密码不正确',{
                    confirmButtonText: '确定',
                    callback: action => {

                    }
                })
            }
          }).catch(error=>{
              this.$alert('连接服务器失败! '+error,{
                    confirmButtonText: '确定',
                    callback: action => {
                        
                    }
                })
          })
      },
      handleRegister(){
          this.$router.push('/register')
      }
  }
}

</script>
<style scoped>
</style>