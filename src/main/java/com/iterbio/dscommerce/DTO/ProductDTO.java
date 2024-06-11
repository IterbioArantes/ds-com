package com.iterbio.dscommerce.DTO;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class ProductDTO{

    private Long id;

    @Size(min = 3, message ="Field must have of min 3 characters")
    @Size(max = 80, message ="Field must have of max 80 characters")
    @NotBlank(message = "Required Field")
    private String name;
    @Size(min = 10, message = "Field must have of min 10 characters")
    private String description;
    @NotNull(message = "Required Field")
    @Positive(message = "Price must be positive")
    private Double price;
    private String imgUrl;

    @NotEmpty(message = "The product must have at least 1 category")
    @Builder.Default
    private Set<CategoryDTO> categories = new HashSet<>();
}