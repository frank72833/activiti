package com.fns.activiti.process.serviceprovider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailServiceProvider {

    public void sendEmail(String name, String email) {
        log.info("Sending email name: {}, email: {}", name, email);
    }
}
