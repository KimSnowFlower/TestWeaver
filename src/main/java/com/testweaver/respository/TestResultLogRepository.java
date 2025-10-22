package com.testweaver.repository;

import com.testweaver.domain.entity.TestResultLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultLogRepository extends JpaRepository<TestResultLog, Long> { 
    
}
