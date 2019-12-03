package com.codegym.controllers;

import com.codegym.models.Book;
import com.codegym.models.Category;
import com.codegym.services.BookService;
import com.codegym.services.CategoryServices;
import org.hibernate.boot.model.source.spi.Sortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryServices categoryServices;

    @ModelAttribute("categories")
    public Page<Category> categories(Pageable pageable) {
        return categoryServices.findAll(pageable);
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("book-list")
    public String bookList(Model model, @PageableDefault(size = 5) Pageable pageable) {
        Page<Book> books = bookService.findAll(pageable);
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("book-list-date")
    public String bookListByDate(Model model, @PageableDefault(size = 5, direction = Sort.Direction.DESC, sort = "dateOfPurchase") Pageable pageable) {
        Page<Book> books = bookService.findAll(pageable);
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("book-list-price")
    public String bookListByPrice(Model model, @PageableDefault(size = 5, sort = "price") Pageable pageable) {
        Page<Book> books = bookService.findAll(pageable);
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/book-create")
    public String bookCreate(Model model) {
        model.addAttribute("book", new Book());
        return "books/create";
    }

    @PostMapping("/book-create")
    public String bookCreate(Book book, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", bindingResult.getAllErrors());
            return "books/create";
        }
        bookService.save(book);
        model.addAttribute("book", new Book());
        model.addAttribute("message", "Created a new book");
        return "books/create";
    }

    @GetMapping("/book-edit/{id}")
    public String bookEdit(Model model, @PathVariable Long id) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "books/edit";
    }

    @PostMapping("/book-edit")
    public String bookEdit(Book book, Model model) {
        bookService.save(book);
        model.addAttribute("book", book);
        model.addAttribute("message", "Edited book");
        return "books/edit";
    }

    @GetMapping("/book-delete/{id}")
    public String bookDelete(Model model, @PathVariable Long id) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "books/delete";
    }

    @PostMapping("/book-delete")
    public String bookDelete(Book book) {
        bookService.delete(book.getId());
        return "redirect:/category-list";
    }

    @GetMapping("/book-list-by-category/{id}")
    public String bookListByCategory(@PathVariable Long id, Model model, @PageableDefault(size = 5) Pageable pageable) {
        Category category = categoryServices.findById(id);
        Page<Book> books = bookService.finByCategory(category, pageable);
        model.addAttribute("books", books);
        return "books/list-book-by-category";
    }

    @PostMapping("/book-search")
    public String bookSearch(@RequestParam("search") String search, Model model, @PageableDefault(size = 5) Pageable pageable) {
        if (search == null) {
            return "redirect:/book-list";
        }
        Page<Book> books = bookService.findAll(pageable);
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getName().toLowerCase().contains(search.toLowerCase())) {
                result.add(book);
            }
        }
        model.addAttribute("books", result);
        return "books/list";
    }
}
