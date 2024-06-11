package com.iterbio.dscommerce.repositories;

import com.iterbio.dscommerce.entities.Category;
import com.iterbio.dscommerce.entities.User;
import com.iterbio.dscommerce.projections.UserDetailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}