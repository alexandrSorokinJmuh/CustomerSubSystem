package com.example.offersubsystem.services;

import com.example.offersubsystem.dao.CategoriesDao;
import com.example.offersubsystem.dto.CategoryDto;
import com.example.offersubsystem.entities.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    CategoriesDao categoriesDao;

    public CategoryService(CategoriesDao categoriesDao) {
        this.categoriesDao = categoriesDao;
    }

    public List<Category> getAll() {
        return categoriesDao.getAll();
    }

    public Category getById(int id) {
        return categoriesDao.getById(id);
    }

    public Category create(Category category){

        return categoriesDao.create(category);
    }

    public Category update(int id, Category category){
        return categoriesDao.update(id, category);
    }

    public void delete(int id){
        categoriesDao.delete(id);
    }

    public Category updateWithDto(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());

        return update(categoryDto.getCategory_id(), category);
    }
}
