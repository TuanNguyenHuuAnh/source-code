package vn.com.unit.process.workflow.controller;

import java.util.List;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.com.unit.process.workflow.service.MyService;

@RestController
public class MyController {

    @Autowired
    private MyService myService;

    @RequestMapping(value = "/startprocess/{message}")
    public String startProcessInstance(@PathVariable("message") String message, @RequestParam String assignee) {
        return myService.startProcess(message, assignee);
    }

    @RequestMapping(value = "/tasks/{assignee}")
    public String getTasks(@PathVariable("assignee") String assignee) {
        List<Task> tasks = myService.getTasks(assignee);
        tasks.get(0).getId();
        tasks.get(0).getExecutionId();
        return tasks.toString();
    }

    @RequestMapping(value = "/completetask")
    public String completeTask(@RequestParam String id) {
        myService.completeTask(id);
        return "Task with id " + id + " has been completed!";
    }

    @RequestMapping(value = "/KhuongTH/startprocess/{key}")
    public String startProcessByKey(@PathVariable("key") String key, @RequestParam String assignee) {
        return myService.startProcessByKey("CommonEventListenerNew", key, assignee);
    }

    @RequestMapping(value = "/KhuongTH/deploy")
    public String deployProcess() {
        return myService.deployProcess();
    }

    @RequestMapping(value = "/trigger/{message}")
    public String trigger(@RequestParam String id, @PathVariable("message") Integer message) {
        myService.trigger(id, message);
        return "Execution with id " + id + " has been trigger!";
    }
}
