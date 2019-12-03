package com.codegym.controllers;

import com.codegym.models.Category;
import com.codegym.services.BookService;
import com.codegym.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryServices categoryServices;

    @GetMapping("category-list")
    public String categoryList(Model model,@PageableDefault(size = 5) Pageable pageable) {
        Page<Category> categories = categoryServices.findAll(pageable);
        model.addAttribute("categories", categories);
        return "categories/list";
    }

    @GetMapping("/category-create")
    public String categoryCreate(Model model) {
        model.addAttribute("category", new Category());
        return "categories/create";
    }
    @PostMapping("/category-create")
    public String categoryCreate(Category category, Model model){
        categoryServices.save(category);
        model.addAttribute("category", new Category());
        model.addAttribute("message", "Created a new category");
        return "categories/create";
    }
    @GetMapping("/category-edit/{id}")
    public String categoryEdit(Model model, @PathVariable Long id) {
        Category category = categoryServices.findById(id);
        model.addAttribute("category", category);
        return "categories/edit";
    }
    @PostMapping("/category-edit")
    public String categoryEdit(@Validated Category category, Model model){
        categoryServices.save(category);
        model.addAttribute("category", category);
        model.addAttribute("message", "Edited category");
        return "categories/edit";
    }
    @GetMapping("/category-delete/{id}")
    public String categoryDelete(Model model, @PathVariable Long id) {
        Category category = categoryServices.findById(id);
        model.addAttribute("category", category);
        return "categories/delete";
    }
    @PostMapping("/category-delete")
    public String categoryDelete(@Validated Category category){
        categoryServices.delete(category.getId());
        return "redirect:/category-list";
    }
}
