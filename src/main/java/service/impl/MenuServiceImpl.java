package service.impl;

import entity.Menu;
import entity.javabean.ParentZtree;
import mapper.MenuMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.MenuService;

import javax.annotation.Resource;
import java.util.List;

@Service("MenuService")
public class MenuServiceImpl implements MenuService {
    private final static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Resource
    private MenuMapper menuMapper;
    @Override
    public List<Menu> getMenuTree(int level,Integer parentId){
        return menuMapper.getMenuList(level,parentId);
    }

    @Override
    public String getMenuIdByRoleId(Integer roleId){
        if(roleId!=null){
            return menuMapper.getMenuIdByRoleId(roleId);
        }
        return null;
    }

    @Override
    public List<Menu> getMenuListByMenuID(String[] menuIdArrays) {
        if(menuIdArrays!=null&&menuIdArrays.length>0){
            return menuMapper.getMenuListByMenuID(menuIdArrays);
        }
        return null;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class,timeout = 10)
    public boolean mainTainMenuPower(Integer roleId,String menuIds){

        try{
            int count = menuMapper.isExsit(roleId);
            if(count>0){
                int result1 = menuMapper.deleteMenuPower(roleId);
                int result2 = 0;
                if(StringUtils.isNotBlank(menuIds)){
                    result2 = menuMapper.addMenuPower(roleId,menuIds);
                }
                if(result1>0&&result2>0){
                    return true;
                }
                return false;
            }else{
                int result2 = menuMapper.addMenuPower(roleId,menuIds);
                return true;
            }
        }catch(Exception ex){
            logger.debug(ex.getMessage());
            return false;
        }
    }
}
