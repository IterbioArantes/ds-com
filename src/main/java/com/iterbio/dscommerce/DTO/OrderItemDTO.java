package com.iterbio.dscommerce.DTO;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderItemDTO {

    @NotEmpty
    private Long productId;
    private String name;
    private Double price;
    @NotEmpty
    private Integer quantity;
    private String imgUrl;


    public Double getSubTotal(){
        return this.price * this.quantity;
    }
}