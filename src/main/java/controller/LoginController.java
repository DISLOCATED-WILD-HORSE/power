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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
        if(user!=null){
            user.setIdPicPath(basePath+user.getIdPicPath());
                if(user.isIsdisable()){
                    List<String> menuIds = loginService.getMenuId(user.getUserid());
                    if(menuIds==null){
                        return JsonResult.error("该用户没有被分配任何权限，请联系管理员！");
                    }
                    //去除menuIds重复元素

                    List<String> cl = new ArrayList<>();
                    for (int i =0; i<menuIds.size();i++){
                        String a =menuIds.get(i);
                        String[] b = a.split(",");
                        for(int j =0; j<b.length;j++){
                            cl.add(b[j]);
                        }
                    }

                    HashSet h = new HashSet(cl);
                    cl.clear();
                    cl.addAll(h);

                    request.getSession().setAttribute("menuIds",cl);
                    String roleId = loginService.getRoleId(userid);
                    List<Role> roleList = loginService.getRole(roleId);
                    request.getSession().setAttribute("user",user);
                    request.getSession().setAttribute("role",roleList);
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
