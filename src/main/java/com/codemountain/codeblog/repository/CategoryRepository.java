package com.codemountain.codeblog.repository;

import com.codemountain.codeblog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByNameIgnoreCase(String categoryDtoName);

    //Optional<Category> findByName(String categoryName);
    Category findByName(String categoryName);
}
