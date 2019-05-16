package controller;

import common.JsonResult;
import common.Page;
import entity.Role;
import entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.RoleService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @GetMapping("/getRoleInfo")
    public ModelAndView getRoleInfo(){
        ModelAndView modelAndView = new ModelAndView();
        List<Role> roleList = roleService.getRoleList();
        modelAndView.setViewName("roleform");
        modelAndView.addObject("roleList",roleList);
        return  modelAndView;
    }

    /**
     * 角色管理跳转
     * @return
     */
    @RequestMapping("/toRoleList")
    public String toRoleList(){
        return "roleManager";
    }
    /**
     * 角色管理（获取角色列表）
     * @return
     */
    @RequestMapping("/getRoleList")
    @ResponseBody
    public JsonResult getRoleList(@RequestParam(name = "currentPage",defaultValue = "3") int currentPage,
                                  @RequestParam(name="pageSize",defaultValue = "65") int pageSize,
                                  @RequestParam(name = "roleName",required = false) String roleName,
                                  @RequestParam(name = "isActive",required = false) Integer isActive){
        HashMap<String,Object> map = new HashMap<>();
        map.put("currentPage",currentPage);
        map.put("pageSize",pageSize);
        map.put("roleName",roleName);
        map.put("isActive",isActive);
        Page<Role> roleList = roleService.getRoleListAndCount(map);
        if(roleList!=null&&roleList.getList().size()>0){
            int totalCount = roleList.getTotalCount();
            return JsonResult.success("S",roleList.getList(),totalCount);
        }else{
            return JsonResult.error("角色列表信息为空，请检查！");
        }
    }

    /**
     * 新增角色信息
     * @param request
     * @param roleId
     * @param roleName
     * @param isactive
     * @return
     */
    @PostMapping(value = "/addRole")
    @ResponseBody
    public JsonResult addRoleInfo(HttpServletRequest request, @RequestParam("roleId") String roleId,
                                   @RequestParam("roleName") String roleName,
                                  @RequestParam("isactive") String isactive){
        Role role = new Role();
        role.setRoleId(Integer.parseInt(roleId));
        role.setRoleName(roleName);
        role.setIsactive(Integer.parseInt(isactive));
        User user = (User) request.getSession().getAttribute("user");
        role.setCreateUser(user.getUsername());
        Date nowTime = new Date();
        role.setModifyDate(nowTime);
        JsonResult jsonResult = roleService.addRole(role);
        return jsonResult;
    }

    /**
     * 删除角色信息
     * @param roles
     * @return
     */
    @PostMapping("/delRole")
    @ResponseBody
    public JsonResult deleteRole(@RequestBody List<Role> roles){
        return roleService.deleteRoleByRoleId(roles);
    }

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    @PostMapping("/updateRole")
    @ResponseBody
    public JsonResult updateRole(HttpServletRequest request, @RequestBody Role role){
        User user = (User)request.getSession().getAttribute("user");
        role.setCreateUser(user.getUsername());
        role.setModifyDate(new Date());
        return roleService.updateRole(role);
    }
}
