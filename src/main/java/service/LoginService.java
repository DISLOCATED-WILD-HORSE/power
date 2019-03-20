package service;

import entity.User;

public interface LoginService {
    /*
     *用户登录
     */
    User login(String userid, String password);

    int login2(User user);
}
