package com.ecy.show.controller.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ecy.show.dto.sys.TicketSearchDto;
import com.ecy.show.service.sys.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController("SysTicketController")
@RequestMapping("/sys/ticket")
@Api(tags = "系统管理-票据")
@Validated
public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @RequiresPermissions("sys:ticket:list")
    @GetMapping("list")
    @ApiOperation("查询用户ticket")
    public IPage list(TicketSearchDto ticketSearchDto){
        return ticketService.listTicket(ticketSearchDto);
    }

    @RequiresPermissions("sys:ticket:delete")
    @DeleteMapping("{id}")
    @ApiOperation("根据id删除ticket")
    public void delete(@PathVariable Long id){
        ticketService.removeById(id);
    }

    @RequiresPermissions("sys:ticket:delete")
    @DeleteMapping(value = "batch")
    @ApiOperation("批量删除票据")
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(@RequestParam Long[] ids) {
        ticketService.removeByIds(Arrays.asList(ids));
    }
}
