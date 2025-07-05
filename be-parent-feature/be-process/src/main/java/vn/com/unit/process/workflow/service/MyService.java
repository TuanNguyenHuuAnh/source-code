package vn.com.unit.process.workflow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MyService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

//    @Autowired
//    private DmnRepositoryService dmnRepositoryService;

    @Autowired
    private RepositoryService repositoryService;
    
//    @Autowired
//    private SpringProcessEngineConfiguration processEngineConfiguration;

    public String startProcess(String message, String assignee) {

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("message", message);
        variables.put("person", assignee);

        runtimeService.startProcessInstanceByMessage(message, variables);
        return "Process started";
    }

    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }

    public String deployProcess() {
        String res = "deploy successful!";
        try {
            repositoryService.createDeployment().addClasspathResource("BPMN/CommonEventListener2.bpmn").deploy();
//            Deployment deployment = repositoryService.createDeployment().addClasspathResource("dmn/DmnProcess.bpmn").deploy();
//            dmnRepositoryService.createDeployment().parentDeploymentId(deployment.getId()).addClasspathResource("dmn/KhuongTH.dmn").deploy();
        } catch (Exception e) {
            res = "deploy failed!";
        }
        return res;
    }

    public String startProcessByKey(String processDefinitionKey, String key, String assignee) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee", assignee);
        variables.put("key", key);

        String processInstant = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables).getName();
        return "Process " + processInstant + " started";
    }

    public void trigger(String executionId, Integer message) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee", "abc");
        variables.put("message", message);
        runtimeService.messageEventReceived("externalMesage", executionId, variables);
    }

    public void print(DelegateExecution delegateExecution) {
        System.out.println("excutionID:" + delegateExecution.getId());
    }
}