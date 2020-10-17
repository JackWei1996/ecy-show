package com.ecy.show.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ecy.show.dto.sys.CurrentUser;
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
@RestController("UserController")
@RequestMapping("/user")
@Validated
@Api(tags = "用户操作")
public class UserController {
    private UserService userService;
    private TicketService ticketService;
    private WorksService worksService;

    public UserController(UserService userService, TicketService ticketService, WorksService worksService) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.worksService = worksService;
    }

    @GetMapping
    @ApiOperation("获取个人信息")
    public CurrentUser getInfo() throws BusinessException {
        return userService.getLoginInfo();
    }

    @ApiOperation("注销")
    @PostMapping("logoff")
    public String register() throws BusinessException {
        CurrentUser loginInfo = userService.getLoginInfo();
        User user = new User();
        user.setDeleted(1);
        user.setId(loginInfo.getId());
        boolean update = userService.updateById(user);
        if (update){
            return "注销成功";
        }else {
            return "注销失败";
        }
    }

    @ApiOperation("上传作品")
    @PostMapping("uploadWorks")
    public String uploadWorks(Works works) throws BusinessException {
        CurrentUser loginInfo = userService.getLoginInfo();

        works.setUserId(loginInfo.getId());
        boolean save = worksService.save(works);
        if (save){
            return "上传成功";
        }else {
            return "上传失败";
        }
    }
}