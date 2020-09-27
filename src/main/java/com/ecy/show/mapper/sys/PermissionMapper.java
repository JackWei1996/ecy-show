package com.ecy.show.mapper.sys;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecy.show.entity.sys.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 南京白墨科技有限公司
 * @since 2019-09-03
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    List<JSONObject> listAllPermission();
}
