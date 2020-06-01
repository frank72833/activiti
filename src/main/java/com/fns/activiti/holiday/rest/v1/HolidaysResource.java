package com.fns.activiti.holiday.rest.v1;

import com.fns.activiti.holiday.Holiday;
import com.fns.activiti.holiday.rest.v1.request.HolidayRequest;
import com.fns.activiti.holiday.serviceProvider.HolidayServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/processes", produces = MediaType.APPLICATION_JSON_VALUE)
public class HolidaysResource {

    private final HolidayServiceProvider holidayServiceProvider;

    @Autowired
    public HolidaysResource(HolidayServiceProvider holidayServiceProvider) {
        this.holidayServiceProvider = holidayServiceProvider;
    }

    @GetMapping("/holidays/pending")
    public List<Holiday> getHolidaysPending() {
        return holidayServiceProvider.getHolidaysPending();
    }

    @PostMapping("/{userId}/holidays/approve")
    public String approveHolidays(@PathVariable("userId") String userId) {
        return holidayServiceProvider.approveHolidays(userId);
    }

    @PostMapping("/{userId}/holidays/reject")
    public String rejectHolidays(@PathVariable("userId") String userId) {
        return holidayServiceProvider.rejectHolidays(userId);
    }

    @PostMapping("/{userId}/holidays")
    public String requestHoliday(@PathVariable("userId") String userId, @RequestBody HolidayRequest request) {
        return holidayServiceProvider.requestHoliday(userId, request);
    }
}
