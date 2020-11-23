package com.codemountain.codeblog.service;


import com.codemountain.codeblog.dto.CategoryDto;
import com.codemountain.codeblog.entity.Category;
import com.codemountain.codeblog.exception.CodeBlogException;
import com.codemountain.codeblog.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category =  categoryRepository.save(mapDtoToCategory(categoryDto));
        categoryDto.setCategoryId(category.getCategoryId());
        return categoryDto;
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToCategoryDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto getSingleCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new CodeBlogException("No Category found with id - " + id));
        return mapToCategoryDto(category);
    }

    //reads from database
    private CategoryDto mapToCategoryDto(Category category) {
        return CategoryDto.builder()
                .name(category.getName())
                .categoryId(category.getCategoryId())
                .description(category.getDescription())
                .numberOfPosts(category.getCategoryPost().size())
                .createdDate(category.getCreatedDate())
                .build();
    }

    //saves to database
    private Category mapDtoToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .createdDate(System.currentTimeMillis())
                .build();
    }

}
