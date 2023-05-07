<template>
  <page-main>
    <a-card :loading="loading" title="房产管理">
      <a-table
        :data-source="room_data_list"
        :row-selection="{ selectedRowKeys: table_selectedRowKeys, onChange: Table_selectChange }"
      >
        <a-table-column key="id" title="房间编号" data-index="id" />
        <a-table-column key="buildingName" title="楼宇名称" data-index="buildingName" />
        <a-table-column key="unitName" title="单元名称" data-index="unitName" />
        <a-table-column key="direction" title="朝向" data-index="direction" />
        <a-table-column key="purpose" title="用途" data-index="purpose" />
        <a-table-column key="specifications" title="规格" data-index="specifications" />
        <a-table-column key="grade" title="标准" data-index="grade" />
        <a-table-column key="builtupArea" title="建筑面积" data-index="builtupArea" />
        <a-table-column key="useArea" title="使用面积" data-index="useArea" />
        <a-table-column key="isSale" title="是否出售" data-index="isSale">
          <template slot-scope="text, record">
            <a-tag :color="record.isSale == '已有住户' ? 'red' : 'blue'">{{record.isSale}}</a-tag>
          </template>
        </a-table-column>
      </a-table>
    </a-card>
  </page-main>
</template>

<script>
import { getRoomByUserId } from '@/api/requests/rq-manage.js'

export default {
  data () {
    return {
      loading: false,
      table_selectedRowKeys: [],
      room_data_list: [],
    }
  },
  created () {
    this.Get_RoomList()
  },
  methods: {
    Get_RoomList () {
      getRoomByUserId().then(res => {
        this.room_data_list = res.data
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