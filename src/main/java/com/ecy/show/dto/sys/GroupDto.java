package com.ecy.show.dto.sys;

import lombok.Data;

import java.util.List;

@Data
public class GroupDto {
    private Integer id;
    private String name;
    private List<MenuDto>menus;

}

@Data
class MenuDto{
    private String menuCode;
    private String menuName;
    private List<PermissionDto>permissions;
}

@Data
class PermissionDto{
    private Long permissionId;
    private String permissionCode;
    private String permissionName;
}
