package service;

import common.JsonResult;
import common.Page;
import entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {
    /**
     * 获取角色信息列表
     * @return
     */
    List<Role> getRoleList();

    /**
     *分页查询获取角色信息列表和总记录数
     * @param map
     * @return
     */
    Page<Role> getRoleListAndCount(Map<String,Object> map);

    /**
     * 新增角色信息
     * @param role
     * @return
     */
    JsonResult addRole(Role role);

    /**
     * 删除角色
     * @param roles
     * @return
     */
    JsonResult deleteRoleByRoleId(List<Role> roles);

    /**
     * 更新角色表
     * @param role
     * @return
     */
    JsonResult updateRole(Role role);
}
