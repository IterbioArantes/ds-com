package com.iterbio.dscommerce.controllers;

import com.iterbio.dscommerce.DTO.OrderDTO;
import com.iterbio.dscommerce.DTO.ProductDTO;
import com.iterbio.dscommerce.DTO.ProductMinDTO;
import com.iterbio.dscommerce.services.AuthService;
import com.iterbio.dscommerce.services.OrderService;
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
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id){

        OrderDTO orderDTO = orderService.findById(id);

        return ResponseEntity.ok(orderDTO);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderDTO orderDTO){

        OrderDTO orderDTOInserted = orderService.insert(orderDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(orderDTOInserted.getId()).toUri();

        return ResponseEntity.created(uri).body(orderDTOInserted);
    }
}