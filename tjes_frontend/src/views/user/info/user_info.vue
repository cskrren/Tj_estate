<template>
  <page-main>
    <a-card title="个人信息">
      <upload_image/>
      <a-form-model
        ref="current_form"
        :rules="rules"
        :model="current_form"
        style="width: 100%; display: flex; justify-content: center; align-items: center; flex-direction: column; font-size: 18px;"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-form-model-item>
          <span>姓名：</span>
          <template v-if="!editMode">{{ current_form.userName }}</template>
          <template v-else>
            <a-input style="width: 200px;" v-model="current_form.userName" placeholder="您的姓名" />
          </template>
        </a-form-model-item>

        <a-form-model-item>
          <span>性别：</span>
          <template v-if="!editMode">{{ current_form.userGender }}</template>
          <template v-else>
            <a-input style="width: 200px;" v-model="current_form.userGender" placeholder="您的性别" />
          </template>
        </a-form-model-item>

        <a-form-model-item>
          <span>年龄：</span>
          <template v-if="!editMode">{{ current_form.userAge }}</template>
          <template v-else>
            <a-input style="width: 200px;" v-model="current_form.userAge" placeholder="您的年龄" />
          </template>
        </a-form-model-item>

        <a-form-model-item>
          <span :style="{ display: 'inline-block', width: '50px', verticalAlign: 'top' }">电话：</span>
          <template v-if="!editMode">{{ current_form.userPhone }}</template>
          <template v-else>
            <a-input style="width: 200px;" v-model="current_form.userPhone" placeholder="您的电话" />
          </template>
        </a-form-model-item>
        <a-form-model-item>
          <span :style="{ display: 'inline-block', width: '50px', verticalAlign: 'top' }">邮箱：</span>
          <template v-if="!editMode">{{ current_form.userEmail }}</template>
          <template v-else>
            <a-input style="width: 200px;" v-model="current_form.userEmail" placeholder="您的邮箱" />
          </template>
        </a-form-model-item>
        <a-form-model-item>
          <span :style="{ display: 'inline-block', width: '50px', verticalAlign: 'top' }">单位：</span>
          <template v-if="!editMode">{{ current_form.userWorkPlace }}</template>
          <template v-else>
            <a-input style="width: 200px;" v-model="current_form.userWorkPlace" placeholder="您的单位" />
          </template>
        </a-form-model-item>
        <a-form-model-item>
          <a-button v-if="!editMode" type="primary" @click="editMode = true">编辑</a-button>
          <a-button v-if="editMode" type="primary" @click="save_userInfo">保存</a-button>
        </a-form-model-item>
      </a-form-model>
    </a-card>
  </page-main>
</template>

<script>
import upload_image from '@/views/user/info/upload_image.vue'
import { getUserInfoList, saveUserInfo } from '@/api/requests/rq-manage.js'

export default {
  components: { upload_image },
  name: 'user_info',
  data () {
    return {
      current_form: {},
      editMode: false, // 新增 editMode 状态
    }
  },
  created () {
    this.Get_UserInfoList()
  },
  methods: {
    Get_UserInfoList () {
      // 获取用户信息的逻辑
      getUserInfoList().then(res => {
        this.current_form = res.data[0]
        console.log(this.current_form)
      })
    },
    save_userInfo () {
      console.log(this.current_form)
      saveUserInfo(this.current_form).then(res => {
        if(res.code === 200) {
          this.$message.success('保存成功')
        } else {
          this.$message.error('保存失败')
        }
        this.editMode = false
        this.Get_UserInfoList()
      })
    } 
  },
}
</script>

<style>
.user-info {
    background-color: #f0f2f5;
    padding: 20px;
}
.user-info .card {
    width: 80%;
    margin: 0 auto;
}
.user-info .card .ant-card-head-title {
    font-size: 20px;
    color: #2d3a4b;
}
.user-info .card .ant-card-body {
    padding: 20px;
}
.user-info .card .ant-form-item-label {
    width: 100px;
    text-align: right;
}
.user-info .card .ant-form-item-control {
    flex: auto;
}
.user-info .card .ant-form-item-control-input input {
    background-color: #fff;
    border: none;
    border-radius: 4px;
    padding: 8px 12px;
    font-size: 20px;
    line-height: 1.5715;
    color: #666;
    transition: border-color 0.3s ease;
    box-shadow: 0 2px 0 rgba(0, 0, 0, 0.015);
    border-color: #d9d9d9;
}
.user-info .card .ant-form-item-control-input input:focus {
    border-color: #1890ff;
    box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
    outline: none;
}
.user-info .card .ant-form-item-control-input input::placeholder {
    color: #cfcfcf;
}
.user-info .card .ant-btn-primary {
    margin-right: 10px;
}
.user-info .card .ant-btn-default {
    background-color: #fff;
    color: #666;
    border-color: #d9d9d9;
}
.user-info .card .ant-btn-default:hover {
    color: #2d3a4b;
    border-color: #2d3a4b;
}

</style>