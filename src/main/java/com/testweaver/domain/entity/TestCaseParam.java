package com.testweaver.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(
    name = "test_case_param",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_case_param", 
        columnNames = {"test_case_id", "parameter_id"}
    )
)
@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TestCaseParam {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "test_case_id", nullable = false)
    private TestCase testCase;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "parameter_id", nullable = false)
    private Parameter parameter;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "parameter_value_id", nullable = false)
    private ParameterValue parameterValue;
    
    public void changeParameterValue(ParameterValue parameterValue) {
        if (parameterValue == null) {
            throw new IllegalArgumentException("Parameter value cannot be null");
        }
        this.parameterValue = parameterValue;
    }
}