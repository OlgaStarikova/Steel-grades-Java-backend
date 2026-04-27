package com.example.webksz.mapper;

import com.example.webksz.config.MapperConfig;
import com.example.webksz.dto.CreateSteelGradeRequestDto;
import com.example.webksz.dto.SteelGradeDto;
import com.example.webksz.dto.UpdateSteelGradeRequestDto;
import com.example.webksz.model.SteelGrade;
import com.example.webksz.model.SteelGradesGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface SteelGradeMapper {

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    SteelGradeDto toDto(SteelGrade steelGrade);

    default SteelGradeDto toDto(SteelGrade steelGrade, Boolean hasRecipe) {
        return new SteelGradeDto(
                steelGrade.getId(),
                steelGrade.getName(),
                steelGrade.getGroup().getId(),
                steelGrade.getGroup().getName(),
                hasRecipe
        );
    }

    @Mapping(source = "groupId", target = "group")
    @Mapping(target = "id", ignore = true)
    SteelGrade toModel(CreateSteelGradeRequestDto requestDto);

    default SteelGradesGroup mapGroup(Long groupId) {
        if (groupId == null) {
            return null;
        }
        SteelGradesGroup group = new SteelGradesGroup();
        group.setId(groupId);
        return group;
    }
}