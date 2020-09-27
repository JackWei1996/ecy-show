package com.ecy.show.dto.sys;

import lombok.Data;

@Data
public class EditUserDto {
    private Long id;
    /**
     * 用户名
     */
    private String name;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 单位id
     */
    private Long companyId;

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 车牌号
     */
    private String carNumber;

    /**
     * 车型
     */
    private String carType;

    /**
     * 头像
     */
    private String headImgUrl;
}
