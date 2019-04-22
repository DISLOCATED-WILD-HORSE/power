package controller;

import common.JsonResult;
import entity.Menu;
import entity.Role;
import entity.Role_Menu;
import entity.javabean.ChildrenZtree;
import entity.javabean.ParentZtree;
import entity.javabean.ThirdChildrenTree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.MenuService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    @GetMapping("/getMenu")
    @ResponseBody
    public JsonResult getMenuTree(@RequestParam("roleId") String roleId){
        //获取一级菜单列表
        List<Menu> menuList1 = menuService.getMenuTree(1,0);
        //申明一级菜单集合
        List<ParentZtree> parentZtreeList = new ArrayList<>();


        //根据角色编号获取菜单数据列表
        Integer roleID = Integer.parseInt(roleId);
        String menuIds = menuService.getMenuIdByRoleId(roleID);
        List<Menu> menuInfoList = new ArrayList<>();
        if(StringUtils.isNotBlank(menuIds)){
            String[] menuIdArrays = menuIds.split(",");
            menuInfoList = menuService.getMenuListByMenuID(menuIdArrays);
        }
        if(menuList1.size()>0){
            for (Menu item : menuList1){



                ParentZtree parentZtree = new ParentZtree();

                for (Menu m : menuInfoList){
                    if(m.getMenuId()==item.getMenuId()){
                        parentZtree.setChecked(true);
                        break;
                    }
                }

                parentZtree.setId(item.getMenuId());
                parentZtree.setPid(item.getParentId());
                parentZtree.setName(item.getMenuName());

                //获取二级菜单数据列表
                List<Menu> menuList2 = menuService.getMenuTree(2,item.getMenuId());
                //申明二级菜单
                List<ChildrenZtree> childrenZtreeList = new ArrayList<>();
                if(menuList2.size()>0){
                    for (Menu item2 : menuList2){
                        ChildrenZtree childrenZtree = new ChildrenZtree();

                        for (Menu m : menuInfoList){
                            if(m.getMenuId()==item2.getMenuId()){
                                childrenZtree.setChecked(true);
                                break;
                            }
                        }

                        childrenZtree.setId(item2.getMenuId());
                        childrenZtree.setPid(item2.getParentId());
                        childrenZtree.setName(item2.getMenuName());

                        //获取三级菜单数据列表
                        List<Menu> menuList3 = menuService.getMenuTree(3,item2.getMenuId());
                        //申明三级菜单
                        List<ThirdChildrenTree> thirdChildrenTreeList = new ArrayList<>();
                        if(menuList3.size()>0){
                            for (Menu item3 : menuList3){
                                ThirdChildrenTree thirdChildrenTree = new ThirdChildrenTree();
                                for (Menu m : menuInfoList){
                                    if(m.getMenuId()==item3.getMenuId()){
                                        thirdChildrenTree.setChecked(true);
                                        break;
                                    }
                                }

                                thirdChildrenTree.setId(item3.getMenuId());
                                thirdChildrenTree.setPid(item3.getParentId());
                                thirdChildrenTree.setName(item3.getMenuName());
                                thirdChildrenTreeList.add(thirdChildrenTree);
                            }
                        }
                        //如果有三级菜单就加入二级菜单对象集合
                        if(thirdChildrenTreeList.size()>0){
                            childrenZtree.setChildren(thirdChildrenTreeList);
                        }
                        childrenZtreeList.add(childrenZtree);
                    }
                }
                //如果有二级菜单就加入一级菜单对象集合
                if(childrenZtreeList.size()>0){
                    parentZtree.setChildren(childrenZtreeList);
                }
                parentZtreeList.add(parentZtree);
            }
        }
        return JsonResult.success("S",parentZtreeList);
    }

    @PostMapping("/maimTain")
    @ResponseBody
    public JsonResult mainTainMenuPower(@RequestParam("roleId") Integer roleId, @RequestBody List<ParentZtree> selectNodes){
        String menuIds="";
        for (ParentZtree item : selectNodes){
            menuIds += item.getId()+",";
        }
        boolean flag = menuService.mainTainMenuPower(roleId,menuIds);
        if(flag){
            return JsonResult.success("S");
        }
        return JsonResult.error("E");
    }
}
