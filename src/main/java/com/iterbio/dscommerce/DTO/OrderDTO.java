package com.iterbio.dscommerce.DTO;

import com.iterbio.dscommerce.entities.enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OrderDTO {

    private Long id;
    private Instant moment;
    private OrderStatus status;

    private UserMinDTO client;
    private PaymentDTO payment;

    @NotEmpty(message = "The order must have at least 1 item")
    @Builder.Default
    private final List<OrderItemDTO> items = new ArrayList<>();

    public Double getTotal(){

        return items.stream().map(OrderItemDTO::getSubTotal).reduce(0.0, Double::sum);

    }
}
