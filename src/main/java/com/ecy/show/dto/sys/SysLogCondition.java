package com.ecy.show.dto.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysLogCondition {
    private String ip;

    private Long userId;

    private String nickname;

    private String url;

    private Integer logType;

    private Integer minSpendTime;

    private Integer maxSpendTime;

    private LocalDateTime beginDate;

    private LocalDateTime endDate;
}
