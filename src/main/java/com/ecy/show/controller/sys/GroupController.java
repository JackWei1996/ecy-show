package com.ecy.show.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecy.show.dto.sys.GroupDto;
import com.ecy.show.dto.sys.group.EditGroupDto;
import com.ecy.show.entity.sys.Group;
import com.ecy.show.exception.BusinessException;
import com.ecy.show.global.Constant;
import com.ecy.show.service.sys.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@Api(tags = "系统管理-管理分组")
@RestController
@RequestMapping("/sys/group")
public class GroupController {
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequiresPermissions("sys:group:add")
    @PostMapping
    @ApiOperation("添加")
    public void saveGroup(@Validated @RequestBody EditGroupDto group) throws Exception{
        groupService.saveGroup(group);
    }

    @RequiresPermissions("sys:group:edit")
    @PutMapping
    @ApiOperation("修改")
    public void editGroup(@Validated @RequestBody EditGroupDto group) throws Exception{
        groupService.editGroup(group);
    }

    @GetMapping
    @ApiOperation("根据id查询")
    public Group getGroup(@RequestParam Integer id) {
        return groupService.getById(id);
    }

    @RequiresPermissions("sys:group:delete")
    @DeleteMapping("{id}")
    @ApiOperation("根据id删除")
    public void delete(@PathVariable @NotNull(message = "id不合法")  @Min(value = 1, message = "id不合法") Long id) throws BusinessException {
       Group group= groupService.getById(id);
        if(group.getIsStatic()==1){
            throw new BusinessException("静态分组不能删除!");
        }
        groupService.removeById(id);
    }

    @RequiresPermissions("sys:group:list")
    @GetMapping("list")
    @ApiOperation("分页查询")
    public IPage list(Page page, @RequestParam(required = false) String name){
        return groupService.page(page, new QueryWrapper<Group>().like(!StringUtils.isEmpty(name),"name",name).orderByDesc("id"));
    }

    @RequiresPermissions("sys:permission:list")
    @GetMapping("permission")
    @ApiOperation("根据groupId得到该组别的权限")
    public GroupDto getPermissionByGroupId(@RequestParam Integer id) {
        return groupService.getPermissionByGroupId(id);
    }

    @GetMapping("all")
    public List list(){
        return groupService.list(new QueryWrapper<Group>()
                .ne("id", Constant.USER_ROLE)
                .ne("id", Constant.STAFF_ROLE));
    }
}
