package com.ecy.show.service.sys;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecy.show.entity.sys.Permission;
import com.ecy.show.mapper.sys.PermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *
 * @author 南京白墨科技有限公司
 * @since 2019-09-03
 */
@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {

    public List<JSONObject> listAllPermission() {
        List<JSONObject> permissions = baseMapper.listAllPermission();
        return permissions;
    }
}
