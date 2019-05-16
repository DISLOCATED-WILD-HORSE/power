package service.impl;

import entity.Role;
import entity.User;
import mapper.LoginMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import service.LoginService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String getRoleId(String userid){
        String result="";
        if(StringUtils.isNotBlank(userid)){
            result = loginMapper.getRoleId(userid);
        }
        return result;
    }

    @Override
    public List<Role> getRole(String roleid){
        List<String> roleIds = new ArrayList<>();
        String [] roleIdArray = roleid.split(",");
        for (String item : roleIdArray){
            roleIds.add(item);
        }
        List<Role> roleList = new ArrayList<>();
        if(roleid!=null){
            roleList = loginMapper.getRole(roleIds);
        }
        return roleList;
    }

    @Override
    public List<String> getMenuId(String userid){
        if(StringUtils.isNotBlank(userid)){
            String roleIds = loginMapper.getRoleIdByUserId(userid);
            String [] roleIdArray = new String[1000];
            if(roleIds.contains(",")){
                roleIdArray = roleIds.split(",");
            }else{
                roleIdArray[0]=roleIds;
            }
            return loginMapper.getMenuIdByRoleId(roleIdArray);
        }
        return null;
    }
}
