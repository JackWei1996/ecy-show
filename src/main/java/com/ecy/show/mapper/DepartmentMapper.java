package com.ecy.show.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecy.show.entity.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 南京白墨科技有限公司
 * @since 2019-10-29
 */
@Repository
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> listTree();

    void addPeople(@Param("companyId") Long companyId, @Param("departmentId") Long departmentId);

    void subPeople(@Param("companyId") Long companyId, @Param("departmentId") Long departmentId);
}
