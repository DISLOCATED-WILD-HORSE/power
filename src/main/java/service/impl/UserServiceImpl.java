package service.impl;

import mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import service.UserService;

import javax.annotation.Resource;

@Service("UserService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public int excuteOldPwd(String userid,String oldPassword){
        int result=0;
        if(StringUtils.isNotBlank(userid)&&StringUtils.isNotBlank(oldPassword)){
            result = userMapper.getCount(userid,oldPassword);
        }
        return result;
    }
    @Override
    public int updatePwd(String userid,String newPassword){
        int result=0;
        if(StringUtils.isNotBlank(userid)&&StringUtils.isNotBlank(newPassword)){
            result = userMapper.updatePwd(userid,newPassword);
        }
        return result;
    }
}
