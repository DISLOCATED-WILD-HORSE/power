package mapper;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /*
    *根据用户id和密码查看是否存在用户
     */
    int getCount(@Param("userid") String userid,@Param("oldPassword") String oldPassword);
    /*
    *修改密码
     */
    int updatePwd(@Param("userid") String userid,@Param("newPassword") String newPassword);
}
