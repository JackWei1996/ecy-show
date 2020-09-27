package com.ecy.show.dto.admin;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class BookStaffDto {
    @NotBlank(message = "姓名不可为空")
    private String name;
    @NotBlank(message = "请先进行微信登录")
    private String code;
    @NotBlank(message = "工号不可为空")
    private String jobNumber;
}
