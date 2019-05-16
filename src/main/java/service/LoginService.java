package service;

import entity.Role;
import entity.User;

import java.util.List;

public interface LoginService {
    /*
     *用户登录
     */
    User login(String userid, String password);

    int login2(User user);

    /*
    *根据用户编号获取角色编号
     */
    String getRoleId(String userid);

    /*
     *通过用户编号查询用户角色
     */
    List<Role> getRole(String roleid);

    /**
     * 根据登录的用户编号获取菜单编号列表
     */
    List<String> getMenuId(String userid);
}
