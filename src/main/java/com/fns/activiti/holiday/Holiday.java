package com.fns.activiti.holiday;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Holiday {
    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private Date startDate;
    private Date endDate;
}
