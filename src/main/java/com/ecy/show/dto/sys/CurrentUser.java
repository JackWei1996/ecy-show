package com.ecy.show.dto.sys;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 在线用户信息
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public  class CurrentUser {
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
     * 微信openid
     */
    private String openid;

    /**
     * refreshToken
     */
    private String refreshToken;
    /**
     * accessToken
     */
    private String accessToken;

    /**
     * 头像
     */
    private String headImgUrl;

    @TableField(exist = false)
    private String companyName;
    @TableField(exist = false)
    private String departmentName;

    private Integer groupId;

    private GroupDto groupDto;
}