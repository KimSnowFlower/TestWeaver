package com.testweaver.respository;

import com.testweaver.domain.entity.TestCaseParam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseParamRepository extends JpaRepository<TestCaseParam, Long> {
    
}
