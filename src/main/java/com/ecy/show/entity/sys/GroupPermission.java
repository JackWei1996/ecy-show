package com.ecy.show.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`sys_group_permission`")
public class GroupPermission {
    public GroupPermission() {
        this.deleted=0;
    }
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer groupId;
    private Long permissionId;
    @TableLogic
    private Integer deleted;
}
