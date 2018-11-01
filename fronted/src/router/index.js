import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/Login'
import IndexPage from '@/views/IndexPage'
import MainPage from '@/views/MainPage'
import Register from '@/views/Register'
import axios from '@/router/http'
import store from '@/store/store'
import types from '@/types/types'

// 导入ElementUI中的提示消息
import {MessageBox} from 'element-ui'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/main',
      name: 'Main',
      meta: {
        requireAuth: true
      },
      component: MainPage,
      children: [
        {
          path: 'index-page',
          name: 'IndexPage',
          meta: {
            requireAuth: true
          },
          component: IndexPage
        },
        {
          path: 'file-manager',
          name: 'FileManager',
          meta: {
            requireAuth: true
          },
          component: resolve => require(['@/views/FileManager'], resolve)
        },
        {
          path: 'inverted-index',
          name: 'InvertedIndex',
          meta: {
            requireAuth: true
          },
          component: resolve => require(['@/views/InvertedIndex'], resolve)
        },
        {
          path: 'setting',
          name: 'Setting',
          meta: {
            requireAuth: true
          },
          component: resolve => require(['@/views/Setting'], resolve)
        },
        {
          path: 'help',
          name: 'Help',
          meta: {
            requireAuth: true
          },
          component: resolve => require(['@/views/Help'], resolve)
        }
      ]
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    }
    // 使用懒加载
    // {
    //   path: '/file-manager',
    //   name: 'FileManager',
    //   meta: {
    //     requireAuth: true
    //   },
    //   component: resolve => require(['@/views/FileManager'], resolve)
    // }
  ]
})
// 注册全局前置守卫，用于前端权限控制
router.beforeEach((to, from, next) => {
  if (to.matched.some(r => r.meta.requireAuth)) { // some是数组方法，用于判断数组中是否包含指定元素,参数是方法
    checkToken(next)
  } else {
    next()
  }
})

let checkToken = (next) => {
  axios.get('/checkToken') // 不能用this.axios，因为不是在vue对象中
    .then(res => { // 将返回 的结果放到store中
      store.commit(types.OP_GET_TOKEN, res.data.content)
      next()
    })
    .catch(e => {
      MessageBox.alert('登陆失效，请重新登陆! ', {
        confirmButtonText: '确定',
        callback: action => { // 回调函数

        }
      })
      next({path: '/'})
    })
}
export default router
