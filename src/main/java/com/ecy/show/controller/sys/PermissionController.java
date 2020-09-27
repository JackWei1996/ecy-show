package com.ecy.show.controller.sys;

import com.ecy.show.service.sys.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/permission")
@Api(tags = "系统管理-权限管理")
@Validated
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation("查询所有权限, 给角色分配权限时调用")
    @RequiresPermissions("sys:permission:list")
    @GetMapping("list")
    public List<JSONObject> listAllPermission() {
        return permissionService.listAllPermission();
    }

}
