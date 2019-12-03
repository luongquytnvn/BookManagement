package com.codegym.services.impl;

import com.codegym.models.Book;
import com.codegym.models.Category;
import com.codegym.repositories.BookRepository;
import com.codegym.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findOne(id);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.delete(id);
    }

    @Override
    public Page<Book> finByCategory(Category category, Pageable pageable) {
        return bookRepository.findAllByCategory(category, pageable);
    }
}
