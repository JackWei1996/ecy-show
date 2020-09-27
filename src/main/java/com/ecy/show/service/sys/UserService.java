package com.ecy.show.service.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecy.show.exception.BusinessException;
import com.ecy.show.dto.sys.*;
import com.ecy.show.entity.Department;
import com.ecy.show.entity.sys.Group;
import com.ecy.show.entity.sys.User;
import com.ecy.show.mapper.DepartmentMapper;
import com.ecy.show.mapper.sys.GroupMapper;
import com.ecy.show.mapper.sys.UserMapper;
import com.ecy.show.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 南京白墨科技有限公司
 * @since 2019-09-03
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    private GroupMapper groupMapper;
    private DepartmentMapper departmentMapper;

    public UserService(GroupMapper groupMapper, TicketService ticketService,
                       DepartmentMapper departmentMapper) {
        this.groupMapper = groupMapper;
        this.departmentMapper = departmentMapper;
    }

    /**
     * @Description 得到用户信息
     */
    public CurrentUser getLoginInfo() throws BusinessException {
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getPrincipal();

        if (null != currentUser.getCompanyId()) {
            Department company = departmentMapper.selectById(currentUser.getCompanyId());
            currentUser.setCompanyName(company.getName());
            Department dep = departmentMapper.selectById(currentUser.getDepartmentId());
            currentUser.setDepartmentName(dep.getName());
        }

        GroupDto groupDto = groupMapper.getPermissionByGroupId(currentUser.getGroupId());
        if (groupDto == null ) {
            groupDto=new GroupDto();
        }

        Group group = groupMapper.selectById(currentUser.getGroupId());
        if (group != null) {
            groupDto.setName(group.getName());
        }

        currentUser.setGroupDto(groupDto);

        return currentUser;
    }

    /**
     * @Description 屏蔽用户隐蔽信息
     */
    private User getUser(User user) {
        User userInfo = new User();
        BeanUtils.copyProperties(userInfo, user);
        userInfo.setOpenid(null);
        userInfo.setAccessToken(null);
        userInfo.setRefreshToken(null);
        userInfo.setIdCard(null);
        userInfo.setPwd(null);
        return userInfo;
    }

    /**
     * @Description 管理员删除用户
     */
    public void admin2LockUser(Long userId) {
        removeById(userId);
    }

    /**
     * @Description: 管理员根据条件查询用户
     */
    public IPage adminPageUser(UserSearchDto searchDto) {
        return baseMapper.adminPageUser(searchDto);
    }

    /**
     * @Description: 管理员批量删除用户
     */
    public void admin2BatchLockUser(Long[] userIds, int key) {
        removeByIds(Arrays.asList(userIds));
    }

    public void assignUserGroup(AssignUserGroupDto assignUserGroupDto) {
        User user = new User().setId(assignUserGroupDto.getId()).setGroupId(assignUserGroupDto.getGroupId());
        updateById(user);
    }

    public void updateUser(EditUserDto editUserDto) {
        User user = new User();
        BeanUtils.copyProperties(editUserDto, user);
        Long userId = JwtUtil.getUserId();
        user.setId(userId);
        updateById(user);
    }

    /**
     * 根据用户id得到用户分组编码。目前用户只能有一个分组
     */
    public Set<String> getUserGroupSet(Long userId) {
        Group group = groupMapper.selectById(baseMapper.selectById(userId).getGroupId());
        return new HashSet<String>(1) {{
            add(group.getName());
        }};
    }

    /**
     * 根据用户id得到用户权限编码数组
     */
    public Set<String> getUserPermissionsSet(Long userId) {
        User user = baseMapper.selectById(userId);
        Integer groupId = user.getGroupId();

        Set<String> hashSet = new HashSet<>();
        baseMapper.getUserPermissionsSet(groupId).forEach(r -> {
            hashSet.add(r.getPermissionCode());
        });
        return hashSet;
    }


    public User getUserInfoById(Long userId, Long currentId) throws BusinessException {
        User user = baseMapper.getUserInfoById(userId, currentId);
        try {
            if (user.getDeleted().equals(User.DELETED)) {
                throw new BusinessException("该用户已注销");
            }
            user.setDeleted(null);
        } catch (Exception e) {
            throw new BusinessException("未找到该用户");
        }
        return user;
    }

    /*
     * 根据旧密码修改用户密码
     * */
    public void updatePwd(Long userId, ResetPwdDto resetPwdDto) throws Exception {
        User user = getById(userId);
        if (null != user.getPwd() && null == resetPwdDto.getOldPwd()) {
            throw new BusinessException("请填写旧密码");
        }
        if (user.getPwd().equals(DigestUtils.md5DigestAsHex(resetPwdDto.getOldPwd().getBytes("UTF-8")))) {
            User newUser = new User();
            newUser.setId(userId)
                    .setPwd(DigestUtils.md5DigestAsHex(resetPwdDto.getNewPwd().getBytes("UTF-8")));
            updateById(newUser);
        } else {
            throw new BusinessException("旧密码错误");
        }
    }

    public void updateName(Long userId, String newName) {
        User user = new User()
                .setId(userId)
                .setName(newName)
                .setGmtModified(LocalDateTime.now());

        updateById(user);
    }

}