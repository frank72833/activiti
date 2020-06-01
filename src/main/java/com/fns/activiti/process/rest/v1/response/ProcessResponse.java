package com.fns.activiti.process.rest.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessResponse {
    private String processInstanceId;
    private String id;
    private String name;
    private String tenantId;
    private String localizedName;
    private String deploymentId;
    private Date startTime;
    private String processDefinitionId;
}
