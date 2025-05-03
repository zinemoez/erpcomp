package com.erp.erp.sevices.serv;


import com.erp.erp.dto.CategoryDTO;
import com.erp.erp.dto.Response;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    Response deleteCategory(Long id);
}
