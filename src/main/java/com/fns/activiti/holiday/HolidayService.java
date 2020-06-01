package com.fns.activiti.holiday;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayService extends JpaRepository<Holiday, Long> {
    List<Holiday> findByUserId(String userId);
}
