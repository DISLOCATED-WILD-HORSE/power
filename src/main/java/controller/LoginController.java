package controller;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.LoginService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class LoginController {
    @Resource
    private LoginService loginService;
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/getForm" , method = RequestMethod.POST)
    public String login(@RequestParam String userid,@RequestParam String password){
        User user = loginService.login(userid,password);
        if(user!=null){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("user",user);
            modelAndView.setViewName("index");
            return "index";
        }
        return "fail";
    }

    @RequestMapping(value = "/getForm2", method = RequestMethod.POST)
    public String login2(User user){
        int result = loginService.login2(user);
        if(user!=null){
            return "index";
        }
        return "fail";
    }
}
