package com.julienprr.dreamshops.service.category;

import com.julienprr.dreamshops.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    List<Category> getAllCategories();

    Category addCategory(Category category);

    Category updateCategory(Long id, Category category);

    void deleteCategoryById(Long id);

}
