package com.ecy.show.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    @ApiModelProperty(name = "phone", value = "账号", required = true, dataType = "String")
    @NotBlank(message = "账号不能为空")
    private String phone;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(name = "pwd", value = "密码", required = true, dataType = "String")
    private String pwd;
    private String code;
}
