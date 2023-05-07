<template>
  <page-main>
    <a-card :loading="loading" title="房产管理">
      <a-table
        :data-source="car_data_list"
        :row-selection="{ selectedRowKeys: table_selectedRowKeys, onChange: Table_selectChange }"
      >
        <a-table-column key="name" title="车辆名称" data-index="name" />
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
      </a-table>
    </a-card>
  </page-main>
</template>

<script>
import { getCarByUserId } from '@/api/requests/rq-manage.js'
import { rTime } from '@/util/time.js'

export default {
  data () {
    return {
      rTime,
      loading: false,
      table_selectedRowKeys: [],
      car_data_list: [],
    }
  },
  created () {
    this.Get_CarList()
  },
  methods: {
    Get_CarList () {
      getCarByUserId().then(res => {
        this.car_data_list = res.data
      })
    },
    Table_selectChange (selectedRowKeys) {
      this.table_selectedRowKeys = selectedRowKeys;
    },
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
.ant-modal-content {
    width: 24vw;
}
</style>