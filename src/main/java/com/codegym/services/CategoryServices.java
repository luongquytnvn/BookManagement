package com.codegym.services;

import com.codegym.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryServices {
    Page<Category> findAll(Pageable pageable);
    Category findById(Long id);
    void save(Category category);
    void delete(Long id);
}
