package mapper;

import entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    /**
     * 获取角色信息列表
     * @return
     */
    List<Role> getRoleList();
}
