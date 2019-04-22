package service;

import entity.Role;
import entity.User;

public interface LoginService {
    /*
     *用户登录
     */
    User login(String userid, String password);

    int login2(User user);

    /*
    *根据用户编号获取角色编号
     */
    Integer getRoleId(String userid);

    /*
     *通过用户编号查询用户角色
     */
    Role getRole(Integer roleid);
}
