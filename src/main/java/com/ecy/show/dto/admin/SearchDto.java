package com.ecy.show.dto.admin;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchDto {
    private String name;
    private Integer type;
    private String number;
    private String carNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer isOut;
}
