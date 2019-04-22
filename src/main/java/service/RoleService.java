package service;

import entity.Role;

import java.util.List;

public interface RoleService {
    /**
     * 获取角色信息列表
     * @return
     */
    List<Role> getRoleList();
}
