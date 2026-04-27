package com.example.webksz.service;

import com.example.webksz.dto.CreateSteelGradeRequestDto;
import com.example.webksz.dto.SteelGradeDto;
import com.example.webksz.dto.UpdateSteelGradeRequestDto;
import com.example.webksz.model.SteelGrade;
import com.example.webksz.model.SteelGradesGroup;
import com.example.webksz.repository.SteelGradeRepository;
import com.example.webksz.repository.SteelGradesGroupRepository;
import com.example.webksz.repository.SteelRecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SteelGradeService {

    private final SteelGradeRepository steelGradeRepository;
    private final SteelGradesGroupRepository steelGradesGroupRepository;
    private final SteelRecipeRepository steelRecipeRepository;

    public List<SteelGradeDto> findAll() {
        return steelGradeRepository.findAll()
                .stream()
                .map(steelGrade -> {
                    boolean hasRecipe = steelRecipeRepository.existsBySteelGradeIdAndIsDeletedFalse(steelGrade.getId());
                    return toDto(steelGrade, hasRecipe);
                })
                .toList();
    }

    public SteelGradeDto findById(Long id) {
        SteelGrade steelGrade = steelGradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SteelGrade not found: " + id));
        boolean hasRecipe = steelRecipeRepository.existsBySteelGradeIdAndIsDeletedFalse(id);
        return toDto(steelGrade, hasRecipe);
    }

    private SteelGradeDto toDto(SteelGrade steelGrade, Boolean hasRecipe) {
        return new SteelGradeDto(
                steelGrade.getId(),
                steelGrade.getName(),
                steelGrade.getGroup().getId(),
                steelGrade.getGroup().getName(),
                hasRecipe
        );
    }

    @Transactional
    public SteelGradeDto create(CreateSteelGradeRequestDto requestDto) {
        if (requestDto.groupId() == null) {
            throw new IllegalArgumentException("groupId must not be null");
        }
        if (requestDto.name() == null || requestDto.name().isBlank()) {
            throw new IllegalArgumentException("name must not be empty");
        }
        if (steelGradeRepository.existsByNameIgnoreCase(requestDto.name())) {
            throw new IllegalArgumentException("Steel grade with this name already exists");
        }

        SteelGradesGroup group = steelGradesGroupRepository
                .findById(requestDto.groupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + requestDto.groupId()));

        SteelGrade steelGrade = new SteelGrade();
        steelGrade.setName(requestDto.name());
        steelGrade.setGroup(group);

        steelGrade = steelGradeRepository.save(steelGrade);
        return toDto(steelGrade, false);
    }

    @Transactional
    public SteelGradeDto update(Long id, UpdateSteelGradeRequestDto requestDto) {
        SteelGrade steelGrade = steelGradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SteelGrade not found: " + id));

        if (steelGradeRepository.existsByNameIgnoreCaseAndIdNot(requestDto.name(), id)) {
            throw new IllegalArgumentException("Steel grade with this name already exists");
        }

        SteelGradesGroup group = steelGradesGroupRepository
                .findById(requestDto.groupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + requestDto.groupId()));

        steelGrade.setName(requestDto.name());
        steelGrade.setGroup(group);

        steelGrade = steelGradeRepository.save(steelGrade);
        boolean hasRecipe = steelRecipeRepository.existsBySteelGradeIdAndIsDeletedFalse(id);
        return toDto(steelGrade, hasRecipe);
    }

    @Transactional
    public void delete(Long id) {
        boolean hasRecipe = steelRecipeRepository.existsBySteelGradeIdAndIsDeletedFalse(id);
        if (hasRecipe) {
            throw new IllegalArgumentException("Cannot delete steel grade with recipes");
        }
        steelGradeRepository.deleteById(id);
    }
}