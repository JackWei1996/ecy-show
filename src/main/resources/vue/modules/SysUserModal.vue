<template>
  <a-modal
    :title="title"
    :width="600"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="姓名">
          <a-input placeholder="请输入姓名" v-decorator="['name', validatorRules.name]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="工号">
          <a-input placeholder="请输入工号" v-decorator="['jobNumber', validatorRules.jobNumber]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单位id">
          <a-input placeholder="请输入单位id" v-decorator="['companyId', validatorRules.companyId]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="部门id">
          <a-input placeholder="请输入部门id" v-decorator="['departmentId', validatorRules.departmentId]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="身份证">
          <a-input placeholder="请输入身份证" v-decorator="['idCard', validatorRules.idCard]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="">
          <a-input placeholder="请输入" v-decorator="['emil', validatorRules.emil]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="">
          <a-input placeholder="请输入" v-decorator="['wechart', validatorRules.wechart]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="">
          <a-input placeholder="请输入" v-decorator="['qq', validatorRules.qq]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="手机号/登录账号">
          <a-input placeholder="请输入手机号/登录账号" v-decorator="['phone', validatorRules.phone]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="车牌号">
          <a-input placeholder="请输入车牌号" v-decorator="['carNumber', validatorRules.carNumber]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="车型">
          <a-input placeholder="请输入车型" v-decorator="['carType', validatorRules.carType]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="头像">
          <a-input placeholder="请输入头像" v-decorator="['headImgUrl', validatorRules.headImgUrl]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="refresh_token">
          <a-input placeholder="请输入refresh_token" v-decorator="['refreshToken', validatorRules.refreshToken]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="微信openid">
          <a-input placeholder="请输入微信openid" v-decorator="['openid', validatorRules.openid]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="微信session">
          <a-input placeholder="请输入微信session" v-decorator="['accessToken', validatorRules.accessToken]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="分组id，1管理员，2员工，3用户">
          <a-input placeholder="请输入分组id，1管理员，2员工，3用户" v-decorator="['groupId', validatorRules.groupId]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="是否推送">
          <a-input placeholder="请输入是否推送" v-decorator="['isPush', validatorRules.isPush]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="修改时间">
          <a-input placeholder="请输入修改时间" v-decorator="['gmtModified', validatorRules.gmtModified]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="是否删除0，未删除，1删除">
          <a-input placeholder="请输入是否删除0，未删除，1删除" v-decorator="['deleted', validatorRules.deleted]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="密码">
          <a-input placeholder="请输入密码" v-decorator="['pwd', validatorRules.pwd]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { postSysUser, putSysUser } from '@/api'

export default {
  name: 'SysUserList',
  data() {
    return {
      title: '操作',
      visible: false,
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      confirmLoading: false,
      form: this.$form.createForm(this),
      validatorRules: {
        name: { rules: [{ required: true, message: '请输入姓名!' }] }
        jobNumber: { rules: [{ required: true, message: '请输入工号!' }] }
        companyId: { rules: [{ required: true, message: '请输入单位id!' }] }
        departmentId: { rules: [{ required: true, message: '请输入部门id!' }] }
        idCard: { rules: [{ required: true, message: '请输入身份证!' }] }
        emil: { rules: [{ required: true, message: '请输入!' }] }
        wechart: { rules: [{ required: true, message: '请输入!' }] }
        qq: { rules: [{ required: true, message: '请输入!' }] }
        phone: { rules: [{ required: true, message: '请输入手机号/登录账号!' }] }
        carNumber: { rules: [{ required: true, message: '请输入车牌号!' }] }
        carType: { rules: [{ required: true, message: '请输入车型!' }] }
        headImgUrl: { rules: [{ required: true, message: '请输入头像!' }] }
        refreshToken: { rules: [{ required: true, message: '请输入refresh_token!' }] }
        openid: { rules: [{ required: true, message: '请输入微信openid!' }] }
        accessToken: { rules: [{ required: true, message: '请输入微信session!' }] }
        groupId: { rules: [{ required: true, message: '请输入分组id，1管理员，2员工，3用户!' }] }
        isPush: { rules: [{ required: true, message: '请输入是否推送!' }] }
        gmtModified: { rules: [{ required: true, message: '请输入修改时间!' }] }
        deleted: { rules: [{ required: true, message: '请输入是否删除0，未删除，1删除!' }] }
        pwd: { rules: [{ required: true, message: '请输入密码!' }] }
      }
    }
  },
  created() {},
  methods: {
    add() {
      this.edit({})
    },
    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model,'name','jobNumber','companyId','departmentId','idCard','emil','wechart','qq','phone','carNumber','carType','headImgUrl','refreshToken','openid','accessToken','groupId','isPush','gmtModified','deleted','pwd',))
      })
    },
    // 确定
    handleOk() {
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let formData = Object.assign(this.model, values)
          let obj
          if (!this.model.id) {
            obj = postSysUser(formData)
          } else {
            obj = putSysUser(formData)
          }
          obj
            .then(res => {
              this.$message.success('操作成功')
              this.$emit('ok')
              this.handleCancel()
            })
            .finally(() => {
              that.confirmLoading = false
            })
        }
      })
    },
    // 关闭
    handleCancel() {
      this.$emit('close')
      this.visible = false
    }
  }
}
</script>