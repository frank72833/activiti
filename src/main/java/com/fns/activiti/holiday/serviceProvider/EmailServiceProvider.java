package com.fns.activiti.holiday.serviceProvider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailServiceProvider {
    public void sendApproveEmail(String userId) {
        log.info("sendApproveEmail: {}", userId);
    }

    public void sendRejectEmail(String userId) {
        log.info("sendRejectEmail: {}", userId);
    }
}
