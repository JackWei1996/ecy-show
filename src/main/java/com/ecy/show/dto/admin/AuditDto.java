package com.ecy.show.dto.admin;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class AuditDto {
    @NotNull(message = "请选择事由类型")
    private Integer causeType;
    @NotNull(message = "请选择单位")
    private Long companyId;
    @NotNull(message = "请选择单位")
    private Long departmentId;
    @NotBlank(message = "请填写受访人姓名")
    private String respondents;
    @NotBlank(message = "请填写受访人手机号")
    private String respondentsPhone;
    private String remarks;
    @NotNull(message = "请选择开始时间")
    private LocalDateTime startTime;
    @NotNull(message = "请选择结束时间")
    private LocalDateTime endTime;
}