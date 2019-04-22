package mapper;

import entity.Role;
import entity.User;
import org.apache.ibatis.annotations.Param;

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
    Integer getRoleId(String userid);
    /*
    *通过角色编号查询用户角色
     */
    Role getRole(@Param("roleid") int roleid);
}
