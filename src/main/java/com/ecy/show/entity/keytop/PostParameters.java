package com.ecy.show.entity.keytop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
/**
 * 科拓接口：出场数据提交接口PostPayDetail
 */
@ApiModel
public class PostParameters {
    /**
     * 停车场编号
     */
    @ApiModelProperty(value = "停车场编号", dataType = "String")
    private String parkCode;
    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号", dataType = "String")
    private String plateNo;
    /**
     * 入场取票号/无牌车入场的卡号
     */
    @ApiModelProperty(value = "入场取票号", dataType = "String")
    private String cardNo;
    /**
     * 车辆类型
     */
    @ApiModelProperty(value = "车辆类型", dataType = "Integer")
    private Integer carType;
    /**
     * 入场时间
     */
    @ApiModelProperty(value = "入场时间", dataType = "String")
    private String entryTime;
    /**
     * 出场时间
     */
    @ApiModelProperty(value = "出场时间", dataType = "String")
    private String leaveTime;
    /**
     * 出口抓拍图片
     */
    @ApiModelProperty(value = "抓拍图片", dataType = "String")
    private String imgName;

    /**
     * 车位编号
     */
    @ApiModelProperty(value = "车位编号", dataType = "String")
    private String busNumber;

    /**
     * 进出时间 yyyy-MM-dd HH:mm:ss
     */
    @ApiModelProperty(value = "进出时间 yyyy-MM-dd HH:mm:ss", dataType = "String")
    private String stopTime;

    /**
     * 进出标志 0-进入 1-离开
     */
    @ApiModelProperty(value = "进出标志 0-进入 1-离开", dataType = "int")
    private Integer stopFlag;
}
