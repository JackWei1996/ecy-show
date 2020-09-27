package com.ecy.show.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecy.show.entity.Department;
import com.ecy.show.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 南京白墨科技有限公司
 * @since 2019-10-29
 */
@Service
public class DepartmentService extends ServiceImpl<DepartmentMapper, Department> {

    public List<Department> listTree() {
        return baseMapper.listTree();
    }

    public void addPeoples(Long companyId, Long departmentId) {
        baseMapper.addPeople(companyId, departmentId);
    }

    public void subPeople(Long companyId, Long departmentId) {
        baseMapper.subPeople(companyId, departmentId);
    }
}
