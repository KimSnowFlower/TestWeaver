package com.testweaver.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity 
@Table(name = "test_projects")
@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TestProject {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=200, nullable=false, unique=true)
    private String name;

    @Lob
    private String description;

    @Column(nullable=false)
    private OffsetDateTime createdAt;

    @Column(nullable=false)
    private OffsetDateTime updatedAt;

    // 비즈니스 로직이 담긴 수정 메서드
    public void updateInfo(String name, String description) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        if (description != null) {
            this.description = description;
        }
    }

    @PrePersist
    void prePersist() {
        var now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }
    
    @PreUpdate
    void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }
}