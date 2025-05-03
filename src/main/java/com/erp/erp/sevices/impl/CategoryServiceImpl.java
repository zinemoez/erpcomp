package com.erp.erp.sevices.impl;



import com.erp.erp.dto.CategoryDTO;
import com.erp.erp.dto.Response;
import com.erp.erp.entity.Category;
import com.erp.erp.exceptions.NotFoundException;
import com.erp.erp.mappers.CategoryMapper;
import com.erp.erp.repository.CategoryRepository;
import com.erp.erp.sevices.serv.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
      categoryRepository.save(categoryMapper.toEntity(categoryDTO));
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());

        return categoryDTOS;
    }


    @Override
    public CategoryDTO getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Category Not Found"));
        CategoryDTO categoryDTO = categoryMapper.toDTO(category);

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
