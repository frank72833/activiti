package com.fns.activiti.process.rest.v1;

import com.fns.activiti.process.rest.v1.request.ProcessRequest;
import com.fns.activiti.process.rest.v1.response.ProcessResponse;
import com.fns.activiti.process.serviceprovider.ProcessServiceProvider;
import com.fns.activiti.process.rest.v1.response.TaskResponse;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/processes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcessResource {

    private final ProcessServiceProvider processServiceProvider;

    @Autowired
    public ProcessResource(ProcessServiceProvider processServiceProvider) {
        this.processServiceProvider = processServiceProvider;
    }

    @GetMapping("/{processId}/tasks")
    public List<TaskResponse> getTasks(@PathVariable("processId") String processId) {
        List<org.activiti.engine.task.Task> tasks = processServiceProvider.getTasks(processId);
        return map(tasks);
    }

    @PostMapping("/test")
    public ProcessResponse startTestProcess(@RequestBody ProcessRequest request) {
        ProcessResponse process = map(processServiceProvider.startProcess(request));
        return process;
    }

    // Simple Mappers
    private List<TaskResponse> map(List<Task> tasks) {
        return tasks.stream()
                .map((task) -> this.map(task))
                .collect(Collectors.toList());
    }

    private TaskResponse map(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .processInstanceId(task.getProcessInstanceId())
                .build();
    }

    private ProcessResponse map(ProcessInstance processInstance) {

        return ProcessResponse.builder()
                .id(processInstance.getId())
                .processDefinitionId(processInstance.getProcessDefinitionId())
                .deploymentId(processInstance.getDeploymentId())
                .localizedName(processInstance.getLocalizedName())
                .startTime(processInstance.getStartTime())
                .tenantId(processInstance.getTenantId())
                .name(processInstance.getName())
                .build();
    }
}
