package com.testweaver.domain.dto.parameter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ParameterDtos {
    public record CreateReq(@NotBlank String name, Integer sortOrder, List<@NotNull String> values) {}
    public record UpdateReq(@NotBlank String name, Integer sortOrder) {}
    public record ValueCreateReq(@NotNull Long parameterId, List<@NotBlank String> values) {}

    public record ParamResp(Long id, String name, Integer sortOrder) {}
    public record ValueResp(Long id, String value, Integer sortOrder) {}

    public record ParamWithValuesResp(Long id, String name, Integer sortOrder, List<ValueResp> values) {}
}
