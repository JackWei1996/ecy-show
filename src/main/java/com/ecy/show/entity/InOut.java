package com.ecy.show.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2019-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InOut implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 车位编号
     */
    private String busNumber;

    /**
     * 车牌号码:识别正常情况下，车牌号首位为车牌颜色
空字开头车牌号码为未正常识别车牌或者空车牌

     */
    private String plateNo;

    /**
     * 出车位时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime outTime;

    /**
     * 进时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime stopTime;

    /**
     * 进出标志 0-进入 1-离开
     */
    private Integer stopFlag;


}
