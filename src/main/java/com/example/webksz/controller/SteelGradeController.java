package com.example.webksz.controller;

import com.example.webksz.dto.CreateSteelGradeRequestDto;
import com.example.webksz.dto.SteelGradeDto;
import com.example.webksz.dto.UpdateSteelGradeRequestDto;
import com.example.webksz.service.SteelGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/webksz/steel-grades")
@Validated
public class SteelGradeController {

    @Autowired
    private SteelGradeService steelGradeService;

    @GetMapping
    public ResponseEntity<List<SteelGradeDto>> getAll() {
        return ResponseEntity.ok(steelGradeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SteelGradeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(steelGradeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SteelGradeDto> create(@Valid @RequestBody CreateSteelGradeRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(steelGradeService.create(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SteelGradeDto> update(@PathVariable Long id, @Valid @RequestBody UpdateSteelGradeRequestDto requestDto) {
        return ResponseEntity.ok(steelGradeService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        steelGradeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}