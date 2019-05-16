package mapper;

import entity.Role;
import entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginMapper {
    /*
    *通过用户编号和密码查询用户
     */
    User getUser(@Param("userid") String userid,@Param("password") String password);
    /*
    *通过用户编号和密码查询用户是否存在
     */
    int getCount(User user);

    /*
    *通过用户编号获取角色编号
     */
    String getRoleId(String userid);
    /*
    *通过角色编号查询用户角色
     */
    List<Role> getRole(@Param("roleid") List<String> roleid);

    /**
     * 根据登录的用户编号获取菜单编号列表
     */
    String getMenuId(String userid);

    /**
     * 根据用户编号获取角色用户关联表的角色编号
     * @param UserId
     * @return
     */
    String getRoleIdByUserId(String UserId);

    /**
     * 根据角色编号获取菜单编号
     * @param roleId
     * @return
     */
    List<String> getMenuIdByRoleId(@Param("roleId") String [] roleId);
}
