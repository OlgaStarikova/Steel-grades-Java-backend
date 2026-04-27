package com.example.webksz.service;

import com.example.webksz.dto.SteelGradesGroupDto;
import com.example.webksz.model.SteelGradesGroup;
import com.example.webksz.repository.SteelGradesGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SteelGradesGroupService {

    private final SteelGradesGroupRepository steelGradesGroupRepository;

    public List<SteelGradesGroupDto> findAll() {
        return steelGradesGroupRepository.findAll()
                .stream()
                .map(group -> new SteelGradesGroupDto(group.getId(), group.getName()))
                .toList();
    }

    public SteelGradesGroupDto findById(Long id) {
        SteelGradesGroup group = steelGradesGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + id));
        return new SteelGradesGroupDto(group.getId(), group.getName());
    }

    @Transactional
    public SteelGradesGroupDto create(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be empty");
        }
        SteelGradesGroup group = new SteelGradesGroup();
        group.setName(name);
        group = steelGradesGroupRepository.save(group);
        return new SteelGradesGroupDto(group.getId(), group.getName());
    }

    @Transactional
    public SteelGradesGroupDto update(Long id, String name) {
        SteelGradesGroup group = steelGradesGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + id));
        group.setName(name);
        group = steelGradesGroupRepository.save(group);
        return new SteelGradesGroupDto(group.getId(), group.getName());
    }

    @Transactional
    public void delete(Long id) {
        steelGradesGroupRepository.deleteById(id);
    }
}