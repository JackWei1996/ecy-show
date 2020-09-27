package com.ecy.show.dto.admin;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Validated
@Data
public class RejectDto {
    @Min(value = 1, message = "id不合法")
    private Long id;
    private Integer rejectType;
    private String rejectReason;
}
