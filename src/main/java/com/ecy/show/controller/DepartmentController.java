package com.ecy.show.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ecy.show.entity.Department;
import com.ecy.show.entity.group.Insert;
import com.ecy.show.entity.group.Update;
import com.ecy.show.exception.BusinessException;
import com.ecy.show.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 南京白墨科技有限公司
 * @since 2019-10-29
 */
@RestController
@RequestMapping("/department")
@Validated
@Api(tags = "单位部门管理")
public class DepartmentController {
    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @RequiresPermissions("sys:department:add")
    @ApiOperation("新增部门")
    @PostMapping
    public void insertDepartment(@RequestBody @Validated({Insert.class}) Department department) throws BusinessException {
        if (null == department.getSuperId()){
            department.setSuperId(0L);
        }
        department.setPeoples(0);
        department.setGmtCreate(LocalDateTime.now());
        departmentService.save(department);
    }

    @RequiresPermissions("sys:department:edit")
    @ApiOperation("修改部门信息")
    @PutMapping
    public void updateDepartment(@RequestBody @Validated({Update.class}) Department department) throws BusinessException{
        department.setGmtModified(LocalDateTime.now());
        departmentService.updateById(department);
    }

    @RequiresPermissions("sys:department:delete")
    @ApiOperation("删除某部门,如果某部门下有用户，提示用户无法删除")
    @DeleteMapping("/{id}")
    public void deletedDepartment(@PathVariable @Min(value = 1, message = "id不合法") Long id)
            throws BusinessException{
        Department department = departmentService.getById(id);
        if (department.getPeoples() > 0){
            throw new BusinessException("该部门下有用户，无法删除");
        }
        departmentService.removeById(id);
    }

    @ApiOperation("根据id获取部门")
    @GetMapping("/{id}")
    public Department getDepartment(@PathVariable @Min(value = 1, message = "id不合法") Long id){
        return departmentService.getById(id);
    }

    @ApiOperation("得到所有父节点为superId的部门列表")
    @GetMapping("list")
    public List<Department> listDepartment(@RequestParam @Min(value = 0, message = "superId不合法") Long superId){
        return departmentService.list(new QueryWrapper<Department>().eq("super_id", superId));
    }

    @ApiOperation("得到部门信息（不分页），树型结构数据")
    @GetMapping("tree")
    public List<Department> listTree(){
        return departmentService.listTree();
    }
}
