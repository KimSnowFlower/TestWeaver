package com.testweaver.respository;

import com.testweaver.domain.entity.TestCase;
import com.testweaver.domain.entity.TestProject;
import com.testweaver.domain.model.TestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    List<TestCase> findByProjectOrderByCreatedAtDesc(TestProject testProject);
    List<TestCase> findByProjectAndStatus(TestProject testProject, TestStatus status);
    boolean existsByProjectIdAndComboHash(Long projectId, String comboHash);

}
