package controller;

import common.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController {
@Resource
private UserService userService;
    /*
     *跳转修改密码弹出页面
     */
    @RequestMapping("/updatePwd")
    public String toUpdatePwd(){
        return "/update/pwd";
    }

    /*
    *验证旧密码是否正确
     */
    @RequestMapping(value = "/oldPwd",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult excuteOldPwd(String userid, String oldPassword, String newPassword){
        int result = userService.excuteOldPwd(userid,oldPassword);
        if(result>0){
            int result2 = userService.updatePwd(userid,newPassword);
            if(result2>0){
                //修改成功
                return JsonResult.success("修改成功");
            }else{
                return JsonResult.error("修改失败");
            }
        }else{
            return JsonResult.error("旧密码输入有误");
        }
    }
}
