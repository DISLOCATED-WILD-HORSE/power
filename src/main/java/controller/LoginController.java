package controller;

import common.JsonResult;
import entity.Role;
import entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    *用户登录(返回前段页面JSON数据)
     */
    @RequestMapping(value = "/loginIn" , method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(Model model, HttpServletRequest request, @RequestParam String userid, @RequestParam String password){
        User user = loginService.login(userid,password);
        String basePath = "http://localhost:8080";
        user.setIdPicPath(basePath+user.getIdPicPath());
        if(user!=null){
            if(user.isIsdisable()){
                Integer roleId = loginService.getRoleId(userid);
                Role role = loginService.getRole(roleId);
                request.getSession().setAttribute("user",user);
                request.getSession().setAttribute("role",role);
                return JsonResult.success("登入成功");
            }else {
                //model.addAttribute("error","账号未激活");
                return JsonResult.error("账号未激活");
            }
        }else{
            //model.addAttribute("error","用户名或密码不对");
            return JsonResult.error("用户名或密码不对");
        }
    }

    /*
    *登录成功后页面跳转
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "index1";
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
        return "redirect:"+basePath+"login1.jsp";
    }


    @GetMapping("/test")
    public JsonResult test(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user!=null){
            return JsonResult.success("");
        }
        return null;
    }
}
