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
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="1清纯JK 2漫展场照 3动漫人物 4 游戏角色 5Lolita 6古装">
          <a-input placeholder="请输入1清纯JK 2漫展场照 3动漫人物 4 游戏角色 5Lolita 6古装" v-decorator="['type', validatorRules.type]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="标题">
          <a-input placeholder="请输入标题" v-decorator="['title', validatorRules.title]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="">
          <a-input placeholder="请输入" v-decorator="['details', validatorRules.details]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="图片url逗号分隔">
          <a-input placeholder="请输入图片url逗号分隔" v-decorator="['imgsUrl', validatorRules.imgsUrl]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="1coser 2摄影师">
          <a-input placeholder="请输入1coser 2摄影师" v-decorator="['roleType', validatorRules.roleType]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="用户id">
          <a-input placeholder="请输入用户id" v-decorator="['userId', validatorRules.userId]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="创建时间">
          <a-input placeholder="请输入创建时间" v-decorator="['createTime', validatorRules.createTime]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { postWorks, putWorks } from '@/api'

export default {
  name: 'WorksList',
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
        type: { rules: [{ required: true, message: '请输入1清纯JK 2漫展场照 3动漫人物 4 游戏角色 5Lolita 6古装!' }] }
        title: { rules: [{ required: true, message: '请输入标题!' }] }
        details: { rules: [{ required: true, message: '请输入!' }] }
        imgsUrl: { rules: [{ required: true, message: '请输入图片url逗号分隔!' }] }
        roleType: { rules: [{ required: true, message: '请输入1coser 2摄影师!' }] }
        userId: { rules: [{ required: true, message: '请输入用户id!' }] }
        createTime: { rules: [{ required: true, message: '请输入创建时间!' }] }
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
        this.form.setFieldsValue(pick(this.model,'type','title','details','imgsUrl','roleType','userId','createTime',))
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
            obj = postWorks(formData)
          } else {
            obj = putWorks(formData)
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