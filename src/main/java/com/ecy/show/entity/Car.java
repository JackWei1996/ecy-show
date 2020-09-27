package com.ecy.show.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2019-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer LEAVE = 0;
    public static final Integer DONT_IN = 1;
    public static final Integer IN = 2;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 车牌号
     */
    private String carNumber;

    private String carType;
    /**
     * 车主
     */
    private String name;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 车辆当前状态
     */
    private Integer status;

    /**
     * 单位id
     */
    private Long companyId;

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 预约次数
     */
    private Integer appointmentCount;

    /**
     * 停车次数
     */
    private Integer parkingCount;

    /**
     * 创建日期
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改日期
     */
    private LocalDateTime gmtModified;

    private Long userId;

    @TableField(exist = false)
    private String companyName;
    @TableField(exist = false)
    private String departmentName;
}
