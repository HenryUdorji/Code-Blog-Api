package com.codemountain.codeblog.controller;


import com.codemountain.codeblog.dto.CategoryDto;
import com.codemountain.codeblog.entity.Category;
import com.codemountain.codeblog.repository.CategoryRepository;
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

    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findByNameIgnoreCase(categoryDto.getName());
        if (existingCategory != null) {
            return new ResponseEntity<>("Category already exist", HttpStatus.CONFLICT);
        }
        else {
            categoryService.createCategory(categoryDto);
            return new ResponseEntity<>("Category created - " + categoryDto.getName(), HttpStatus.CREATED);
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getAll());
    }

    //TODO -> Create an api to query just a single category from Database
}
