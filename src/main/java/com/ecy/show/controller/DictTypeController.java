package com.ecy.show.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecy.show.entity.DictItem;
import com.ecy.show.entity.DictType;
import com.ecy.show.entity.group.Insert;
import com.ecy.show.entity.group.Update;
import com.ecy.show.exception.BusinessException;
import com.ecy.show.mapper.DictItemMapper;
import com.ecy.show.service.DictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 南京白墨科技有限公司
 * @since 2019-10-29
 */
@RestController
@RequestMapping("/type")
@Api(tags = "字典管理")
@Validated
public class DictTypeController {
    private DictTypeService dictTypeService;
    private DictItemMapper dictItemMapper;

    public DictTypeController(DictTypeService dictTypeService, DictItemMapper dictItemMapper) {
        this.dictTypeService = dictTypeService;
        this.dictItemMapper = dictItemMapper;
    }

    @ApiOperation("新增字典类型")
    @PostMapping
    public void insertType(@Validated({Insert.class}) @RequestBody DictType dictType) throws BusinessException {
        verify(dictType);
        dictTypeService.save(dictType);
    }

    private void verify(DictType dictType) throws BusinessException{
        int n = dictTypeService.count(new QueryWrapper<DictType>().and(r->r.eq("name", dictType.getName()).or()
                .eq("code", dictType.getCode())).ne(null != dictType.getId(), "id", dictType.getId()));
        if (n > 0){
            throw new BusinessException("名称或编码重复");
        }
    }

    @ApiOperation("根据id获取字典")
    @GetMapping(value = "/{id}")
    public DictType getType(@PathVariable @Min(value = 1, message = "id不合法") Long id){
        return dictTypeService.getById(id);
    }

    @ApiOperation("修改字典类型")
    @PutMapping
    public void updateType(@Validated({Update.class}) @RequestBody DictType dictType) throws BusinessException{
        verify(dictType);

        DictType old = dictTypeService.getById(dictType.getId());

        if (null == old){
            throw new BusinessException("修改失败，id不正确");
        }

        if (null != dictType.getCode()){
            dictItemMapper.update(new DictItem().setDictTypeCode(dictType.getCode()),
                    new QueryWrapper<DictItem>().eq("dict_type_code", old.getCode()));
        }
        dictTypeService.updateById(dictType);
    }

    @ApiOperation("根据id删除字典类型")
    @DeleteMapping("/{id}")
    public void deletedType(@PathVariable @Min(value = 1, message = "id不合法") Long id){
        dictTypeService.removeById(id);
    }

    @ApiOperation("字典类型列表")
    @GetMapping("list")
    public IPage listType(Page page, String name){
        return dictTypeService.listType(page, name);
    }
}
