package com.example.offersubsystem.rest;

import com.example.offersubsystem.dto.CategoryDto;
import com.example.offersubsystem.entities.Category;
import com.example.offersubsystem.services.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryRestController {
    CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<Category> indexRest() {
        return categoryService.getAll();
    }

    @GetMapping("/categories/{category_id}")
    public Category showRest(@PathVariable("category_id") int id) {
        return categoryService.getById(id);
    }


    @PostMapping("")
    public Category createRest(Category category) {
        categoryService.create(category);
        return category;
    }

    @PutMapping("/{category_id}")
    public Category updateRest(@PathVariable("category_id") int id,
                               CategoryDto categoryDto
    ) {

        Category category = categoryService.updateWithDto(categoryDto);

        return category;
    }
    @DeleteMapping("/{category_id}")
    public Category deleteRest(@PathVariable("category_id") int id) {
        Category category = categoryService.getById(id);
        categoryService.delete(id);
        return category;
    }

}
