package mapper;

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
}
