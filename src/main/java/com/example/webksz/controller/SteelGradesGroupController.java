package com.example.webksz.controller;

import com.example.webksz.dto.CreateGroupRequestDto;
import com.example.webksz.dto.SteelGradesGroupDto;
import com.example.webksz.service.SteelGradesGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/webksz/steel-grades-groups")
@Validated
public class SteelGradesGroupController {

    @Autowired
    private SteelGradesGroupService steelGradesGroupService;

    @GetMapping
    public ResponseEntity<List<SteelGradesGroupDto>> getAll() {
        return ResponseEntity.ok(steelGradesGroupService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SteelGradesGroupDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(steelGradesGroupService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SteelGradesGroupDto> create(@Valid @RequestBody CreateGroupRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(steelGradesGroupService.create(requestDto.name()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SteelGradesGroupDto> update(@PathVariable Long id, @Valid @RequestBody CreateGroupRequestDto requestDto) {
        return ResponseEntity.ok(steelGradesGroupService.update(id, requestDto.name()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        steelGradesGroupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}