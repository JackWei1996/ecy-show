package com.ecy.show.controller;

import com.ecy.show.dto.sys.CurrentUser;
import com.ecy.show.exception.BusinessException;
import com.ecy.show.service.sys.TicketService;
import com.ecy.show.service.sys.UserService;
import com.ecy.show.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 南京白墨科技有限公司
 * @since 2019-10-29
 */
@RestController
@RequestMapping("/user")
@Validated
@Api(tags = "用户")
public class UserController {
    private UserService userService;
    private TicketService ticketService;

    public UserController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @GetMapping
    @ApiOperation("获取个人信息")
    public CurrentUser getInfo() throws BusinessException {
        return userService.getLoginInfo();
    }

    @ApiOperation("退出登录")
    @PostMapping("logout")
    public void logout(HttpServletRequest request) {
        try {
            String token = JwtUtil.getToken(request);
            ticketService.logout(token);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}