package com.ecy.show.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecy.show.entity.DictType;
import com.ecy.show.mapper.DictTypeMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 南京白墨科技有限公司
 * @since 2019-10-29
 */
@Service
public class DictTypeService extends ServiceImpl<DictTypeMapper, DictType> {

    public IPage listType(Page page, String name) {
        return baseMapper.selectPage(page, new QueryWrapper<DictType>()
                .like(null != name, "name", name));
    }
}
