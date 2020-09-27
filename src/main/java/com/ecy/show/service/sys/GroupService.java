package com.ecy.show.service.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecy.show.exception.BusinessException;
import com.ecy.show.dto.sys.GroupDto;
import com.ecy.show.dto.sys.group.EditGroupDto;
import com.ecy.show.entity.sys.Group;
import com.ecy.show.entity.sys.GroupPermission;
import com.ecy.show.mapper.sys.GroupMapper;
import com.ecy.show.mapper.sys.GroupPermissionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 南京白墨科技有限公司
 * @since 2019-09-03
 */
@Service
public class GroupService extends ServiceImpl<GroupMapper, Group> {
    private GroupPermissionMapper groupPermissionMapper;

    public GroupService(GroupPermissionMapper groupPermissionMapper) {
        this.groupPermissionMapper = groupPermissionMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveGroup(EditGroupDto editGroupDto) throws Exception {
        Group group=new Group().setIsStatic(0);
        BeanUtils.copyProperties(editGroupDto,group);
        int count = count(new QueryWrapper<Group>().eq("name", group.getName()));
        if (count > 0) {
            throw new BusinessException("添加组别重复");
        }
        save(group);
        if (editGroupDto.getPermissions().size() > 0) {
            baseMapper.insertGroupPermission(group.getId(), editGroupDto.getPermissions());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void editGroup(EditGroupDto editGroupDto) throws Exception {
        Group group=new Group();
        BeanUtils.copyProperties(editGroupDto,group);
        int count = count(new QueryWrapper<Group>().eq("name", group.getName()).ne("id", group.getId()));
        if (count > 0) {
            throw new BusinessException("组别编码不能重复");
        }
        updateById(group);

        QueryWrapper<GroupPermission> queryWrapper = new QueryWrapper<GroupPermission>()
                .select("permission_id")
                .eq("group_id", group.getId());

        List<Long> permissionIds = new ArrayList<>();
        //得到当前分组下的旧的权限
        groupPermissionMapper.selectList(queryWrapper).forEach(r -> permissionIds.add(r.getPermissionId()));

        //添加新权限
        saveNewPermission(editGroupDto, permissionIds);

        //移除旧的不再拥有的权限
        removeOldPermission(editGroupDto, permissionIds);

    }

    public GroupDto getPermissionByGroupId(Integer id) {
        return baseMapper.getPermissionByGroupId(id);
    }

    /**
     * 为分组添加新权限
     */
    private void saveNewPermission(EditGroupDto editGroupDto, List<Long> oldPermissionIds) {
        List<Long> waitInsert = new ArrayList<>();
        for (Long newPermissionId : editGroupDto.getPermissions()) {
            if (!oldPermissionIds.contains(newPermissionId)) {
                waitInsert.add(newPermissionId);
            }
        }
        if (waitInsert.size() > 0) {
            baseMapper.insertGroupPermission(editGroupDto.getId(), waitInsert);
        }
    }

    /**
     * 删除该分组下旧的 不再拥有的权限
     */
    private void removeOldPermission(EditGroupDto group, List<Long> oldPermissionIds) {
        List<Long> waitRemove = new ArrayList<>();
        for (Long oldPerm : oldPermissionIds) {
            if (!group.getPermissions().contains(oldPerm)) {
                waitRemove.add(oldPerm);
            }
        }
        if (waitRemove.size() > 0) {
            baseMapper.removeOldPermission(group.getId(), waitRemove);
        }
    }

}
