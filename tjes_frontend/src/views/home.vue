<template>
  <a-layout class="content">
    <a-layout>
      <a-layout-sider width="400" style="background: #eff2f5;">
        <div class="custom-card" style="box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);" 
          onmouseover="this.style.boxShadow='0 4px 8px rgba(0, 0, 0, 0.25)';" 
          onmouseout="this.style.boxShadow='0 2px 4px rgba(0, 0, 0, 0.1)';">
          <a-card title="用户登录">
            <div class="login">
              <a-input v-model="login_form_data.userName" placeholder="请输入你的用户名">
                <a-icon slot="prefix" type="user" />
                <a-tooltip slot="suffix" title="输入用户名">
                  <a-icon type="info-circle" style="color: rgba(0, 0, 0, 0.45);" />
                </a-tooltip>
              </a-input>
              <br />
              <br />
              <a-input-password v-model="login_form_data.passWord" placeholder="请输入你的密码" />
              <br />
              <br />
              <el-row :gutter="20">
                <el-col :span="12" :offset="0">
                  <a-input v-model="login_form_data.code" placeholder="请输入验证码" />
                </el-col>
                <el-col :span="12" :offset="0">
                  <img
                    @click="() => {
                    login_img_src += '?time=' + new Date().getTime()

                  }"
                    :src="login_img_src"
                    style="height: 32px; width: 100px; border: 1px solid;"
                  />
                </el-col>
              </el-row>
              <br />
              <el-row :gutter="20">
                <el-col :span="5" :offset="0">
                  <a-button type="primary" @click="home_login()">登录</a-button>
                </el-col>
                <el-col :span="5" :offset="1">
                  <a-button type="primary" @click="goto_register()">注册</a-button>
                </el-col>
                <el-col :span="12" :offset="1">
                  <a-button @click="goto_passwordfind()">找回密码</a-button>
                </el-col>
              </el-row>
            </div>
          </a-card>
        </div>
        <div class="custom-card" style="box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);" 
          onmouseover="this.style.boxShadow='0 4px 8px rgba(0, 0, 0, 0.25)';" 
          onmouseout="this.style.boxShadow='0 2px 4px rgba(0, 0, 0, 0.1)';">
          <a-card title="小区信息">
            <a-descriptions bordered>
              <!-- 字从左到右 -->
              <a-descriptions-item label="小区名称" :span="3">绿地新都会</a-descriptions-item>
              <a-descriptions-item label="小区地址" :span="3">上海市嘉定区</a-descriptions-item>
              <a-descriptions-item label="小区面积" :span="3">3000亩</a-descriptions-item>
              <a-descriptions-item label="小区户数" :span="3">10000户</a-descriptions-item>
              <a-descriptions-item label="小区人数" :span="3">30000人</a-descriptions-item>
              <a-descriptions-item label="小区负责人" :span="3">XXX</a-descriptions-item>
              <a-descriptions-item label="联系方式" :span="3">
                <a-badge status="processing" text="+86180xxxx0527" />
              </a-descriptions-item>
            </a-descriptions>
          </a-card>
        </div>
      </a-layout-sider>
      <a-layout-content style="overflow: hidden;">
        <div class="carousel" style="display: flex;">
          <a-card title="图片展示" style="box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); width: 800px; margin-right: 10px;" 
          onmouseover="this.style.boxShadow='0 4px 8px rgba(0, 0, 0, 0.25)';" 
          onmouseout="this.style.boxShadow='0 2px 4px rgba(0, 0, 0, 0.1)';">
              <el-carousel indicator-position="outside" style="height: 350px; overflow: hidden;">
                <el-carousel-item
                  v-for="item in carousel_img_list"
                  :key="item"
                  style="height: 350px;"
                >
                  <img :src="item" style="width: 100%; height: 350px;" />
                </el-carousel-item>
              </el-carousel>
          </a-card >
          <a-card title="聊天Bot" style=" box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);" onmouseover="this.style.boxShadow='0 8px 16px rgba(0, 0, 0, 0.25)';" onmouseout="this.style.boxShadow='0 4px 8px rgba(0, 0, 0, 0.1)';">
            <chat-api />
          </a-card>
        </div>
        <div class="notice">
          <a-card
          style="margin-bottom: 8px; margin-right: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);"
            onmouseover="this.style.boxShadow='0 8px 16px rgba(0, 0, 0, 0.25)';" 
            onmouseout="this.style.boxShadow='0 4px 8px rgba(0, 0, 0, 0.1)';"
            :tab-list="tabListNoTitle"
            :active-tab-key="noTitleKey"
            @tabChange="key => onTabChange(key, 'noTitleKey')"
          >
            <p v-if="noTitleKey === 'em_notice'">
              <notice-list />
            </p>
            <p v-else-if="noTitleKey === 'rq_facilities'">
              <rq-facilities-list />
            </p>
            <p v-else-if="noTitleKey === 'estate_user'">
              <estate-user-list />
            </p>
          </a-card>
        </div>
      </a-layout-content>
    </a-layout>
    <a-layout-footer style="display: flex; justify-content: center;">Copyright © 2023 智慧小区管理系统</a-layout-footer>
  </a-layout>
