package com.fns.activiti.process.rest.v1;

import com.fns.activiti.process.rest.v1.request.ProcessRequest;
import com.fns.activiti.process.serviceprovider.ProcessServiceProvider;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/processes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcessResource {

    private final ProcessServiceProvider processServiceProvider;

    @Autowired
    public ProcessResource(ProcessServiceProvider processServiceProvider) {
        this.processServiceProvider = processServiceProvider;
    }

    @GetMapping("/{processId}/tasks")
    public List<ProcessDefinition> getTasks(@PathVariable("processId") String processId) {
        return processServiceProvider.getTasks(processId);
    }

    @PostMapping("/hire")
    public String startTestProcess(@RequestBody ProcessRequest request) {
        return processServiceProvider.startProcess(request);
    }

    @PostMapping("/confirm")
    public List<String> confirmEmail(@RequestBody ProcessRequest request) {
        return processServiceProvider.confirmEmail(request.getEmail());
    }
}
