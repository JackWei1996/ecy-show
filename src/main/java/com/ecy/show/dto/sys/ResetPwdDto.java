package com.ecy.show.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPwdDto {
    @ApiModelProperty(name = "oldPwd", value = "旧密码", required = true, dataType = "String")
    @NotBlank(message = "请输入旧密码")
    private String oldPwd;

    @ApiModelProperty(name = "newPwd", value = "新密码", required = true, dataType = "String")
    @NotBlank(message = "请输入新密码")
    private String newPwd;
}
