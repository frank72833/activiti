package com.fns.activiti.process.serviceprovider;

import com.fns.activiti.process.rest.v1.request.ProcessRequest;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProcessServiceProvider {

    private final RuntimeService runtimeService;
    private final TaskService taskService;

    public ProcessServiceProvider(RuntimeService runtimeService, TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    public ProcessInstance startProcess(ProcessRequest request) {
        log.info("startTestProcess: {}", request);
        ProcessInstance process = runtimeService.startProcessInstanceByKey("TestProcess");

        return process;
    }

    public List<Task> getTasks(String processId) {
        return taskService.createTaskQuery().processInstanceId(processId).list();
    }
}
