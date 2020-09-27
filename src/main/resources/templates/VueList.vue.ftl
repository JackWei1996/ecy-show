<template>
  <a-card :bordered="false">
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-if="$auth('plat:${table.entityPath}:add')">新增</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <a-table
        ref="table"
        size="middle"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        @change="handleTableChange"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)" v-if="$auth('plat:${table.entityPath}:edit')">编辑</a>
          <a-divider type="vertical" v-if="$auth('plat:${table.entityPath}:edit')&&$auth('plat:${table.entityPath}:delete')" />
          <a-dropdown v-if="$auth('plat:${table.entityPath}:delete')">
            <a class="ant-dropdown-link">
              更多
              <a-icon type="down" />
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->
    <group-modal ref="modalForm" @ok="modalFormOk"></group-modal>
  </a-card>
</template>
<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import ${table.entityName}Modal from './modules/${table.entityName}Modal'

export default {
  name: '${table.entityName}List',
  mixins: [JeecgListMixin],
  components: {
    ${table.entityName}Modal
  },
  data() {
    return {
      queryParam: {},
      columns: [
      <#list table.fields as field>
       <#if field.keyFlag!=true>
        {
          title: '${field.comment}',
          align: 'center',
          dataIndex: '${field.propertyName}'
        },
        </#if>
        </#list>
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/${table.entityPath}/list',
        delete: '/${table.entityPath}'
      }
    }
  },
  filters: {},
  computed: {},
  created() {},
  methods: {}
}
</script>
<style scoped>
</style>