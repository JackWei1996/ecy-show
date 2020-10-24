package com.ecy.show.controller.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecy.show.dto.sys.CurrentUser;
import com.ecy.show.entity.Works;
import com.ecy.show.entity.sys.User;
import com.ecy.show.exception.BusinessException;
import com.ecy.show.service.WorksService;
import com.ecy.show.service.sys.TicketService;
import com.ecy.show.service.sys.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("getInfo")
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
    public String uploadWorks(@RequestBody Works works) throws BusinessException {
        CurrentUser loginInfo = userService.getLoginInfo();

        works.setUserId(loginInfo.getId());
        boolean flag = worksService.save(works);
        if (flag){
            return "上传成功";
        }else {
            return "上传失败";
        }
    }

    @ApiOperation("修改作品")
    @PostMapping("updateWorks")
    public String updateWorks(@RequestBody Works works) throws BusinessException {
        CurrentUser loginInfo = userService.getLoginInfo();

        works.setUserId(loginInfo.getId());
        boolean flag = worksService.updateById(works);
        if (flag){
            return "修改成功";
        }else {
            return "修改失败";
        }
    }

    @ApiOperation("删除作品")
    @PostMapping("deleteWorks")
    public String deleteWorks(@RequestBody Long id) throws BusinessException {
        boolean flag = worksService.removeById(id);
        if (flag){
            return "删除成功";
        }else {
            return "删除失败";
        }
    }

    @ApiOperation("根据id查询作品")
    @GetMapping("getWorks")
    public Works getWorks(@RequestParam Long id) throws BusinessException {
        Works byId = worksService.getById(id);
        return byId;
    }

    @ApiOperation("查询本人作品")
    @GetMapping("listMyWorks")
    public IPage listMyWorks(Page page, Works works) throws BusinessException {
        CurrentUser loginInfo = userService.getLoginInfo();
        works.setUserId(loginInfo.getId());

        return worksService.search(page, works);
    }
}