package com.erp.erp.sevices.impl;



import com.erp.erp.dto.CategoryDTO;
import com.erp.erp.dto.Response;
import com.erp.erp.entity.Category;
import com.erp.erp.exceptions.NotFoundException;
import com.erp.erp.repository.CategoryRepository;
import com.erp.erp.sevices.Imp.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category categoryToSave = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(categoryToSave);

        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {

        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<CategoryDTO> categoryDTOS = modelMapper.map(categories, new TypeToken<List<CategoryDTO>>() {}.getType());

        return categoryDTOS;
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Category Not Found"));
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);

        return categoryDTO;
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Category Not Found"));

        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);

        return categoryDTO;

    }

    @Override
    public Response deleteCategory(Long id) {

         categoryRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Category Not Found"));

        categoryRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("Category Successfully Deleted")
                .build();


    }
}
