package com.ecy.show.dto.admin;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Validated
@Data
public class BookDto {
    @NotBlank(message = "车牌号不可为空")
    @Length(min = 7, max = 8, message = "车牌号错误")
    private String carNumber;
    @NotBlank(message = "车辆类型不可为空")
    private String carType;
    @NotNull(message = "请选择开始时间")
    private LocalDateTime startTime;
    @NotNull(message = "请选择结束时间")
    private LocalDateTime endTime;
}
