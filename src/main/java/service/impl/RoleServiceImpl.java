package service.impl;

import common.JsonResult;
import common.MyException;
import common.Page;
import entity.Role;
import mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.RoleService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("RoleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRoleList() {
        return roleMapper.getRoleList();
    }

    @Override
    public Page<Role> getRoleListAndCount(Map<String, Object> map) {
        Page<Role> page = new Page<>();
        page.setCurrentPage((int) map.get("currentPage"));
        page.setPageSize((int) map.get("pageSize"));
        page.setList(roleMapper.getRoleList1(map));
        page.setTotalCount(roleMapper.getRoleCount(map));
        return page;
    }

    @Override
    public JsonResult addRole(Role role) {
        if (role != null) {
            int result;
            try {
                result = roleMapper.addRole(role);
            } catch (Exception e) {
                throw new MyException(e, "角色信息新增异常！");
            }
            if (result > 0) {
                return JsonResult.success("添加成功！");
            } else {
                return JsonResult.error("角色信息添加失败，请检查");
            }
        } else {
            return JsonResult.error("要添加的角色信息为空，请检查！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 60)
    public JsonResult deleteRoleByRoleId(List<Role> roles) {
        try {
            if (roles != null && roles.size() > 0) {
                int roleUserCount = roleMapper.getRoleUserCountByRoleId(roles);
                int roleMenuCount = roleMapper.getRoleMenuCountByRoleId(roles);
                if (roleUserCount > 0 && roleMenuCount > 0) {
                    //删除角色用户关联表记录
                    int userResult = roleMapper.deleteRoleUserByRoleId(roles);
                    //删除角色菜单关联表记录
                    int menuResult = roleMapper.deleteRoleMenuByRoleId(roles);
                    //删除角色表记录
                    int roleResult = roleMapper.deleteRoleByRoleId(roles);
                    if (userResult > 0 && menuResult > 0 && roleResult > 0) {
                        return JsonResult.success("角色信息删除成功！");
                    } else {
                        return JsonResult.error("角色信息删除失败，请检查！");
                    }
                } else if (roleUserCount > 0 && roleMenuCount <= 0) {
                    //删除角色用户关联表记录
                    int userResult = roleMapper.deleteRoleUserByRoleId(roles);
                    //删除角色表记录
                    int roleResult = roleMapper.deleteRoleByRoleId(roles);
                    if (userResult > 0 && roleResult > 0) {
                        return JsonResult.success("角色信息删除成功！");
                    } else {
                        return JsonResult.error("角色信息删除失败，请检查！");
                    }
                } else if (roleUserCount <= 0 && roleMenuCount > 0) {
                    //删除角色菜单关联表记录
                    int menuResult = roleMapper.deleteRoleMenuByRoleId(roles);
                    //删除角色表记录
                    int roleResult = roleMapper.deleteRoleByRoleId(roles);
                    if (menuResult > 0 && roleResult > 0) {
                        return JsonResult.success("角色信息删除成功！");
                    } else {
                        return JsonResult.error("角色信息删除失败，请检查！");
                    }
                } else {
                    //删除角色表记录
                    int roleResult = roleMapper.deleteRoleByRoleId(roles);
                    if (roleResult > 0) {
                        return JsonResult.success("角色信息删除成功！");
                    } else {
                        return JsonResult.error("角色信息删除失败，请检查！");
                    }
                }
            } else {
                return JsonResult.error("需要删除的角色为空");
            }
        } catch (Exception e) {
            throw new MyException(e, "角色信息删除异常！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 60)
    public JsonResult updateRole(Role role){
        List<Role> roles = new ArrayList<>();
        try {
            if (role != null) {
                roles.add(role);
                int result = roleMapper.updateRole(role);
                if (result > 0) {
                    if (role.getIsactive() == 0) {
                        //角色未激活（清除该角色下的用户及菜单权限）
                        int userResult = roleMapper.deleteRoleUserByRoleId(roles);
                        int menuResult = roleMapper.deleteRoleMenuByRoleId(roles);
                        if (userResult > 0 && menuResult > 0) {
                            return JsonResult.success("更新角色信息成功！");
                        } else {
                            return JsonResult.error("更新角色信息失败！");
                        }
                    } else {
                        return JsonResult.success("更新角色信息成功！");
                    }
                } else {
                    return JsonResult.error("更新角色信息失败，请检查！");
                }
            } else {
                return JsonResult.error("需要更新的角色信息为空，请检查！");
            }
        } catch (Exception e) {
            throw new MyException(e,"更新角色信息出现异常！");
        }
    }
}
