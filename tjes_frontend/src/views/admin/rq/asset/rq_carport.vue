<template>
  <page-main>
    <a-card :loading="loading" title="小区车辆管理">
      <div class="head">
        <span>搜索类型</span>
        <a-select
          size="large"
          default-value="单位名称"
          v-model="car_query_type"
          style="width: 200px;">
        >
          <a-select-option value="name">用户姓名</a-select-option>
          <a-select-option value="carname">车辆名称</a-select-option>
          <a-select-option value="paking">停车位</a-select-option>
        </a-select>
        <a-input-search
          v-model="car_query_text"
          placeholder="输入要搜索的文本"
          :enter-button="car_query_buttonTitle"
          size="large" style="width: 300px;"
          @search="car_query_buttonTitle == '搜索' ? Query_carDataList() : Get_carDataList()"
        />
        <a-button
          type="primary"
          style="height: 38px;"
          @click="car_save_modalVisible = true"
        >添加车辆</a-button>
        <a-button
          type="danger"
          v-if="table_selectedRowKeys.length > 0"
          style="height: 38px; margin-left: 10px;"
          @click="Del_batchData"
        >删除被选择的「车辆」</a-button>
      </div>
      <a-table
        :data-source="car_data_list"
        :row-selection="{ selectedRowKeys: table_selectedRowKeys, onChange: Table_selectChange }"
      >
        <a-table-column key="name" title="车辆名称" data-index="name" />
        <a-table-column key="user" title="所属用户" data-index="user" />
        <a-table-column key="parking" title="停车位" data-index="parking"/>
        <a-table-column key="status" title="是否在位" data-index="status">
          <template slot-scope="text, record">
            <a-switch v-model="record.status == 0" @change="Change_carStatus(record)" />
          </template>
        </a-table-column>
        <a-table-column key="date" title="最近在位时间" data-index="date">
          <!-- rTime -->
          <template slot-scope="text, record">
            <span>{{rTime(record.date)}}</span>
          </template>
        </a-table-column>
        <a-table-column key="action" title="操作">
          <template slot-scope="text, record">
            <a-button-group>
              <a-button type="primary" @click="Edit_carData(record)">编辑</a-button>
              <a-button type="danger" @click="Del_carData(record.id)">删除</a-button>
            </a-button-group>
          </template>
        </a-table-column>
      </a-table>
    </a-card>
    <!-- 新增或保存设施提示框 -->
    <a-modal
      v-model="car_save_modalVisible"
      :title="car_save_title"
      ok-text="确认"
      cancel-text="取消"
      :maskClosable="false"
      :destroyOnClose="false"
      @ok="Save_carData"
    >
      <a-form-model :model="car_form_data" :label-col="labelCol" :wrapper-col="wrapperCol">
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="车辆名称">
              <a-input v-model="car_form_data.name" />
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="所属用户">
              <a-input v-model="car_form_data.user" />
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="停车位">
              <a-input v-model="car_form_data.parking" />
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="是否在位">
              <a-input v-model="car_form_data.status" />
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="最近在位时间" style="width: 300px;">
              <a-date-picker
                :default-value="car_form_data.date"
                @change="Form_date_changeHandler"
                placeholder="选择时间"
              />
            </a-form-model-item>
          </el-col>
        </el-row>
      </a-form-model>
    </a-modal>
  </page-main>
</template>

<script>
import { getCar, deletecar, savecar } from '@/api/requests/rq-manage.js'
import { Success, Warning } from '@/util/message.js'
import { rTime } from '@/util/time.js'
export default {
  data () {
    return {
      rTime,
      loading: false,
      labelCol: { span: 8 },
      wrapperCol: { span: 14 },
      table_selectedRowKeys: [],
      car_query_type: 'name',
      car_query_buttonTitle: '搜索',
      car_query_text: '',
      car_save_title: '新增楼宇',
      car_save_modalVisible: false,
      car_form_data: {},
      car_data_list: [],
    }
  },
  created () {
    this.Get_carDataList()
  },
  watch: {
    car_save_modalVisible (val) {
      if (!val) {
        this.car_form_data = {}
      }
    }
  },
  methods: {
    Get_carDataList () {
      getCar().then(res => {
        this.car_query_buttonTitle = '搜索'
        this.car_data_list = res.data
        this.car_save_title = '新增车辆'
      })
    },
    Query_carDataList () {
      let text = this.car_query_text
      let temp_list = []
      this.car_data_list.forEach(item => {
        let flag = false
        if (typeof (item[this.car_query_type]) == 'number') {
          if (item[this.car_query_type] <= parseInt(text)) {
            flag = true
          }
        } else {
          if (item[this.car_query_type].indexOf(text) != -1) {
            flag = true
          }
        }
        if (flag) {
          temp_list.push(item)
        }

      })
      this.car_query_buttonTitle = '返回'
      this.car_data_list = temp_list
    },
    Edit_carData (form) {
      this.car_save_title = '编辑楼宇'
      this.car_form_data = JSON.parse(JSON.stringify(form))
      this.car_save_modalVisible = true
    },
    Del_carData (id) {
      deletecar(id).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.Get_carDataList()
      })
    },
    Del_batchData () {
      this.table_selectedRowKeys.forEach(i => {
        this.Del_carData(this.car_data_list[i].id)
      })
      this.table_selectedRowKeys = []
    },
    Save_carData () {
      savecar(this.car_form_data).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.car_save_modalVisible = false
        this.Get_carDataList()
      })


    },
    Table_selectChange (selectedRowKeys) {
      this.table_selectedRowKeys = selectedRowKeys;
    },
    Form_date_changeHandler (date) {
      this.car_form_data.date = date._d
    },
    Change_carStatus(r) {
      r.status = r.status == 0 ? 1 : 0
      this.car_form_data = r
      this.Save_carData()
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