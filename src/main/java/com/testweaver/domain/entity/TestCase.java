package com.testweaver.domain.entity;

import com.testweaver.domain.model.TestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="test_case", uniqueConstraints = {
       @UniqueConstraint(name="uq_case_combo", columnNames={"project_id","combo_hash"})
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_id", nullable=false)
    private TestProject project;

    @Column(length=255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable=false)
    @Builder.Default
    private TestStatus status = TestStatus.PENDING;

    @Lob
    private String notes;

    @Column(name="combo_hash", length=64)
    private String comboHash;

    @Column(nullable=false)
    private OffsetDateTime createdAt;
    @Column(nullable=false)
    private OffsetDateTime updatedAt;

    public void updateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Test case name cannot be null or blank");
        }
        this.name = name;
    }

    public void updateStatus(TestStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Test status cannot be null");
        }
        this.status = status;
    }

    public void updateNotes(String notes) {
        this.notes = notes;
    }

    public void markAsPending() {
        this.status = TestStatus.PENDING;
    }

    public void markAsFailed() {
        this.status = TestStatus.FAIL;
    }

    public void markAsPassed() {
        this.status = TestStatus.PASS;
    }

    @PrePersist
    void prePersist() { var now = OffsetDateTime.now(); createdAt = now; updatedAt = now; }
    @PreUpdate
    void preUpdate() { updatedAt = OffsetDateTime.now(); }
}
