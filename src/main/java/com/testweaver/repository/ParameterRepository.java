package com.testweaver.repository;

import com.testweaver.domain.entity.Parameter;
import com.testweaver.domain.entity.TestProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    List<Parameter> findByProjectOrderBySortOrderAsc(TestProject testProject);
    
    Optional<Parameter> findByProjectAndNameIgnoreCase(TestProject testProject, String name);
    
}
