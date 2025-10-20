package com.testweaver.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity 
@Table(name = "test_result_log")
@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TestResultLog {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "test_case_id", nullable = false)
    private TestCase testCase;
    
    @Column(length = 20, nullable = false)
    private String status;
    
    @Lob
    private String memo;
    
    @Column(nullable = false)
    @Builder.Default
    private OffsetDateTime executedAt = OffsetDateTime.now();
    
    // 로그는 수정하지 않는 불변 객체로 설계
    // 수정 메서드 없음
}