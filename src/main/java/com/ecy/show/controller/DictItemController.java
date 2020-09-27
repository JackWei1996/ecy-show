package com.ecy.show.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ecy.show.entity.DictItem;
import com.ecy.show.entity.group.Insert;
import com.ecy.show.entity.group.Update;
import com.ecy.show.exception.BusinessException;
import com.ecy.show.service.DictItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
@RequestMapping("/item")
@Api(tags = "字典管理")
@Validated
public class DictItemController {
    private DictItemService dictItemService;

    public DictItemController(DictItemService dictItemService) {
        this.dictItemService = dictItemService;
    }

    @ApiOperation("新增字典项")
    @PostMapping
    public void insertItem(@RequestBody @Validated({Insert.class}) DictItem dictItem) throws BusinessException {
        verify(dictItem);
        dictItemService.save(dictItem);
    }

    private void verify(DictItem dictItem) throws BusinessException{
        int n = dictItemService.count(new QueryWrapper<DictItem>().and(r->r
                .eq("code", dictItem.getCode()).or()
                .eq("name", dictItem.getName()))
                .ne(null != dictItem.getId(),"id", dictItem.getId())
                .eq(null != dictItem.getDictTypeCode(), "dict_type_code",dictItem.getDictTypeCode()));
        if (n > 0){
            throw new BusinessException("字典项名称或编码重复");
        }
    }

    @ApiOperation("修改字典项")
    @PutMapping
    public void updateItem(@RequestBody @Validated({Update.class}) DictItem dictItem) throws BusinessException{
        verify(dictItem);
        dictItemService.updateById(dictItem);
    }

    @ApiOperation("根据id删除字典项")
    @DeleteMapping("/{id}")
    public void deletedType(@PathVariable @Min(value = 1, message = "id不合法") Long id){
        dictItemService.removeById(id);
    }

    @ApiOperation("根据id获取字典")
    @GetMapping(value = "/{id}")
    public DictItem getType(@PathVariable @Min(value = 1, message = "id不合法") Long id){
        return dictItemService.getById(id);
    }

    @ApiOperation("字典项列表")
    @GetMapping("list")
    public List<DictItem> listItem(@RequestParam @Validated @NotBlank(message = "请输入类型编码") String dictTypeCode){
        return dictItemService.list(new QueryWrapper<DictItem>()
                .eq("dict_type_code", dictTypeCode).orderByAsc("sort"));
    }
}
