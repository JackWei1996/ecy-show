package com.ecy.show.dto.admin;

import com.ecy.show.annotation.Phone;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class BookUserDto {
    @NotBlank(message = "姓名不可为空")
    private String name;
    @Phone
    private String phone;
    @NotBlank(message = "请先进行微信登录")
    private String code;
    @NotBlank(message = "身份证不可为空")
    private String idCard;
}
