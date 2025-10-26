package com.testweaver.service;

import com.testweaver.common.error.NotFoundException;
import com.testweaver.domain.dto.project.ProjectDtos;
import com.testweaver.domain.entity.TestProject;
import com.testweaver.repository.TestProjectRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestProjectService {
    private final TestProjectRepository projectRepo;

    @Transactional
    public ProjectDtos.Resp create(ProjectDtos.CreateReq req) {
        var entity = TestProject.builder()
                .name(req.name())
                .description(req.description())
                .build();
        
        var saved = projectRepo.save(entity);

        return new ProjectDtos.Resp(saved.getId(), saved.getName(), saved.getDescription());
    }

        @Transactional(readOnly = true)
    public List<ProjectDtos.Resp> list(String q) {
        var list = (q == null || q.isBlank())
                ? projectRepo.findAll()
                : projectRepo.findByNameContainingIgnoreCase(q);
        return list.stream()
                .map(p -> new ProjectDtos.Resp(p.getId(), p.getName(), p.getDescription()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjectDtos.Resp get(Long id) {
        var p = projectRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found: " + id));
        return new ProjectDtos.Resp(p.getId(), p.getName(), p.getDescription());
    }

    @Transactional
    public ProjectDtos.Resp update(Long id, ProjectDtos.UpdateReq req) {
        var p = projectRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found: " + id));
        
        p.updateInfo(req.name(), req.description());
        
        return new ProjectDtos.Resp(p.getId(), p.getName(), p.getDescription());
    }

    @Transactional
    public void delete(Long id) {
        if (!projectRepo.existsById(id)) throw new NotFoundException("Project not found: " + id);
        projectRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TestProject getEntity(Long id) {
        return projectRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found: " + id));
    }
}
