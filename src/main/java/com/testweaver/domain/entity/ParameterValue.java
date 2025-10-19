package com.testweaver.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="parameter_value", uniqueConstraints = {
       @UniqueConstraint(name="uq_param_value", columnNames={"parameter_id","value"})
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParameterValue {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY) 
    @JoinColumn(name="parameter_id", nullable=false)
    private Parameter parameter;

    @Column(length=200, nullable=false)
    private String value;

    @Column(name="sort_order", nullable=false)
    @Builder.Default
    private Integer sortOrder = 0;

    public void updateValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Parameter value cannot be null or blank");
        }

        this.value = value;
    }

    public void updateSortOrder(Integer sortOrder) {
        if (sortOrder == null || sortOrder < 0) {
            throw new IllegalArgumentException("Sort order must be a non-negative integer");
        }

        this.sortOrder = sortOrder;
    }

    @Column(nullable=false)
    private OffsetDateTime createdAt;
    @Column(nullable=false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void prePersist() { var now = OffsetDateTime.now(); createdAt = now; updatedAt = now; }
    @PreUpdate
    void preUpdate() { updatedAt = OffsetDateTime.now(); }
}
