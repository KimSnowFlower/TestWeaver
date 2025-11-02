package com.testweaver.service;

import com.testweaver.common.error.BadRequestException;
import com.testweaver.common.error.NotFoundException;
import com.testweaver.domain.dto.parameter.ParameterDtos;
import com.testweaver.domain.entity.Parameter;
import com.testweaver.domain.entity.ParameterValue;
import com.testweaver.domain.entity.TestProject;
import com.testweaver.repository.ParameterRepository;
import com.testweaver.repository.ParameterValueRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service 
@RequiredArgsConstructor
public class ParameterService {
    private final ParameterRepository paramRepo = null;
    private final ParameterValueRepository valueRepo = null;
    private final TestProjectService projectService = null;

    @Transactional
    public ParameterDtos.ParamResp create(Long projectId, ParameterDtos.CreateReq req) {
        TestProject project = projectService.getEntity(projectId);

        paramRepo.findByProjectAndNameIgnoreCase(project, req.name()).ifPresent(p -> {
            throw new BadRequestException("duplicated parameter name in project");
        });

        var entity = Parameter.builder()
                .project(project)
                .name(req.name())
                .sortOrder(Objects.requireNonNullElse(req.sortOrder(), 0))
                .build();
        var saved = paramRepo.save(entity);

        if (req.values() != null && !req.values().isEmpty()) {
            int i=0;
            for (String v : dedup(req.values())) {
                var pv = ParameterValue.builder()
                        .parameter(saved)
                        .value(v)
                        .sortOrder(i++)
                        .build();
                valueRepo.save(pv);
            }
        }
        return new ParameterDtos.ParamResp(saved.getId(), saved.getName(), saved.getSortOrder());
    }

    @Transactional(readOnly = true)
    public List<ParameterDtos.ParamWithValuesResp> list(Long projectId) {
        TestProject project = projectService.getEntity(projectId);
        var params = paramRepo.findByProjectOrderBySortOrderAsc(project);
        return params.stream().map(p -> {
            var values = valueRepo.findByParameterOrderBySortOrderAsc(p).stream()
                    .map(v -> new ParameterDtos.ValueResp(v.getId(), v.getValue(), v.getSortOrder()))
                    .toList();
            return new ParameterDtos.ParamWithValuesResp(p.getId(), p.getName(), p.getSortOrder(), values);
        }).toList();
    }

    @Transactional
    public ParameterDtos.ParamResp update(Long paramId, ParameterDtos.UpdateReq req) {
        var p = paramRepo.findById(paramId)
                .orElseThrow(() -> new NotFoundException("Parameter not found: " + paramId));
        // 중복 이름 검사
        paramRepo.findByProjectAndNameIgnoreCase(p.getProject(), req.name())
                .filter(other -> !Objects.equals(other.getId(), p.getId()))
                .ifPresent(x -> { throw new BadRequestException("duplicated parameter name in project"); });

        p.updateName(req.name());
        if (req.sortOrder() != null) p.updateSortOrder(req.sortOrder());
        return new ParameterDtos.ParamResp(p.getId(), p.getName(), p.getSortOrder());
    }

    @Transactional
    public void delete(Long paramId) {
        if (!paramRepo.existsById(paramId)) throw new NotFoundException("Parameter not found: " + paramId);
        paramRepo.deleteById(paramId);
    }

    @Transactional
    public List<ParameterDtos.ValueResp> addValues(ParameterDtos.ValueCreateReq req) {
        var p = paramRepo.findById(req.parameterId())
                .orElseThrow(() -> new NotFoundException("Parameter not found: " + req.parameterId()));
        var exist = new HashSet<>(
                valueRepo.findByParameterOrderBySortOrderAsc(p).stream().map(ParameterValue::getValue).map(String::toLowerCase).toList()
        );
        int base = (int) valueRepo.findByParameterOrderBySortOrderAsc(p).size();
        var results = new ArrayList<ParameterDtos.ValueResp>();
        int i=0;
        for (String v: dedup(req.values())) {
            if (exist.contains(v.toLowerCase())) continue;
            var saved = valueRepo.save(ParameterValue.builder()
                    .parameter(p).value(v).sortOrder(base + i++).build());
            results.add(new ParameterDtos.ValueResp(saved.getId(), saved.getValue(), saved.getSortOrder()));
        }
        return results;
    }

    private static List<String> dedup(List<String> src) {
        return src.stream().map(String::trim).filter(s -> !s.isBlank())
                .distinct().toList();
    }
}
