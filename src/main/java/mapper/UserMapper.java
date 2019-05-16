package mapper;

import entity.Menu;
import entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    /*
    *根据用户id和密码查看是否存在用户
     */
    int getCount(@Param("userid") String userid,@Param("oldPassword") String oldPassword);
    /*
    *修改密码
     */
    int updatePwd(@Param("userid") String userid,@Param("newPassword") String newPassword);

    /*
    *查询用户表总记录数
     */
    int getUserCount();
    /*
    *分页查询用户列表信息
     */
    List<User> getUserList(@Param("pageSize") int pageSize, @Param("currentPage") int currentPage);

    /*
    *修改用户表
     */
    int updateUser(User user);

    /**
     * 根据菜单ID列表查询菜单列表
     * @param menuIdArray
     * @return
     */
    List<Menu> getMenuListByID(String [] menuIdArray);
}
