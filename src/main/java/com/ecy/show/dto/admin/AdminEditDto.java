package com.ecy.show.dto.admin;

import com.ecy.show.entity.group.Insert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "editUserDto", description = "管理员对象")
public class AdminEditDto {
    private Long id;
    @ApiModelProperty(name = "name", value = "姓名", required = true, dataType = "String")
    @NotBlank(message = "姓名不能为空")
    private String name;
    @ApiModelProperty(name = "phone", value = "账号", required = true, dataType = "String")
    @NotBlank(message = "账号不能为空")
    private String phone;

    @NotBlank(message = "密码不能为空", groups = {Insert.class})
    @ApiModelProperty(name = "pwd", value = "密码", required = true, dataType = "String")
    private String pwd;
    private Integer groupId;

    private  Boolean isPush;
}