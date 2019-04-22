package controller;

import common.JsonResult;
import common.Page;
import entity.Menu;
import entity.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        return "/update/password";
    }

    /*
    *验证旧密码是否正确
     */
    @RequestMapping(value = "/oldPwd",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult excuteOldPwd(String userid, String oldPassword, String password, HttpSession httpSession){
        int result = userService.excuteOldPwd(userid,oldPassword);
        if(result>0){
            int result2 = userService.updatePwd(userid,password);
            if(result2>0){
                //User user = (User) httpSession.getAttribute("user");
                httpSession.removeAttribute("user");
                httpSession.removeAttribute("role");
                //修改成功
                return JsonResult.success("密码修改成功");
            }else{
                return JsonResult.error("密码修改失败");
            }
        }else{
            return JsonResult.error("旧密码输入有误");
        }
    }

    /*
    *跳转到个人资料页面
     */
    @GetMapping("/toPersonalInfo")
    public String toPersonal(){
        return "update/info";
    }
    /*
    *分页查询用户列表数据
     */
    @GetMapping("/userList")
    @ResponseBody
    public JsonResult getUserList(@RequestParam int page,@RequestParam int limit){
        //总记录数
        int count = userService.getUserCount();
        Page<User> pageUser = new Page<>();
        pageUser.setTotalCount(count);
        List<User> userList = userService.getUserList(limit,page);
        pageUser.setList(userList);
        return JsonResult.success("",pageUser.getList());
    }

    /**
     * 上传图片
     */
    @PostMapping("/upload")
    @ResponseBody
    public JsonResult uploadFile(HttpServletRequest request,
                                     @RequestParam(value = "file",required = false) MultipartFile attach){
        String idPicPath = null;
        //判断文件是否为空
        if(!attach.isEmpty()){
            String path = request.getSession().getServletContext().getRealPath("statics"+ File.separator+"uploadfiles");
            String OldFileName = attach.getOriginalFilename();//原文件名
            String prefix = FilenameUtils.getExtension(OldFileName);//原文件后缀
            int filesSize = 50000000;
            if(attach.getSize()>filesSize){
                //request.setAttribute("uploadFileError","上传大小不得超过50000KB");
                return JsonResult.error("上传大小不得超过50000KB");
            }else if(prefix.equalsIgnoreCase("jpg")
                    || prefix.equalsIgnoreCase("png")
                    || prefix.equalsIgnoreCase("jpeg")
                    || prefix.equalsIgnoreCase("pneg")){
                String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1,10000)+"_newPerson.jpg";
                File targetFile = new File(path,fileName);
                if(!targetFile.exists()){
                    //创建文件夹
                    targetFile.mkdirs();
                }
                //保存
                try{
                    attach.transferTo(targetFile);
                }catch (Exception e){
                    e.printStackTrace();
                    //request.setAttribute("uploadFileError","上传失败");
                    return JsonResult.error("上传失败");
                }
                idPicPath = path+File.separator+fileName;
            }else{
                //request.setAttribute("uploadFileError","上传图片格式不正确");
                return JsonResult.error("上传图片格式不正确");
            }
        }
        return JsonResult.success("上传成功",idPicPath);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/updateUser")
    @ResponseBody
    public JsonResult updateUserInfo(HttpSession session,User user){
        boolean flag = userService.updateUserInfo(user);
        if(flag){
            //刷新session
            session.setAttribute("user",user);
            return JsonResult.success("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    @RequestMapping("/menu")
    @ResponseBody
    public JsonResult getMenuById(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        String menuIds = userService.getMenuId(user.getUserid());
        String [] menuidArray = menuIds.split(",");

        List<Menu> menuList = userService.getMenuListByID(menuidArray);

        if(menuList!=null&&menuList.size()>0){
            request.getSession().setAttribute("menuList",menuList);
            return JsonResult.success("S");
        }
        return JsonResult.error("E");
    }
}
