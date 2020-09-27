package com.ecy.show.controller.admin;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ecy.show.entity.group.Insert;
import com.ecy.show.entity.group.Update;
import com.ecy.show.exception.BusinessException;
import com.ecy.show.dto.admin.AdminEditDto;
import com.ecy.show.dto.sys.CurrentUser;
import com.ecy.show.dto.sys.LoginDto;
import com.ecy.show.entity.sys.User;
import com.ecy.show.global.Constant;
import com.ecy.show.service.sys.TicketService;
import com.ecy.show.service.sys.UserService;
import com.ecy.show.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员")
@Validated
public class AdminController {
    private UserService userService;
    private TicketService ticketService;

    public AdminController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @RequiresPermissions("sys:user:add")
    @PostMapping
    @ApiOperation(value = "管理员新增", notes = "json格传递数据", produces = "application/json")
    public void registerAdmin(@Validated({Insert.class}) @RequestBody AdminEditDto adminEditDto) throws UnsupportedEncodingException {
        User user = new User();
        BeanUtils.copyProperties(adminEditDto, user);
        user
        .setGmtCreate(LocalDateTime.now())
        .setPwd(DigestUtils.md5DigestAsHex(adminEditDto.getPwd().getBytes("UTF-8")));
        userService.save(user);
    }


    @RequiresPermissions("sys:user:edit")
    @PutMapping
    @ApiOperation(value = "修改管理员信息", notes = "json格传递数据", produces = "application/json")
    public void editAdminUser(@Validated({Update.class}) @RequestBody AdminEditDto editUserDto) throws BusinessException, UnsupportedEncodingException {
        int n = userService.count(new QueryWrapper<User>()
                .eq("phone", editUserDto.getPhone())
                .ne("id",editUserDto.getId())
                .ne("group_id", Constant.STAFF_ROLE)
                .ne("group_id",Constant.USER_ROLE)
        );
        if (n > 0){
            throw new BusinessException("手机号/登录账号重复");
        }

        User user = new User();
        BeanUtils.copyProperties(editUserDto, user);
        if(!StringUtils.isEmpty(editUserDto.getPwd())){
            user.setPwd(DigestUtils.md5DigestAsHex(editUserDto.getPwd().getBytes("UTF-8")));
        }
        userService.updateById(user);
    }

    @ApiOperation("退出登录")
    @PostMapping("logout")
    public void logout(HttpServletRequest request) {
        String token = JwtUtil.getToken(request);
        ticketService.logout(token);
    }

    @PostMapping("login")
    @ApiOperation("管理员登录")
    public String login(@Validated @RequestBody LoginDto loginDto)
            throws BusinessException, UnsupportedEncodingException {
        User user = userService.getOne(
                new QueryWrapper<User>()
                .eq("phone", loginDto.getPhone())
                .ne("group_id", Constant.STAFF_ROLE)
                .ne("group_id",Constant.USER_ROLE)
                .orderByDesc("id")
                .last("limit 1")
                , false);

        if (user == null) {
            throw new BusinessException("该手机号/登录账号不存在!");
        }

        if (!user.getPwd().equals(DigestUtils.md5DigestAsHex(loginDto.getPwd().getBytes("UTF-8")))) {
            throw new BusinessException("密码不正确!");
        }
        return ticketService.saveTicket(user);
    }

    @GetMapping("info")
    @ApiOperation("获取管理员信息")
    public CurrentUser getLoginInfo() throws Exception {
        return userService.getLoginInfo();
    }

}
