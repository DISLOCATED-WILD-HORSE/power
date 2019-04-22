package service;

import entity.Menu;
import entity.javabean.ParentZtree;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MenuService {
    /**
     * 获取菜单数
     * @return
     */
    List<Menu> getMenuTree(int level,Integer parentId);

    /**
     * 根据角色编号获取菜单编号
     * @param roleId
     * @return
     */
    String getMenuIdByRoleId(Integer roleId);

    /**
     * 根据菜单编号获取菜单列表
     * @param menuIdArrays
     * @return
     */
    List<Menu> getMenuListByMenuID(String[] menuIdArrays);


    boolean mainTainMenuPower(Integer roleId,String menuIds);
}
