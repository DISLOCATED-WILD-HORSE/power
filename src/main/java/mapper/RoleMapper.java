package mapper;

import entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    /**
     * 获取角色信息列表
     * @return
     */
    List<Role> getRoleList();

    /**
     * 分页查询获取角色信息列表
     * @param map
     * @return
     */
    List<Role> getRoleList1(Map<String,Object> map);

    /**
     * 分页查询根据查询条件获取总记录数
     * @param map
     * @return
     */
    int getRoleCount(Map<String,Object> map);

    /**
     * 新增角色信息
     * @param role
     * @return
     */
    int addRole(Role role);

    /**
     * 删除角色信息
     * @param roles
     * @return
     */
    int deleteRoleByRoleId(@Param("roles") List<Role> roles);

    /**
     * 删除角色前查看该角色是否存在角色用户表
     * @param roles
     * @return
     */
    int getRoleUserCountByRoleId(@Param("roles") List<Role> roles);

    /**
     * 删除角色前查看该角色是否存在角色菜单表
     * @param roles
     * @return
     */
    int getRoleMenuCountByRoleId(@Param("roles") List<Role> roles);

    /**
     * 根据角色ID删除角色用户表记录
     * @param roles
     * @return
     */
    int deleteRoleUserByRoleId(@Param("roles") List<Role> roles);

    /**
     * 根据角色ID删除角色菜单表记录
     * @param roles
     * @return
     */
    int deleteRoleMenuByRoleId(@Param("roles") List<Role> roles);

    /**
     * 更新角色表
     * @param role
     * @return
     */
    int updateRole(@Param("role") Role role);

    /**
     * 根据用户编号查看所在角色的信息
     * @param userId
     * @return
     */
    Role getRoleByUserId(String userId);
}
