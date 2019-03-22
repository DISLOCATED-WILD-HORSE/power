package service;

import org.apache.ibatis.annotations.Param;

public interface UserService {
    /*
    *验证旧密码是否正确
     */
    int excuteOldPwd(String userid, String oldPassword);
    /*
    *修改密码
     */
    int updatePwd(String userid,String newPassword);
}
