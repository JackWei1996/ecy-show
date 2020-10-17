package com.ecy.show.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jack魏
 * @since 2020-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
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

    private String emil;

    private String wechart;

    private String qq;

    /**
     * 手机号/登录账号
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

    /**
     * refresh_token
     */
    private String refreshToken;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 微信session
     */
    private String accessToken;

    /**
     * 分组id，1管理员，2员工，3用户
     */
    private Integer groupId;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 是否推送
     */
    private Boolean isPush;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 是否删除0，未删除，1删除
     */
    private Integer deleted;

    /**
     * 密码
     */
    private String pwd;


}
