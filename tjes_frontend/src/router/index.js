import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store/index'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css' // progress bar style

Vue.use(VueRouter)

import Layout from '@/layout'
import EmptyLayout from '@/layout/empty'

const constantRoutes = [
  {
    path: '/home',
    name: 'home',
    component: () => import('@/views/home'),
    meta: {
      title: '主页'
    }
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/home/register'),
    meta: {
      title: '注册'
    }
  },
  {
    path: '/passwordfind',
    name: 'passwordfind',
    component: () => import('@/views/home/passwordfind'),
    meta: {
      title: '找回密码'
    }
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'dashboard',
        component: () => import('@/views/index'),
        meta: {
          title: store.state.settings.dashboardTitle
        }
      },
      {
        path: 'reload',
        name: 'reload',
        component: () => import('@/views/reload')
      }
    ]
  }
]


import Admin from './modules/admin'
import User from './modules/user'

// 当 children 不为空的主导航只有一项时，则隐藏
let asyncRoutes = [
  {
    meta: {
      title: '管理员后台',
      auth: ['admin']
    },
    children: [
      ...Admin
    ]
  },
  {
    meta: {
      title: '用户后台',
    },
    children: [
      ...User
    ]
  },
]

const lastRoute = [{
  path: '*',
  component: () => import('@/views/404'),
  meta: {
    title: '404',
    sidebar: false
  }
}]

const router = new VueRouter({
  routes: constantRoutes
})

// 解决路由在 push/replace 了相同地址报错的问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
const originalReplace = VueRouter.prototype.replace
VueRouter.prototype.replace = function replace (location) {
  return originalReplace.call(this, location).catch(err => err)
}

router.beforeEach(async (to, from, next) => {
  store.state.settings.enableProgress && NProgress.start()
  // 已经登录，但还没根据权限动态挂载路由
  if (store.getters['user/isLogin'] && !store.state.menu.isGenerate) {
    /**
     * 重置 matcher
     * https://blog.csdn.net/baidu_28647571/article/details/101711682
     */
    router.matcher = new VueRouter({
      routes: constantRoutes
    }).matcher
    const accessRoutes = await store.dispatch('menu/generateRoutes', {
      asyncRoutes,
      currentPath: to.path
    })
    accessRoutes.push(...lastRoute)
    console.log(accessRoutes)
    accessRoutes.forEach(route => {
      router.addRoute(route)
    })
    next({ ...to, replace: true })
  }
  if (store.state.menu.isGenerate) {
    store.commit('menu/setHeaderActived', to.path)
  }
  to.meta.title && store.commit('settings/setTitle', to.meta.title)
  if (store.getters['user/isLogin']) {
    if (to.name) {
      if (to.matched.length !== 0) {
        // 如果已登录状态下，进入登录页会强制跳转到控制台页面
        if (to.name == 'login') {
          next({
            name: 'dashboard',
            replace: true
          })
        } else if (!store.state.settings.enableDashboard && to.name == 'dashboard') {
          // 如果未开启控制台页面，则默认进入侧边栏导航第一个模块
          next({
            name: store.getters['menu/sidebarRoutes'][0].name,
            replace: true
          })
        }
      } else {
        // 如果是通过 name 跳转，并且 name 对应的路由没有权限时，需要做这步处理，手动指向到 404 页面
        next({
          path: '/404'
        })
      }
    }
  } else {
    // 未登录状态下，如果访问的页面需要权限，则跳转到登录页
    if(to.name == 'register' || to.name == 'passwordfind'){
      next()
    } else if (to.name != 'home') {
      next({
        name: 'home',
        query: {
          redirect: to.fullPath
        }
      })
    }
  }
  // 百度统计代码
  if (process.env.VUE_APP_TYPE == 'example') {
    if (window._hmt) {
      window._hmt.push(['_trackPageview', location.pathname + '#' + to.fullPath])
    }
  }
  next()
})

router.afterEach(() => {
  store.state.settings.enableProgress && NProgress.done()
})

export default router
