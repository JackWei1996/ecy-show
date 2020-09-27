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
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int AWAIT = 0;
    public static final int NO_PASS = 1;
    public static final int PASS = 2;
    public static final int ERROR = 1;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 事由类型
     */
    private Integer causeType;

    /**
     * 姓名
     */
    private String name;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 单位id
     */
    private Long companyId;

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 受访人
     */
    private String respondents;

    private String respondentsPhone;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 拒绝类型
     */
    private Integer rejectType;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 车型
     */
    private String carType;

    /**
     * 车牌号
     */
    private String carNumber;

    /**
     * 停车位编号
     */
    private String parkingSpaceNumber;

    /**
     * 预约开始时间
     */
    private LocalDateTime startTime;

    /**
     * 预约介绍时间
     */
    private LocalDateTime endTime;

    /**
     * 预约状态：0待审核，1不通过，2通过
     */
    private Integer status;

    /**
     * 入场时间
     */
    private LocalDateTime inTime;

    /**
     * 出场时间
     */
    private LocalDateTime outTime;

    /**
     * 是否错停
     */
    private String isError;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    private LocalDateTime checkTime;

    private Long userId;

    @TableField(exist = false)
    private String companyName;
    @TableField(exist = false)
    private String departmentName;
    @TableField(exist = false)
    private String causeName;
    @TableField(exist = false)
    private String rejectName;
    @TableField(exist = false)
    private String carStatus;

    @TableField(exist = false)
    private String stopTime;
    @TableField(exist = false)
    private String stopFlag;
}
