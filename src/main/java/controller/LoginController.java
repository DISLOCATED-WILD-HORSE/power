package controller;

import common.JsonResult;
import entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.LoginService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Resource
    private LoginService loginService;

    /*
    *用户登录
     */
    @RequestMapping(value = "/getForm" , method = RequestMethod.POST)
    public String login(Model model, HttpServletRequest request, @RequestParam String userid, @RequestParam String password){
        User user = loginService.login(userid,password);
        if(user!=null){
            if(user.isIsdisable()){
                request.getSession().setAttribute("user",user);
                return "index";
            }else {
                model.addAttribute("error","账号未激活");
                return "forward:/login.jsp";
            }
        }else{
            model.addAttribute("error","用户名或密码不对");
            return "forward:/login.jsp";
        }
    }

    @RequestMapping(value = "/getForm2", method = RequestMethod.POST)
    public String login2(User user){
        int result = loginService.login2(user);
        if(user!=null){
            return "index";
        }
        return "fail";
    }

    /*
    *用户注销
     */
    @RequestMapping(value = "/loginout",method = RequestMethod.GET)
    public String excute(HttpServletRequest request, HttpSession httpSession){
        httpSession.invalidate();
        String path = request.getContextPath();
        // 拼接跳转页面路径
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        System.out.println(basePath);
        return "redirect:"+basePath+"login.jsp";
    }
}
