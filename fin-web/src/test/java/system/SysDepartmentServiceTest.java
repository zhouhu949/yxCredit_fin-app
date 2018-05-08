package system;

import com.zw.rule.pcd.service.IPCDLinkedService;
import com.zw.rule.po.SysDepartment;
import com.zw.rule.service.SysDepartmentService;
import com.zw.rule.service.SysOrganizationService;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/13.
 */
public class SysDepartmentServiceTest {

    ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath*:/spring/spring-context.xml");
    SysDepartmentService sysDepartmentService = (SysDepartmentService) cxt.getBean("sysDepartmentServiceImpl");
    /*
    *组织管理
    * */
    @Test
    public void findAllDepts() throws Exception {
        Map param=new HashedMap();
        param.put("status",0);
        List list=sysDepartmentService.findAllDepts(param);
        System.out.println(list.get(0));
    }

    /**
     * 添加部门
     * @throws Exception
     */
   /* @Test
    public void saveDepartment() throws Exception {
        SysDepartment sysDepartment=new SysDepartment();
        sysDepartment.setType(1);
        sysDepartment.setName("测试55");
        sysDepartment.setPid("000000005167acb7015167ed732d0009");
        sysDepartment.setDescription("cecece3");
        boolean flag=sysDepartmentService.saveDepartment(sysDepartment);
        System.out.println(flag);
    }*/
    /**
     * 修改部门
     * @throws Exception
     */
   /* @Test
    public void updateDepartment() throws Exception {
        SysDepartment sysDepartment=new SysDepartment();
        sysDepartment.setId("000000005166ec3a01516709c0840000");
        sysDepartment.setType(1);
        boolean flag=sysDepartmentService.updateDepartment(sysDepartment);
        System.out.println(flag);
    }*/

    IPCDLinkedService iPCDLinkedService = (IPCDLinkedService) cxt.getBean("iPCDLinkedServiceImpl");
    @Test
    public void getCity() throws Exception {

        String parentid="330100";
        String flag="city";
        Map map =new HashedMap();
        map.put("flag","province");
        map.put("parentId","330000");
        List map1=iPCDLinkedService.getPCD(map);
        System.out.println(map1);
    }
}
