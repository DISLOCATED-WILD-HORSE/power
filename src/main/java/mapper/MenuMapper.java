package mapper;

import entity.Menu;
import entity.javabean.ParentZtree;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MenuMapper {
    /**
     * 获取菜单数
     * @return
     */
    List<Menu> getMenuList(@Param("level") int level,@Param("parentId") Integer parentId);

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

    /**
     * 根据角色ID删除角色菜单权限
     * @return
     */
    int deleteMenuPower(Integer roleId);

    /**
     * 根据角色和菜单ID新增角色菜单权限
     * @param
     * @return
     */
    int addMenuPower(@Param("roleId") Integer roleId,@Param("menuId") String menuIds);

    /**
     * 查询角色是否有权限
     * @param roleId
     * @return
     */
    int isExsit(Integer roleId);

}
