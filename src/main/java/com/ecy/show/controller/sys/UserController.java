package com.ecy.show.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ecy.show.dto.sys.*;
import com.ecy.show.entity.sys.User;
import com.ecy.show.exception.BusinessException;
import com.ecy.show.global.Constant;
import com.ecy.show.service.DepartmentService;
import com.ecy.show.service.sys.UserService;
import com.ecy.show.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author AntZero
 * @since 2019-09-23
 */
@RestController("SysUserController")
@RequestMapping("/sys/user")
@Api(tags = "系统管理-用户")
@Validated
public class UserController {
    private UserService userService;
    private DepartmentService departmentService;

    public UserController(UserService userService, DepartmentService departmentService) {
        this.userService = userService;
        this.departmentService = departmentService;
    }

    @RequiresPermissions("sys:user:add")
    @PostMapping
    @ApiOperation("添加员工")
    public void addUser(@Validated @RequestBody RegisterDto register) throws BusinessException {
        int n = userService.count(new QueryWrapper<User>()
                .eq("job_number", register.getJobNumber()));
        if (n > 0){
            throw new BusinessException("工号重复");
        }
        User user = new User();
        BeanUtils.copyProperties(register, user);
        user.setGroupId(Constant.STAFF_ROLE);
        user.setGmtCreate(LocalDateTime.now());
        boolean flag = userService.save(user);
        //更新部门数据
        if (flag){
            departmentService.addPeoples(user.getCompanyId(), user.getDepartmentId());
        }
    }

    @RequiresPermissions("sys:user:delete")
    @DeleteMapping("{id}")
    @ApiOperation("删除用户")
    public void adminDeleteUser(@PathVariable @Min(value = 1, message = "id不合法") Long id) {
        User user = userService.getById(id);
        boolean flag = userService.removeById(id);
        //删除成功更新部门数据
        if (flag){
            departmentService.subPeople(user.getCompanyId(), user.getDepartmentId());
        }
    }

    @RequiresPermissions("sys:user:batch-delete")
    @DeleteMapping(value = "batch")
    @ApiOperation("批量删除用户")
    @Transactional(rollbackFor = Exception.class)
    public void adminBatchDeleteUser(@RequestParam @NotNull(message = "请选择用户id") Long[] ids) {
        //删除成功更新部门数据
        for (Long userId : ids){
            User user = userService.getById(userId);
            departmentService.subPeople(user.getCompanyId(), user.getDepartmentId());
        }
        userService.removeByIds(Arrays.asList(ids));
    }

    @RequiresPermissions("sys:user:list")
    @GetMapping("list")
    @ApiOperation("获取用户列表信息")
    public IPage adminPageUser(UserSearchDto searchDto) {
        return userService.adminPageUser(searchDto);
    }

    @RequiresPermissions("sys:user:id")
    @GetMapping
    @ApiOperation("根据用户id查询")
    public User getUser(@RequestParam @Min(value = 1, message = "id不合法") Long userId) {
        User user = userService.getById(userId);
        user.setAccessToken(null);
        user.setOpenid(null);
        user.setPwd(null);
        return user;
    }

    @RequiresPermissions("sys:user:assign")
    @PutMapping("assign")
    @ApiOperation("设置用户分组")
    public void assignUserGroup(@Validated @RequestBody AssignUserGroupDto editUserDto) {
        userService.assignUserGroup(editUserDto);
    }

    @RequiresPermissions("sys:user:edit")
    @PutMapping
    @ApiOperation("修改用户信息")
    public void editUser(@Validated @RequestBody EditUserDto editUserDto) throws BusinessException {
        int n = userService.count(new QueryWrapper<User>()
                .eq("job_number", editUserDto.getJobNumber()).ne("id",editUserDto.getId()));
        if (n > 0){
            throw new BusinessException("工号重复");
        }

       User oldUser= userService.getById(editUserDto.getId());
        if(!oldUser.getCompanyId().equals(editUserDto.getCompanyId())||!oldUser.getDepartmentId().equals(editUserDto.getDepartmentId()))
        {
            departmentService.subPeople(oldUser.getCompanyId(), oldUser.getDepartmentId());
            departmentService.addPeoples(editUserDto.getCompanyId(), editUserDto.getDepartmentId());
        }

        User user = new User();
        BeanUtils.copyProperties(editUserDto, user);
        user.setGmtModified(LocalDateTime.now());
        userService.updateById(user);
    }

    @PutMapping("reset-pwd")
    @ApiOperation("根据旧密码修改自己的密码")
    public void updatePwd(@RequestBody @Validated ResetPwdDto resetPwdDto) throws Exception {
        Long userId= JwtUtil.getUserId();
        userService.updatePwd(userId, resetPwdDto);
    }
    @PutMapping("update-name")
    @ApiOperation("用户修改姓名")
    @ApiImplicitParam(paramType = "query", name = "newName", value = "用户新姓名 ", required = true, dataType = "String")
    public void updateName(@NotBlank(message = "请填写姓名") String newName) throws Exception {
        Long userId = JwtUtil.getUserId();
        int count = userService.count(new QueryWrapper<User>()
                .eq("name", newName)
                .ne("id", userId)
                .ne("group_id",Constant.STAFF_ROLE)
        );
        if (count > 0) {
            throw new BusinessException("该姓名已存在");
        }
        userService.updateName(userId, newName);
    }
}
