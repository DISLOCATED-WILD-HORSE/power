package service.impl;

import entity.Role;
import mapper.RoleMapper;
import org.springframework.stereotype.Service;
import service.RoleService;

import javax.annotation.Resource;
import java.util.List;

@Service("RoleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Override
    public List<Role> getRoleList(){
        return roleMapper.getRoleList();
    }
}
