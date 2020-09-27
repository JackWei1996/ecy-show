package com.ecy.show.dto.sys.group;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class EditGroupDto {
    private Integer id;
    @NotBlank(message = "编码不能为空")
    private String name;
    private String info;

    public List<Long> permissions;
}
