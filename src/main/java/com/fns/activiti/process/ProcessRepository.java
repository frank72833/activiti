package com.fns.activiti.process;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProcessRepository extends CrudRepository<Process, Long> {
    List<Process> findById(String id);
}