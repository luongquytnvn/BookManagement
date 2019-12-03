package com.codegym.services;

import com.codegym.models.Book;
import com.codegym.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<Book> findAll(Pageable pageable);
    Book findById(Long id);
    void save(Book book);
    void delete(Long id);
    Page<Book> finByCategory(Category category, Pageable pageable);
}
