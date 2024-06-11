package com.iterbio.dscommerce.controllers;

import com.iterbio.dscommerce.DTO.ProductDTO;
import com.iterbio.dscommerce.DTO.ProductMinDTO;
import com.iterbio.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){

        ProductDTO product = productService.findById(id);

        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<Page<ProductMinDTO>> findAll(@RequestParam(name = "name", defaultValue = "") String name , Pageable pageable){

        Page<ProductMinDTO> products = productService.findAll(name , pageable);

        return ResponseEntity.ok(products);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO productDTO){

        ProductDTO productDTOInserted = productService.insert(productDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(productDTOInserted.getId()).toUri();

        return ResponseEntity.created(uri).body(productDTOInserted);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO){

        ProductDTO productDTOResult = productService.update(id,productDTO);

        return ResponseEntity.ok(productDTOResult);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
