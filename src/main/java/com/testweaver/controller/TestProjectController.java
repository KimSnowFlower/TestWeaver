package com.testweaver.controller;

import com.testweaver.domain.dto.project.ProjectDtos;
import com.testweaver.service.TestProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequiredArgsConstructor
@RequestMapping("/api/test-projects")
public class TestProjectController {

    private final TestProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDtos.Resp> create(@RequestBody @Valid ProjectDtos.CreateReq req) {
        return ResponseEntity.ok(projectService.create(req));
    }

    @GetMapping
    public ResponseEntity<List<ProjectDtos.Resp>> list(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(projectService.list(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDtos.Resp> get(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDtos.Resp> update(@PathVariable Long id,
                                                   @RequestBody @Valid ProjectDtos.UpdateReq req) {
        return ResponseEntity.ok(projectService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
