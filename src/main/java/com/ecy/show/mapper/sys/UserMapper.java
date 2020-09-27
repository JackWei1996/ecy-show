package com.ecy.show.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ecy.show.dto.sys.UserSearchDto;
import com.ecy.show.entity.sys.Permission;
import com.ecy.show.entity.sys.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 南京白墨科技有限公司
 * @since 2019-09-03
 */
@Repository
public interface UserMapper extends BaseMapper<User> {


    List<Permission>getUserPermissionsSet(@Param("groupId") Integer groupId);

    User getUserByNickname(@Param("nickname") String nickname, @Param("userId") Long userId);

    User getUserInfoById(@Param("userId") Long userId, @Param("currentId") Long currentId);

    IPage adminPageUser(@Param("page") UserSearchDto searchDto);

    List<User> listStaff();
}
