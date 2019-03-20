package controller;

import common.JsonResult;
import entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.RegistService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/regist")
public class RegistController {
    @Resource
    private RegistService registService;
    @RequestMapping(value = "/getForm",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult regist(@ModelAttribute User user){
        boolean flag = registService.register(user);
        if(flag){
            return JsonResult.success("注册成功");
        }
        return JsonResult.success("注册失败");
    }
}
