import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'

import api from './api'
Vue.prototype.$api = api

import dayjs from 'dayjs'
Vue.prototype.$dayjs = dayjs

import auth from './util/auth'
Vue.use(auth)

import cookies from 'vue-cookies'
Vue.use(cookies)

import VueMeta from 'vue-meta'
Vue.use(VueMeta)

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)

import Chat from 'jwchat';
/* 在 0.2.041 之前的版本需要引入 css */
// import 'jwchat/lib/JwChat.css';
Vue.use(Chat)


import AntDesignVue from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css';
Vue.use(AntDesignVue)

import axios from 'axios';
axios.defaults.baseURL = 'https://api.chatgptapi.com';
axios.defaults.headers.common['Authorization'] = 'Bearer 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';

import { VueJsonp } from 'vue-jsonp'
Vue.use(VueJsonp)

import hotkeys from 'hotkeys-js'
Vue.prototype.$hotkeys = hotkeys

// 全局组件自动注册
import './components/autoRegister'

// 自动加载 svg 图标
const req = require.context('./assets/icons', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys().map(requireContext)
requireAll(req)

import './assets/styles/reset.scss'

// import './mock'

Vue.config.productionTip = false

Vue.prototype.$eventBus = new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
