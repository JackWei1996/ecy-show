package com.ecy.show.controller.sys;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecy.show.annotation.DisableAuditing;
import com.ecy.show.dto.sys.SysLogCondition;
import com.ecy.show.service.sys.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author AntZero
 * @since 2019-09-23
 */
@Validated
@Api(tags = "系统管理-系统日志")
@RestController
@RequestMapping("/sys/log")
public class LogController {
    @Autowired
    private SysLogService systemLogService;

    @DisableAuditing
    @RequiresPermissions("sys:log:list")
    @GetMapping("list")
    @ApiOperation("查询系统日志列表")
    public IPage list(Page page, SysLogCondition condition){
        return systemLogService.listSystemLogByAdmin(page, condition);
    }

    @DisableAuditing
    @RequiresPermissions("sys:log:delete")
    @DeleteMapping("{id}")
    @ApiOperation("根据id删除")
    public void delete(@PathVariable Long id) {
        systemLogService.removeById(id);
    }

    @DisableAuditing
    @RequiresPermissions("sys:log:delete")
    @DeleteMapping(value = "batch")
    @ApiOperation("批量删除日志")
    public void batchDelete(@RequestParam Long[] ids) {
        systemLogService.removeByIds(Arrays.asList(ids));
    }
}
