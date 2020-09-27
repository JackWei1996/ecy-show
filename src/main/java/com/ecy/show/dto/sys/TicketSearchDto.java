package com.ecy.show.dto.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
public class TicketSearchDto extends Page {
   private Integer isValid;
   private String nickname;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;
}
