package FlowCom;

import com.zw.rule.task.po.TaskOperationRecord;
import com.zw.rule.task.service.ProcessTaskService;
import com.zw.rule.task.service.TaskOperationRecordService;
import com.zw.rule.util.flow.service.FlowComService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月26日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class FlowComTest {
    ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath*:/spring/spring-context.xml");
    /**
     * relId,userId
     * 根据关联id查找流程并往任务表和任务节点表(插入当前流程的开始节点连线的下一节点，作为初始任务)插入相应数据
     */
//    @Test
//    public void getProcessByProId() throws Exception {
//        FlowComService flowComService = (FlowComService)cxt.getBean("flowComServiceImpl");
//        flowComService.getProcessByProId("1fe137c8-1350-479b-b11a-a7413d2438d2",1L);
//    }


    /**
     * 测试提交任务
     * 包含参数       status 状态 必需
     *              taskNodeId 当前节点任务ID 必需
     *              userId 必需
     *              relId 总任务id 可选（如果是最后一个节点就必需要有，type有的话或者状态为3）
     * @return
     */
    @Test
    public void CommitTask() throws Exception {
        FlowComService flowComService = (FlowComService)cxt.getBean("flowComServiceImpl");
        //flowComService.CommitTask("88270da8-8ffb-438d-bf44-cd7151de340c",1L,3,"63d4a08b-06f8-4f55-a919-27a007cd739a");
    }

    /**
     * 测试任务转寄
     * 包含以下参数
     * userId  用户id 必需
     * taskNodeId 任务节点表主键 必需
     * nowUserId   当前操作用户id 必需
     */
    @Test
    public void changeTask() throws Exception {
        ProcessTaskService processTaskService = (ProcessTaskService)cxt.getBean("processTaskServiceImpl");
        TaskOperationRecordService taskOperationRecordService = (TaskOperationRecordService)cxt.getBean("taskOperationRecordServiceImpl");

//        FlowCom flowCom = new FlowCom();
        Map<String,Object> param = new HashMap();
        param.put("nowUserId",1);
        param.put("userId","102");
        param.put("taskNodeId","5bc3920f-36c6-42a7-9d80-284ff4701d44");
//        flowCom.changeTask(param,processTaskService,taskOperationRecordService);
    }

    /**
     * 测试任务退回
     * taskNodeId 任务节点表主键 必需
     * userId  用户id 必需
     */
    @Test
    public void exitTask() throws Exception {
        ProcessTaskService processTaskService = (ProcessTaskService)cxt.getBean("processTaskServiceImpl");
        TaskOperationRecordService taskOperationRecordService = (TaskOperationRecordService)cxt.getBean("taskOperationRecordServiceImpl");
        Map<String,Object> param = new HashMap();
//        FlowCom flowCom = new FlowCom();
        param.put("userId","1");
        String taskNodeId = "5bc3920f-36c6-42a7-9d80-284ff4701d44";
        param.put("taskNodeId",taskNodeId);
//        flowCom.exitTask(param,processTaskService,taskOperationRecordService);
    }


    /**
     * 测试更新记录表
     */

    @Test
    public void testTasKOperationRecord() throws Exception {
        TaskOperationRecordService taskOperationRecordService = (TaskOperationRecordService)cxt.getBean("taskOperationRecordServiceImpl");
//        FlowCom flowCom = new FlowCom();
        String taskNodeId = "5bc3920f-36c6-42a7-9d80-284ff4701d44";
        TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
        Long nowUserId = 1L;
        Long userId = 2L;
        taskOperationRecord.setId(UUID.randomUUID().toString());
        taskOperationRecord.setTaskNodeId(taskNodeId);
        taskOperationRecord.setUserId(nowUserId);
        taskOperationRecord.setStatus("1");
        taskOperationRecord.setMsg("任务转寄到"+userId+"，更新process_task_node表");
        taskOperationRecord.setType("2");
        int num = taskOperationRecordService.insertTaskOperationRecord(taskOperationRecord);
        System.out.println(num);
    }




}

