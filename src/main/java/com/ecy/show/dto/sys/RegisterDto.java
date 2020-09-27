package com.ecy.show.dto.sys;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RegisterDto {
    @NotBlank(message = "请输入员工名")
    private String name;
    @NotBlank(message = "请输入员工号")
    private String jobNumber;
    @NotNull(message = "请选择单位")
    private Long companyId;
    @NotNull(message = "请选择部门")
    private Long departmentId;
    private String phone;
    private String idCard;
    private String carNumber;
    private String carType;
}
