package com.iterbio.dscommerce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductMinDTO {

    private Long id;
    private String name;
    private double price;
    private String imgUrl;
}