package com.ecy.show.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("`sys_permission`")
public class Permission {
    public Permission() {
        this.deleted=0;
    }
    private Long id;
    private String menuCode;
    private String menuName;

    private String permissionCode;
    private String permissionName;

    private Integer sort;

    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private List<Permission>permissions;
}
