<template>
  <a-card :bordered="false">
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-if="$auth('plat:sysUser:add')">新增</a-button>
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
          <a @click="handleEdit(record)" v-if="$auth('plat:sysUser:edit')">编辑</a>
          <a-divider type="vertical" v-if="$auth('plat:sysUser:edit')&&$auth('plat:sysUser:delete')" />
          <a-dropdown v-if="$auth('plat:sysUser:delete')">
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
import SysUserModal from './modules/SysUserModal'

export default {
  name: 'SysUserList',
  mixins: [JeecgListMixin],
  components: {
    SysUserModal
  },
  data() {
    return {
      queryParam: {},
      columns: [
        {
          title: '姓名',
          align: 'center',
          dataIndex: 'name'
        },
        {
          title: '工号',
          align: 'center',
          dataIndex: 'jobNumber'
        },
        {
          title: '单位id',
          align: 'center',
          dataIndex: 'companyId'
        },
        {
          title: '部门id',
          align: 'center',
          dataIndex: 'departmentId'
        },
        {
          title: '身份证',
          align: 'center',
          dataIndex: 'idCard'
        },
        {
          title: '',
          align: 'center',
          dataIndex: 'emil'
        },
        {
          title: '',
          align: 'center',
          dataIndex: 'wechart'
        },
        {
          title: '',
          align: 'center',
          dataIndex: 'qq'
        },
        {
          title: '手机号/登录账号',
          align: 'center',
          dataIndex: 'phone'
        },
        {
          title: '车牌号',
          align: 'center',
          dataIndex: 'carNumber'
        },
        {
          title: '车型',
          align: 'center',
          dataIndex: 'carType'
        },
        {
          title: '头像',
          align: 'center',
          dataIndex: 'headImgUrl'
        },
        {
          title: 'refresh_token',
          align: 'center',
          dataIndex: 'refreshToken'
        },
        {
          title: '微信openid',
          align: 'center',
          dataIndex: 'openid'
        },
        {
          title: '微信session',
          align: 'center',
          dataIndex: 'accessToken'
        },
        {
          title: '分组id，1管理员，2员工，3用户',
          align: 'center',
          dataIndex: 'groupId'
        },
        {
          title: '创建时间',
          align: 'center',
          dataIndex: 'gmtCreate'
        },
        {
          title: '是否推送',
          align: 'center',
          dataIndex: 'isPush'
        },
        {
          title: '修改时间',
          align: 'center',
          dataIndex: 'gmtModified'
        },
        {
          title: '是否删除0，未删除，1删除',
          align: 'center',
          dataIndex: 'deleted'
        },
        {
          title: '密码',
          align: 'center',
          dataIndex: 'pwd'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/sysUser/list',
        delete: '/sysUser'
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