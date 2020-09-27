package com.ecy.show.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ecy.show.entity.group.Insert;
import com.ecy.show.entity.group.Update;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
public class DictItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "id不能为空", groups = {Update.class})
    private Long id;

    /**
     * 字典类型code
     */
    @NotBlank(message = "请选择字典类型编码", groups = {Insert.class})
    private String dictTypeCode;

    /**
     * 字典项名称
     */
    @NotBlank(message = "请输入字典项名", groups = {Insert.class})
    private String name;

    /**
     * 字典项编码
     */
    @NotBlank(message = "请输入字典项编码", groups = {Insert.class})
    private String code;

    /**
     * 排序
     */
    private Integer sort;
}