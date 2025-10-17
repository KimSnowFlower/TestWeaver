package com.testweaver.common.error;

import java.time.OffsetDateTime;

// record: 불변 데이터 객체를 간결하게 정의하는 Java의 특별한 클래스
public record ApiError(String code, String message, OffsetDateTime timestamp) {
    public static ApiError of(String code, String message) {
        return new ApiError(code, message, OffsetDateTime.now());
    }
}