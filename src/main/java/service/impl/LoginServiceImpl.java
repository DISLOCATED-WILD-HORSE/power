package service.impl;

import entity.User;
import mapper.LoginMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.LoginService;

import javax.annotation.Resource;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    @Resource
    private LoginMapper loginMapper;
    @Override
    public User login(String userid,String password){
        User user = new User();
        if(StringUtils.isNotBlank(userid) && StringUtils.isNotBlank(password)){
            user = loginMapper.getUser(userid,password);
        }
        return user;
    }

    @Override
    public int login2(User user) {
        int result = 0;
        if(user!=null){
            result = loginMapper.getCount(user);
        }
        return result;
    }
}
