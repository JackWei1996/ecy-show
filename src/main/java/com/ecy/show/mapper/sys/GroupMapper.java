package com.ecy.show.mapper.sys;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecy.show.dto.sys.GroupDto;
import com.ecy.show.entity.sys.Group;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 南京白墨科技有限公司
 * @since 2019-09-03
 */
@Repository
public interface GroupMapper extends BaseMapper<Group> {
    /**
     * 批量插入角色的权限
     *
     * @param groupId      groupId
     * @param permissions 权限
     */
    int insertGroupPermission(@Param("groupId") Integer groupId, @Param("permissions") List<Long> permissions);

    /**
     * 将经拥有而修改为不再拥有的权限 deleted改为'1'
     */
    int removeOldPermission(@Param("groupId") Integer groupId, @Param("permissions") List<Long> permissions);
    /**
     * 分组
     */
    GroupDto getPermissionByGroupId(@Param("id") Integer id);
}
