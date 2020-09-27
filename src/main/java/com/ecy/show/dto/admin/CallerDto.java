package com.ecy.show.dto.admin;

import lombok.Data;

@Data
public class CallerDto {
    private Integer wait;
    private Integer finish;
    private Integer causeType;
    private Integer status;
    private String number;
}
