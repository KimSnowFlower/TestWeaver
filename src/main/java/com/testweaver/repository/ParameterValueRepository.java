package com.testweaver.repository;

import com.testweaver.domain.entity.Parameter;
import com.testweaver.domain.entity.ParameterValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParameterValueRepository extends JpaRepository<ParameterValue, Long> {
    List<ParameterValue> findByParameterOrderBySortOrderAsc(Parameter parameter);
    Optional<ParameterValue> findByParameterAndValueIgnoreCase(Parameter parameter, String value);
}
