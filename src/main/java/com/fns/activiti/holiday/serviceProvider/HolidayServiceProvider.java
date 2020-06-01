package com.fns.activiti.holiday.serviceProvider;

import com.fns.activiti.holiday.Holiday;
import com.fns.activiti.holiday.HolidayService;
import com.fns.activiti.holiday.rest.v1.request.HolidayRequest;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HolidayServiceProvider {

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final HolidayService holidayService;

    @Autowired
    public HolidayServiceProvider(RuntimeService runtimeService, TaskService taskService, HolidayService holidayService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.holidayService = holidayService;
    }

    @Transactional
    public String requestHoliday(String userId, HolidayRequest req) {
        log.info("requestHoliday userId, {}, {}", userId, req);

        Map<String, Object> vars = new HashMap<>();
        vars.put("startDate", req.getStartDate());
        vars.put("endDate", req.getEndDate());
        vars.put("userId", userId);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidaysProcess", vars);

        log.info("processInstance: {}", processInstance.getProcessInstanceId());

        return processInstance.getProcessInstanceId();
    }

    @Transactional
    public List<Holiday> getHolidaysPending() {
        List<Task> tasks = taskService.createTaskQuery()
                .taskName("Approve or reject request")
                .list();

        return tasks.stream().map(task -> {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            return Holiday.builder()
                    .userId((String) variables.get("userId"))
                    .startDate((Date) variables.get("startDate"))
                    .endDate((Date) variables.get("endDate"))
                    .build();
        }).collect(Collectors.toList());
    }

    @Transactional
    public String approveHolidays(String userId) {
        log.info("approveHoliday userId: {}", userId);

        List<Task> tasks = getTasks(userId);

        tasks.stream().forEach((task) -> {
            //taskService.claim(task.getId(), "app");
            Map<String, Object> vars = new HashMap<>();
            vars.put("approved", true);
            taskService.complete(task.getId(), vars);
        });

        return userId;
    }

    @Transactional
    public String rejectHolidays(String userId) {
        log.info("rejectHolidays userId: {}", userId);

        List<Task> tasks = getTasks(userId);

        tasks.stream().forEach((task) -> {
            //taskService.claim(task.getId(), "app");
            Map<String, Object> vars = new HashMap<>();
            vars.put("approved", false);
            taskService.complete(task.getId(), vars);
        });

        return userId;
    }

    private List<Task> getTasks(String userId) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskName("Approve or reject request")
                .includeProcessVariables()
                .processVariableValueEquals("userId", userId)
                .list();

        log.info("tasks size: {}", tasks.size());

        return tasks;
    }

    public Holiday processHoliday(String userId, Date startDate, Date endDate) {
        Holiday holiday = Holiday.builder()
                .userId(userId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        return holidayService.save(holiday);
    }
}
