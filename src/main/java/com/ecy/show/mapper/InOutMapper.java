package com.ecy.show.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecy.show.entity.InOut;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 南京白墨科技有限公司
 * @since 2019-12-19
 */
public interface InOutMapper extends BaseMapper<InOut> {

    int countInCars();

    List<InOut> listInCars();
}
