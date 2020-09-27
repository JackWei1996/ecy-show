package com.ecy.show.entity.sys;

import com.baomidou.mybatisplus.annotation.*;
import com.ecy.show.dto.sys.GroupDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 南京白墨科技有限公司
 * @since 2019-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`sys_user`")
public class User implements Serializable {
    public User() {
        this.deleted=0;
    }

    public static final int DELETED = 1;
    public static final int PUSH = 1;
    public static final int NORMAL = 0;

    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    private String pwd;

    /**
     * 车牌号
     */
    private String carNumber;

    /**
     * 车型id
     */
    private String carType;

    /**
     * 头像
     */
    private String headImgUrl;

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
     * 用户类型
     */
    private Integer groupId;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;
    /*
    * 非普通用户，非员工用户、是否推送，true，推送，false，不推送
    * */
    private  Boolean isPush=false;

    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private GroupDto groupDto;

    @TableField(exist = false)
    private String companyName;
    @TableField(exist = false)
    private String departmentName;
}
