<template>
  <a-card :bordered="false">
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-if="$auth('plat:works:add')">新增</a-button>
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
          <a @click="handleEdit(record)" v-if="$auth('plat:works:edit')">编辑</a>
          <a-divider type="vertical" v-if="$auth('plat:works:edit')&&$auth('plat:works:delete')" />
          <a-dropdown v-if="$auth('plat:works:delete')">
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
import WorksModal from './modules/WorksModal'

export default {
  name: 'WorksList',
  mixins: [JeecgListMixin],
  components: {
    WorksModal
  },
  data() {
    return {
      queryParam: {},
      columns: [
        {
          title: '1清纯JK 2漫展场照 3动漫人物 4 游戏角色 5Lolita 6古装',
          align: 'center',
          dataIndex: 'type'
        },
        {
          title: '标题',
          align: 'center',
          dataIndex: 'title'
        },
        {
          title: '',
          align: 'center',
          dataIndex: 'details'
        },
        {
          title: '图片url逗号分隔',
          align: 'center',
          dataIndex: 'imgsUrl'
        },
        {
          title: '1coser 2摄影师',
          align: 'center',
          dataIndex: 'roleType'
        },
        {
          title: '用户id',
          align: 'center',
          dataIndex: 'userId'
        },
        {
          title: '创建时间',
          align: 'center',
          dataIndex: 'createTime'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/works/list',
        delete: '/works'
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