</template>

<script>
import { login, isAdmin } from '@/api/requests/rq-manage.js'
import notice_list from '@/views/home/notice_list.vue'
import rq_facilities_list from '@/views/home/rq_facilities_list.vue'
import estate_user_list from '@/views/home/estate_user_list.vue'
import chat_api from '@/views/home/chat_api.vue'

export default {
  name: 'home',
  components: {
    'notice-list': notice_list,
    'rq-facilities-list': rq_facilities_list,
    'estate-user-list': estate_user_list,
    'chat-api': chat_api,
  },
  data () {
    return {
      login_img_src: 'http://localhost:8082/login/code',
      login_form_data: {},
      carousel_img_list: ['http://localhost:8082/images/1.jpg', 'http://localhost:8082/images/2.jpg', 'http://localhost:8082/images/3.jpg', 'http://localhost:8082/images/4.jpg'],
      tabListNoTitle: [
        {
          key: 'em_notice',
          tab: '物业公告',
        },
        {
          key: 'rq_facilities',
          tab: '小区设施',
        },
        {
          key: 'estate_user',
          tab: '物业人员',
        },
      ],
      key: 'em_notice',
      noTitleKey: 'rq_facilities',
    }
  },
  created () {
    if (localStorage.getItem("isLogin")) {

      this.$store.dispatch('user/login', this.form).then(res => {
        this.$router.push('/')
      })

    }
  },
  methods: {
    goto_register(){
      this.$router.push('/register')
    },
    goto_passwordfind(){
      this.$router.push('/passwordfind')
    },
    home_login () {
      login(this.login_form_data).then(res => {
        if (res.code == 200) {
          this.$success({
            title: '登录成功',
            content: (
              <div>
                <p>接下来即将跳转到后台界面...</p>
              </div>
            ),
          });
          localStorage.setItem("userName", res.data.userName)
          this.$store.dispatch('user/login', this.form).then(res => {
              setTimeout(() => {
            window.location.href = '/'
          }, 1000)

          })
        } else {
          this.$error({
            title: '登录失败',
            content: res.msg,
          });

        }
      })
    },
    onTabChange (key, type) {
      console.log(key, type);
      this[type] = key;
    },
  },
}
</script>

<style lang="scss" scoped>
.custom-card {
    background-color: #fff;
    transition: all 0.3s;
}
.content {
    padding: 0 30px;
}
.ant-card {
    margin-top: 20px;
    .login {
        padding: 20px;
    }
}
.ant-layout-content {
    margin-left: 10px;
}
.a-input-password {
    font-size: 14;
}
</style>