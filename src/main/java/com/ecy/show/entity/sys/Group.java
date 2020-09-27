package com.ecy.show.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Accessors(chain = true)
@Data
@TableName("`sys_group`")
public class Group {
    public Group() {
        this.deleted=0;
    }
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "编码不能为空")
    private String name;
    private String info;

    /*
    * 1.静态角色
    * 0.不是静态角色
    * */
    private Integer isStatic;
    @TableLogic
    private Integer deleted;
}
