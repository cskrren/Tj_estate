<template>
  <page-main>
    <a-card :loading="loading" title="小区物业人员管理">
      <div class="head">
        <span>搜索类型</span>
        <a-select size="large" default-value="" v-model="estateUser_query_type" style="width: 200px;">
          <a-select-option value="userName">用户名</a-select-option>
          <a-select-option value="fullName">真实姓名</a-select-option>
          <a-select-option value="phone">联系电话</a-select-option>
          <a-select-option value="status">用户状态</a-select-option>
        </a-select>
        <a-input-search v-model="estateUser_query_text" placeholder="输入要搜索的文本"
          :enter-button="estateUser_query_buttonTitle" size="large"
          @search="estateUser_query_buttonTitle == '搜索' ? Query_estateUserDataList() : Get_estateUserDataList()" />
        <a-button type="danger" v-if="table_selectedRowKeys.length > 0" style="height: 40px; margin-left: 10px;"
          @click="Del_batchData">删除被选择的「账号」</a-button>
      </div>
      <a-table :data-source="estateUser_data_list"
        :row-selection="{ selectedRowKeys: table_selectedRowKeys, onChange: Table_selectChange }">
        <a-table-column key="userName" title="用户名" data-index="userName" />
        <a-table-column key="fullName" title="真实姓名" data-index="fullName" />
        <a-table-column key="phone" title="联系电话" data-index="phone" />
        <a-table-column key="status" title="是否启用" data-index="status">
          <template slot-scope="text, record">
            <a-switch v-model="record.status == 0" @change="Change_estateUserStatus(record)" />
          </template>
        </a-table-column>
        <a-table-column key="action" title="操作">
          <template slot-scope="text, record">
            <a-button-group>
              <!-- <a-button type="primary" @click="Edit_estateUserData(record)">编辑</a-button> -->
              <a-button type="danger" @click="Del_estateUserData(record.id)">删除账号</a-button>
            </a-button-group>
          </template>
        </a-table-column>
      </a-table>
    </a-card>
  </page-main>
</template>

<script>
import { getUsers, saveUser, deleteUser } from '@/api/requests/rq-manage.js'
import { Success, Warning } from '@/util/message.js'
import { rTime } from '@/util/time.js'

export default {
  data() {
    return {
      loading: false,
      labelCol: { span: 8 },
      wrapperCol: { span: 14 },
      table_selectedRowKeys: [],
      estateUser_query_type: 'userName',
      estateUser_query_buttonTitle: '搜索',
      estateUser_query_text: '',
      estateUser_save_modalVisible: false,
      estateUser_form_data: {},
      estateUser_data_list: [],
    }
  },
  created() {
    this.Get_estateUserDataList()
  },
  watch: {
    estateUser_save_modalVisible(val) {
      if (!val) {
        this.estateUser_form_data = {}
      }
    }
  },
  methods: {
    Get_estateUserDataList() {
      getUsers().then(res => {
        this.estateUser_query_buttonTitle = '搜索'
        this.estateUser_data_list = res.data
      })
    },
    Query_estateUserDataList() {
      let text = this.estateUser_query_text
      let temp_list = []
      this.estateUser_data_list.forEach(item => {
        if (item[this.estateUser_query_type].indexOf(text) != -1) {
          temp_list.push(item)
        }
      })
      this.estateUser_query_buttonTitle = '返回'
      this.estateUser_data_list = temp_list
    },
    Edit_estateUserData(form) {
      this.estateUser_save_title = '编辑物业人员'
      this.estateUser_form_data = JSON.parse(JSON.stringify(form))
      this.estateUser_save_modalVisible = true
    },
    Del_estateUserData(id) {
      deleteEstateUser(id).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.Get_estateUserDataList()
      })
    },
    Del_batchData() {
      this.table_selectedRowKeys.forEach(i => {
        this.Del_estateUserData(this.estateUser_data_list[i].id)
      })
      this.table_selectedRowKeys = []
    },
    Save_estateUserData() {
      const form = JSON.parse(JSON.stringify(this.estateUser_form_data));
      saveUser(form).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.estateUser_save_modalVisible = false
        this.Get_estateUserDataList()
      })
    },
    Table_selectChange(selectedRowKeys) {
      this.table_selectedRowKeys = selectedRowKeys;
    },
    Change_estateUserStatus(r) {
      r.status = r.status == 0 ? 1 : 0
      this.estateUser_form_data = r
      this.Save_estateUserData()
    }
  },
}
</script>
    
<style lang="scss" scoped>
.head {
    display: flex;
    justify-content: flex-start;
    margin-bottom: 10px;
    span {
        line-height: 40px;
        margin-right: 10px;
    }
    .ant-input-search {
        width: 30%;
        margin-left: 10px;
    }
}
</style>