<template>
  <div>
    <page-header title="欢迎使用「智能小区管理系统」">
    </page-header>
    <page-main>
      <el-row :gutter="24" v-show="isAdmin">
        <el-col :span="6" :offset="0">
          <Card
            title="/rq-manager/info"
            num="小区管理"
            tip="主要管理小区基本内容"
            type="aaa"
            icon="el-icon-office-building"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/rq-manager/facilities"
            num="公共设施"
            tip="小区内所有公共设施"
            type="orange"
            icon="el-icon-office-building"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/rq-manager/notices"
            num="小区公告"
            tip="管理员发布的公告"
            type="blue"
            icon="el-icon-chat-square"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/building-manager/building"
            num="楼宇管理"
            tip="小区内所有楼宇详细记录"
            type="violet"
            icon="el-icon-school"
          />
        </el-col>
      </el-row>
      <el-row :gutter="24" v-show="isAdmin">
        <el-col :span="6" :offset="0">
          <Card
            title="/building-manager/room"
            num="房间管理"
            tip="楼盘内房间管理"
            type="aaa"
            icon="el-icon-house"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/building-manager/car"
            num="车位管理"
            tip="小区内车位管理"
            type="orange"
            icon="el-icon-house"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/guarantee-manager/repair"
            num="报修管理"
            tip="及时处理业主的报修"
            type="blue"
            icon="el-icon-attract"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/guarantee-manager/complaint"
            num="投诉管理"
            tip="查看业主反映的问题/建议"
            type="violet"
            icon="el-icon-message"
          />
        </el-col>
      </el-row>
      <el-row :gutter="24" v-show="isAdmin">
        <el-col :span="6" :offset="0">
          <Card 
          title = "/charge-manager/type"
          num="费用管理" 
          tip="小区内业主的费用管理" 
          type="aaa"
          icon="el-icon-coin" />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card 
            title = "/user-manager/estate_user" 
            num="账号管理" 
            tip="系统用户账号的管理" 
            type="orange"
            icon="el-icon-user" 
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card 
            title = "user-manager/household"
            num="住户管理" 
            tip="小区内业主居住管理" 
            type="blue"
            icon="el-icon-user"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="help-manager/chatroom"
            num="多人聊天"
            tip="智慧小区聊天室"
            type="violet"
            icon="el-icon-date"
          />
        </el-col>
      </el-row>
      <el-row :gutter="24" v-show="!isAdmin">
        <el-col :span="6" :offset="0">
          <Card 
            title="/user/base/info" 
            num="个人信息" 
            tip="修改自己的信息" 
            type="aaa" 
            icon="el-icon-user" />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/user/base/reset_pwd"
            num="密码修改"
            tip="修改自己的密码"
            type="orange"
            icon="el-icon-lock"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/user/asset/room"
            num="房间管理"
            tip="用户的房间资产"
            type="blue"
            icon="el-icon-house"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/user/asset/car"
            num="车辆管理"
            tip="用户的车辆资产"
            type="violet"
            icon="el-icon-house"
          />
        </el-col>
      </el-row>
      <el-row :gutter="24" v-show="!isAdmin">
        <el-col :span="6" :offset="0">
          <Card
            title="/user/pay_record"
            num="物业收费"
            tip="缴纳本月费用"
            type="aaa"
            icon="el-icon-collection-tag"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/user/repair/add"
            num="申请报修"
            tip="设施损坏在此报修"
            type="orange"
            icon="el-icon-tickets"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/user/complaint/add"
            num="发起投诉"
            tip="对物业服务进行投诉"
            type="blue"
            icon="el-icon-phone-outline"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="help-manager/chatroom"
            num="多人聊天"
            tip="智慧小区聊天室"
            type="violet"
            icon="el-icon-date"
          />
        </el-col>
      </el-row>
    </page-main>
  </div>
</template>

<script>
import card from '@/views/component_extend_example/card.vue'
export default {
  data () {
    return {
      isAdmin: false
    }
  },
  components: {
    'Card': card,
  },
  created () {
    let isAdmin = localStorage.getItem('isAdmin')
    this.isAdmin = isAdmin == 'true' ? true : false

  },
  methods: {
    open (url) {
      window.open(url, 'top')
    },
    onIconClick (val) {
      this.$message({
        message: `你点击了：${val}`,
        type: 'info'
      })
    },

  }
}
</script>

<style lang="scss" scoped>
.el-row {
    margin: 10px;
}
</style>
