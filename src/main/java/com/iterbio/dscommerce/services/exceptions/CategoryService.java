package com.iterbio.dscommerce.services.exceptions;

import com.iterbio.dscommerce.DTO.CategoryDTO;
import com.iterbio.dscommerce.entities.Category;
import com.iterbio.dscommerce.entities.Order;
import com.iterbio.dscommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){

        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream().map(this::entityToDto).toList();
    }

    private CategoryDTO entityToDto(Category category){

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}