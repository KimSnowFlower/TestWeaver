package com.testweaver.domain.dto.project;

import jakarta.validation.constraints.NotBlank;

public class ProjectDtos {
    public record CreateReq(@NotBlank String name, String description) {}
    public record UpdateReq(@NotBlank String name, String description) {}
    public record Resp(Long id, String name, String description) {}
}
