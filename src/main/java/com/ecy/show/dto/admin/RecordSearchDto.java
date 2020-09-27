package com.ecy.show.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "预约查询对象")
public class RecordSearchDto {
    @ApiModelProperty(value = "车牌号", dataType = "String")
    private String carNumber;
    @ApiModelProperty(value = "车牌号、身份证、工号、姓名", dataType = "String")
    private String number;
    @ApiModelProperty(value = "此时间内预约")
    private LocalDateTime time;
    @ApiModelProperty(value = "状态", dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "类型", dataType = "Integer")
    private Integer type;
}
