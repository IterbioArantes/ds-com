package com.iterbio.dscommerce.services;

import com.iterbio.dscommerce.DTO.CategoryDTO;
import com.iterbio.dscommerce.DTO.ProductDTO;
import com.iterbio.dscommerce.DTO.ProductMinDTO;
import com.iterbio.dscommerce.entities.Category;
import com.iterbio.dscommerce.entities.Product;
import com.iterbio.dscommerce.repositories.CategoryRepository;
import com.iterbio.dscommerce.repositories.ProductRepository;
import com.iterbio.dscommerce.services.exceptions.DataBaseException;
import com.iterbio.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){

        Optional<Product> product = productRepository.findById(id);
        return entityToDto(product.orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(String name,Pageable pageable){

        Page<Product> products = productRepository.searchByName(name , pageable);
        return products.map(this::entityToMinDto);
    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO){
        Product product = productRepository.save(dtoToEntity(productDTO));

        return entityToDto(product);
    }

    @Transactional
    public ProductDTO update(Long id,ProductDTO productDTO){
        try{
            Product userToUpdate = productRepository.getReferenceById(id);
            dtoToExistingEntity(productDTO,userToUpdate);

            return entityToDto(productRepository.save(userToUpdate));
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id,"To Update");
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException(id,"To delete");
        }
        try{
            productRepository.deleteById(id);
        }catch(DataIntegrityViolationException e){
            throw new DataBaseException("Integrity Failure Reference.");
        }

    }

    private ProductMinDTO entityToMinDto(Product product) {

        return ProductMinDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .imgUrl(product.getImgUrl())
                .build();
    }

    private ProductDTO entityToDto(Product product) {

        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imgUrl(product.getImgUrl())
                .build();

        product.getCategories().forEach(x -> productDTO.getCategories().add(new CategoryDTO(x.getId(),x.getName())));

        return productDTO;
    }

    private Product dtoToEntity(ProductDTO productDTO) {

        Product product = Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .imgUrl(productDTO.getImgUrl())
                .build();


        Long catId = 0L;
        try{
             for(CategoryDTO x : productDTO.getCategories()){
                 catId = x.getId();
                 product.getCategories().add(categoryRepository.getReferenceById(catId));
             }
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(catId, "'category' to insert");
        }
        return product;
    }

    private void dtoToExistingEntity(ProductDTO productDTO, Product existingProduct) {

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setImgUrl(productDTO.getImgUrl());

        existingProduct.getCategories().clear();
        productDTO.getCategories().forEach(x-> existingProduct.getCategories().add(new Category(x.getId())));

    }
}
