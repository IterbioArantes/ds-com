package com.iterbio.dscommerce.controllers;

import com.iterbio.dscommerce.DTO.CategoryDTO;
import com.iterbio.dscommerce.DTO.UserDTO;
import com.iterbio.dscommerce.services.exceptions.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(){

        List<CategoryDTO> categoryDtoList = categoryService.findAll();

        return ResponseEntity.ok(categoryDtoList);
    }
}