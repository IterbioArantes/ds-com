package com.iterbio.dscommerce.repositories;

import com.iterbio.dscommerce.entities.Order;
import com.iterbio.dscommerce.entities.OrderItem;
import com.iterbio.dscommerce.entities.pk.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
