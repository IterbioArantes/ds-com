package com.iterbio.dscommerce.services;


import com.iterbio.dscommerce.entities.Order;
import com.iterbio.dscommerce.entities.User;
import com.iterbio.dscommerce.repositories.OrderRepository;
import com.iterbio.dscommerce.services.exceptions.ForbiddenException;
import com.iterbio.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    public void validateUserAccess(Long orderUserId){

        User user = userService.authenticated();

        if(!user.hasRole("ROLE_ADMIN") && !Objects.equals(user.getId(), orderUserId)){
            throw new ForbiddenException("User doesn't have permission to access this Order.");
        }

    }
}