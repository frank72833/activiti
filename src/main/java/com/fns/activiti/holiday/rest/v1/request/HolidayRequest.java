package com.fns.activiti.holiday.rest.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HolidayRequest {
    public Date startDate;
    public Date endDate;
}
