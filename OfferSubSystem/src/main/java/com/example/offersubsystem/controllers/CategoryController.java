package com.example.offersubsystem.controllers;

import com.example.offersubsystem.entities.Category;
import com.example.offersubsystem.services.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ModelAttribute("allCategory")
    public List<Category> getAll(){
        List<Category> categoryList = categoryService.getAll();
        return categoryList;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("title", "Categories");
        return "category/index";
    }

    @GetMapping("/{customer_id}")
    public String show(@PathVariable("customer_id") int id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        model.addAttribute("title", "Show category");
        return "category/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("customer") Category category, Model model) {
        model.addAttribute("title", "Create category");
        return "category/new";
    }

    @GetMapping("/{category_id}/edit")
    public String edit(Model model, @PathVariable("category_id") int id) {
        model.addAttribute("category", categoryService.getById(id));
        model.addAttribute("title", "Edit category");
        return "category/edit";
    }

}
