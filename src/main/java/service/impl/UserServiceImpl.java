package service.impl;

import entity.Menu;
import entity.User;
import mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import service.UserService;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public int getUserCount(){
        return userMapper.getUserCount();
    }

    @Override
    public List<User> getUserList(int pageSize, int currentPage){
        return userMapper.getUserList(pageSize,currentPage);
    }

    @Override
    public boolean updateUserInfo(User user){
        int result = 0;
        if(StringUtils.isNotBlank(user.getUserid())){
            result = userMapper.updateUser(user);
        }
        if(result>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String getMenuId(String userid){
        if(StringUtils.isNotBlank(userid)){
            return userMapper.getMenuId(userid);
        }
        return null;
    }

    @Override
    public List<Menu> getMenuListByID(String[] menuIdArray){
        if(menuIdArray!=null&&menuIdArray.length>0){
            return userMapper.getMenuListByID(menuIdArray);
        }
        return null;
    }
}
