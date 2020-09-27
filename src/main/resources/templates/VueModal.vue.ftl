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
      <#list table.fields as field>
       <#if (field.keyFlag!=true&&field.propertyName!='gmtCreate')>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="${field.comment}">
          <a-input placeholder="请输入${field.comment}" v-decorator="['${field.propertyName}', validatorRules.${field.propertyName}]" />
        </a-form-item>
        </#if>
       </#list>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { post${table.entityName}, put${table.entityName} } from '@/api'

export default {
  name: '${table.entityName}List',
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
      <#list table.fields as field>
        <#if (field.keyFlag!=true&&field.propertyName!='gmtCreate')>
        ${field.propertyName}: { rules: [{ required: true, message: '请输入${field.comment}!' }] }
        </#if>
     </#list>
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
        this.form.setFieldsValue(pick(this.model,<#list table.fields as field><#if (field.keyFlag!=true&&field.propertyName!='gmtCreate')>'${field.propertyName}',</#if></#list>))
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
            obj = post${table.entityName}(formData)
          } else {
            obj = put${table.entityName}(formData)
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