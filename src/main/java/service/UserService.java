package service;

import entity.Menu;
import entity.User;

import java.util.List;

public interface UserService {
    /*
    *验证旧密码是否正确
     */
    int excuteOldPwd(String userid, String oldPassword);
    /*
    *修改密码
     */
    int updatePwd(String userid,String newPassword);

    /*
    *查询用户表总记录数
     */
    int getUserCount();

    /*
     *分页查询用户列表信息
     */
    List<User> getUserList(int pageSize, int currentPage);

    /*
    *修改用户表信息
     */
    boolean updateUserInfo(User user);

    /**
     * 根据菜单ID列表查询菜单列表
     * @param menuIdArray
     * @return
     */
    List<Menu> getMenuListByID(String [] menuIdArray);
}
