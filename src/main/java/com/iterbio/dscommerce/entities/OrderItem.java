package com.iterbio.dscommerce.entities;

import com.iterbio.dscommerce.entities.pk.OrderItemPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "tb_order_item")
public class OrderItem {

    @EmbeddedId
    private final OrderItemPK id = new OrderItemPK();

    private Integer quantity;
    private double price;

    public OrderItem(Order order,Product product, Integer quantity, double price) {
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }

    public Product getProduct(){
        return id.getProduct();
    }
    public Order getOrder(){
        return id.getOrder();
    }

}