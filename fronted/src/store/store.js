import Vuex from 'vuex'
import Vue from 'vue'
import types from '@/types/types'
Vue.use(Vuex)

var setCookie = (token) => { // cookie不能用.来获取属性，只能用=，因为cookie是string类型
  document.cookie = token
}

var getCookie = () => {
  return document.cookie
}

var store = new Vuex.Store({
  state: {
    authorization: '',
    user: {
      userName: '',
      potrait: ''
    },
    isLoaded: false,
    tempFile: {
      filename: '未选择文件',
      filetype: '无',
      filesize: '0KB',
      uploadtime: ''
    }
  },
  mutations: {
    [types.OP_GET_TOKEN] (state, playload) {
      state.authorization = playload.token
      state.user.userName = playload.userName
      setCookie(state.authorization)
    },
    [types.OP_DELETE_TOKEN] (state) {
      state.authorization = null
      state.user.userName = ''
      setCookie('')
    }
  }
})

export default store
export {store, getCookie}
