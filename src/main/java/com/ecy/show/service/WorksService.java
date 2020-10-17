package com.ecy.show.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecy.show.entity.Works;
import com.ecy.show.mapper.WorksMapper;
import com.ecy.show.service.WorksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jack魏
 * @since 2020-10-17
 */
@Service
public class WorksService extends ServiceImpl<WorksMapper, Works>  {

    public IPage search(Page page, Works works) {
        return baseMapper.selectPage(page, new QueryWrapper<Works>()
                .like(StringUtils.isNoneBlank(works.getTitle()), "title", works.getTitle())
                .or().like(StringUtils.isNoneBlank(works.getTitle()), "details", works.getTitle())
                .eq(null != works.getType(), "type", works.getType())
                .eq(null != works.getRoleType(), "role_type", works.getRoleType())
                .eq(null != works.getUserId(), "user_id", works.getUserId())
        );
    }
}
