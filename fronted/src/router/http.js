// 该文件用户axios的全局配置
// import {MessageBox} from 'element-ui'
import axios from 'axios'
import {store, getCookie} from '@/store/store'
// axios.defaults.baseURL = 'http://localhost:8080' // 基地址，本地调试
axios.defaults.baseURL = 'http://118.25.74.217:8080' // 服务器地址
axios.defaults.timeout = 5000 // 设置超时时间为5秒

// 全局请求前置拦截器
axios.interceptors.request.use(config => {
  if (store.state.authorization) {
    config.headers.authorization = store.state.authorization
  } else if (getCookie()) {
    config.headers.authorization = getCookie()
  }
  return config
},
error => {
  return Promise.reject(error)
})
// 全局response拦截器，根据服务器返回的错误代码进行处理
// axios.interceptors.response.use(res => {
//   // if (res.status === 401) {
//   //   MessageBox.alert('登陆认证失效！请重新登陆！', '失效', {
//   //     confirmButtonText: '确定'
//   //   })
//   // }
//   // 前置路由守卫已经进行路由权限控制
//   return res
// }, error => {
//   MessageBox.alert('发生错误！' + error)
// })
export default axios
