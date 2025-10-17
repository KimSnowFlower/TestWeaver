package com.testweaver.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity 
@Table(
    name = "parameter",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_parameter_name", 
        columnNames = {"project_id", "name"}
    )
)
@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Parameter {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "project_id", nullable = false)
    private TestProject project;
    
    @Column(length = 150, nullable = false)
    private String name;
    
    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;
    
    @Column(nullable = false)
    private OffsetDateTime createdAt;
    
    @Column(nullable = false)
    private OffsetDateTime updatedAt;
    
    public void updateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Parameter name cannot be blank");
        }
        this.name = name;
    }
    
    public void updateSortOrder(Integer sortOrder) {
        if (sortOrder == null || sortOrder < 0) {
            throw new IllegalArgumentException("Sort order must be non-negative");
        }
        this.sortOrder = sortOrder;
    }
    
    @PrePersist
    void prePersist() { 
        var now = OffsetDateTime.now(); 
        createdAt = now; 
        updatedAt = now; 
    }
    
    @PreUpdate
    void preUpdate() { 
        updatedAt = OffsetDateTime.now(); 
    }
}