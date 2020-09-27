package com.ecy.show.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
public class ParkingSpace implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Integer FREE = 0;
    public static final Integer BOOK = 1;
    public static final Integer USE = 2;
    public static final Integer ERROR = 3;
    public static final Integer OCCUPY = 4;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 停车位编号
     */
    @NotBlank(message = "车位编号不可为空")
    private String number;

    /**
     * 所属停车场
     */
    @NotBlank(message = "所属停车场不可为空")
    private String parkingLot;

    /**
     * 楼层
     */
    @NotBlank(message = "楼层不可为空")
    private String floor;

    /**
     * 区域
     */
    @NotBlank(message = "区域不可为空")
    private String area;

    /**
     * 使用次数
     */
    private Integer usageCount;

    /**
     * 状态：0空闲，1已被预约，2使用中,3错停，4占用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    @TableField(exist = false)
    private List<Appointment> car;
}
