package com.testweaver.repository;

import com.testweaver.domain.entity.TestProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestProjectRepository extends JpaRepository<TestProject, Long> {
    List<TestProject> findByNameContainingIgnoreCase(String q);
    
}
