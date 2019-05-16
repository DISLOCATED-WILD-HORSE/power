
import common.Page;
import entity.Role;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.RoleService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/aplicationConfig.xml")
public class Test {
@Resource
private RoleService roleService;
    // Logger和LoggerFactory导入的是org.slf4j包
//    private final static Logger logger = LogManager.getLogger(Test.class);
    private final static Logger logger = LoggerFactory.getLogger(Test.class);
    public static void main(String[] args) {


    }
    @org.junit.Test
    public void test(){
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("currentPage", 1);
        hashMap.put("pageSize", 2);
        hashMap.put("roleName","消");
        hashMap.put("isactive",1);
        Page<Role> roleList = roleService.getRoleListAndCount(hashMap);
        for (Role item : roleList.getList()){
            System.out.println(item.getRoleId()+","+item.getRoleName());
        }
    }
}
