package com.fns.activiti.process.serviceprovider;

import com.fns.activiti.process.rest.v1.request.ProcessRequest;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProcessServiceProvider {

    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;
    private final TaskService taskService;

    @Autowired
    public ProcessServiceProvider(RepositoryService repositoryService, RuntimeService runtimeService, TaskService taskService) {
        this.repositoryService = repositoryService;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    public String startProcess(ProcessRequest request) {
        log.info("startTestProcess: {}", request);

        Map<String, Object> variables = new HashMap<>();
        variables.put("name", request.getApplicantName());
        variables.put("email", request.getEmail());

        repositoryService.createProcessDefinitionQuery().list().stream().forEach((p) -> log.info("Process: {}", p));

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("signupProcess", variables);

        return processInstance.getProcessInstanceId();
    }

    public List<String> confirmEmail(String email) {
        log.info("confirmEmail: {}", email);

        List<Task> tasks = taskService.createTaskQuery()
                .taskDefinitionKey("confirm-email-task")
                .includeProcessVariables()
                .processVariableValueEquals("email", email)
                .list();

        log.info("tasks size: {}", tasks.size());

        tasks.stream().forEach((task) -> {
            taskService.claim(task.getId(), "fran");
            taskService.complete(task.getId());
        });

        return tasks.stream().map((t) -> t.getTaskDefinitionId()).collect(Collectors.toList());
    }

    public List<ProcessDefinition> getTasks(String processId) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(processId).list();
    }
}
