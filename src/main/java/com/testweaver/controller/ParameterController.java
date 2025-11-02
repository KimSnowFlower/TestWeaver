package com.testweaver.controller;

import com.testweaver.domain.dto.parameter.ParameterDtos;
import com.testweaver.service.ParameterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequiredArgsConstructor
@RequestMapping("/api/parameters")
public class ParameterController {

    private final ParameterService parameterService;

    @PostMapping("/projects/{projectId}/parameters")
    public ResponseEntity<ParameterDtos.ParamResp> create(@PathVariable Long projectId,
                                                          @RequestBody @Valid ParameterDtos.CreateReq req) {
        return ResponseEntity.ok(parameterService.create(projectId, req));
    }

    @GetMapping("/projects/{projectId}/parameters")
    public ResponseEntity<?> list(@PathVariable Long projectId) {
        return ResponseEntity.ok(parameterService.list(projectId));
    }

    @PutMapping("/parameters/{paramId}")
    public ResponseEntity<ParameterDtos.ParamResp> update(@PathVariable Long paramId,
                                                          @RequestBody @Valid ParameterDtos.UpdateReq req) {
        return ResponseEntity.ok(parameterService.update(paramId, req));
    }

    @DeleteMapping("/parameters/{paramId}")
    public ResponseEntity<Void> delete(@PathVariable Long paramId) {
        parameterService.delete(paramId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/parameters/values:batchCreate")
    public ResponseEntity<?> addValues(@RequestBody @Valid ParameterDtos.ValueCreateReq req) {
        return ResponseEntity.ok(parameterService.addValues(req));
    }
}
