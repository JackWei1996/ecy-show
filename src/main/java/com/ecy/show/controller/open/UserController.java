package com.ecy.show.controller.open;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecy.show.dto.sys.LoginDto;
import com.ecy.show.entity.Works;
import com.ecy.show.entity.sys.User;
import com.ecy.show.exception.BusinessException;
import com.ecy.show.service.WorksService;
import com.ecy.show.service.sys.TicketService;
import com.ecy.show.service.sys.UserService;
import com.ecy.show.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 南京白墨科技有限公司
 * @since 2019-10-29
 */
@RestController("OpenUserController")
@RequestMapping("/open")
@Validated
@Api(tags = "用户公共接口")
public class UserController {
    private UserService userService;
    private TicketService ticketService;
    private WorksService worksService;

    public UserController(UserService userService, TicketService ticketService,
                          WorksService worksService) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.worksService = worksService;
    }

    @ApiOperation("注册")
    @PostMapping("register")
    public User register(User user) throws BusinessException {
        if (StringUtils.isAnyBlank(user.getPwd(), user.getName(), user.getPhone())){
            throw new BusinessException("请输入正确信息!");
        }
        userService.add(user);
        return user;
    }

    @PostMapping("login")
    @ApiOperation("用户登录")
    public String login(@Validated @RequestBody LoginDto loginDto)
            throws BusinessException, UnsupportedEncodingException {
        User user = userService.getOne(
                new QueryWrapper<User>()
                        .eq("phone", loginDto.getPhone())
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

    @ApiOperation("搜索")
    @PostMapping("search")
    public IPage logout(Page page, Works works) {
        return worksService.search(page, works);
    }

}