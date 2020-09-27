package com.ecy.show.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ecy.show.entity.group.Insert;
import com.ecy.show.entity.group.Update;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 南京白墨科技有限公司
 * @since 2019-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Validated
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单位部门id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Min(value = 1, message = "id不合法", groups = {Update.class})
    private Long id;

    /**
     * 父级id
     */
    private Long superId;

    /**
     * 名称
     */
    @NotBlank(groups = {Insert.class}, message = "名称不可为空")
    private String name;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 人数
     */
    private Integer peoples;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    @TableField(exist = false)
    private String title;

    @TableField(exist = false)
    private Long key;

    @TableField(exist = false)
    private List<Department> children;

    @TableField(exist = false)
    private Boolean isLeaf;
}