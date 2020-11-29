package com.codemountain.codeblog.controller;


import com.codemountain.codeblog.dto.CategoryDto;
import com.codemountain.codeblog.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity createCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category created - " + categoryDto.getName());
    }


    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getSingleCategory(id));
    }

    //TODO -> Create api for updating category info
}
