package com.ecy.show.dto.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class UserSearchDto extends Page {
    private String search;
    private Long companyId;
    private Long departmentId;
    private Boolean isStaff=true;
}