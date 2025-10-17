package com.testweaver.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity @Table(name = "test_projects")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TestProject {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=200, nullable=false, unique=true)
    private String name;

    @Lob
    private String description;

    @Column(nullable=false)
    private OffsetDateTime createdAt;

    @Column(nullable=false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void prePersit() {
        var now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }
    @PreUpdate
    void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }
}
