package com.iterbio.dscommerce.services;

import com.iterbio.dscommerce.DTO.OrderDTO;
import com.iterbio.dscommerce.DTO.OrderItemDTO;
import com.iterbio.dscommerce.DTO.PaymentDTO;
import com.iterbio.dscommerce.DTO.UserMinDTO;
import com.iterbio.dscommerce.entities.Order;
import com.iterbio.dscommerce.entities.OrderItem;
import com.iterbio.dscommerce.entities.Product;
import com.iterbio.dscommerce.entities.User;
import com.iterbio.dscommerce.entities.enums.OrderStatus;
import com.iterbio.dscommerce.repositories.OrderItemRepository;
import com.iterbio.dscommerce.repositories.OrderRepository;
import com.iterbio.dscommerce.repositories.ProductRepository;
import com.iterbio.dscommerce.services.exceptions.BodyRequestException;
import com.iterbio.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private AuthService authService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){

        Optional<Order> orderOpt = orderRepository.findById(id);
        Order order = orderOpt.orElseThrow(() -> new ResourceNotFoundException(id));

        authService.validateUserAccess(order.getClient().getId());
        return entityToDto(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO orderDTO) {

        Order order = orderRepository.save(dtoToEntity(orderDTO));
        orderItemRepository.saveAll(order.getItems());

        return entityToDto(order);
    }

    private OrderDTO entityToDto(Order order) {

        UserMinDTO userMinDTO = UserMinDTO.builder()
                .id(order.getClient().getId())
                .name(order.getClient().getName())
                .build();

        PaymentDTO paymentDTO = order.getPayment() == null ? null : PaymentDTO.builder().id(order.getPayment().getId()).moment(order.getPayment().getMoment()).build();

        OrderDTO orderDTO = OrderDTO.builder()
                .id(order.getId())
                .moment(order.getMoment())
                .status(order.getStatus())
                .client(userMinDTO)
                .payment(paymentDTO)
                .build();

        for(OrderItem x : order.getItems()){

            OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                    .productId(x.getProduct().getId())
                    .name(x.getProduct().getName())
                    .price(x.getPrice())
                    .quantity(x.getQuantity())
                    .imgUrl(x.getProduct().getImgUrl())
                    .build();

            orderDTO.getItems().add(orderItemDTO);
        }

        return orderDTO;
    }

    private Order dtoToEntity(OrderDTO orderDTO){

        User user = userService.authenticated();

        Order order = Order.builder()
                .moment(Instant.now())
                .status(OrderStatus.WAITING_PAYMENT)
                .client(user)
                .build();

        for(OrderItemDTO x : orderDTO.getItems()){
            try{
                if(x.getProductId() == null){
                    throw new BodyRequestException("Product ID can't be null");
                }else if(x.getQuantity() == null || x.getQuantity() <= 0){

                    String errorMessage = x.getQuantity() == null ? "Quantity is a required field" : "The field quantity must be positive";

                    throw new BodyRequestException(errorMessage);
                }
                Product product = productRepository.getReferenceById(x.getProductId());
                OrderItem orderItem = new OrderItem(order,product,x.getQuantity(),product.getPrice());

                //Logic to update the quantity of a product if the same product is added to the items array more than 1 time
                /*
                if(order.getItems().contains(orderItem)){
                    for(OrderItem y : order.getItems()){
                        if(y.equals(orderItem)){
                            y.setQuantity(y.getQuantity() + orderItem.getQuantity());
                        }
                    }
                }*/
                order.getItems().add(orderItem);

            }catch(EntityNotFoundException e){
                throw new ResourceNotFoundException(x.getProductId());
            }
        }
        return order;
    }
}