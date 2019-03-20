package service.impl;

import entity.User;
import mapper.RegistMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import service.RegistService;

import javax.annotation.Resource;

@Service("registService")
public class RegistServiceImpl implements RegistService {
    @Resource
    private RegistMapper registMapper;

    @Override
    public boolean register(User user) {
        int result=0;
        if(StringUtils.isNotBlank(user.getUserid())){
            result=registMapper.insertUser(user);
        }
        if(result>0){
            return true;
        }else{
            return false;
        }
    }
}
