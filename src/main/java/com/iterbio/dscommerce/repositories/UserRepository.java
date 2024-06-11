package com.iterbio.dscommerce.repositories;

import com.iterbio.dscommerce.entities.User;
import com.iterbio.dscommerce.projections.UserDetailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "SELECT tb_role.id AS roleId, email, authority, password FROM tb_user " +
            "INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id " +
            "INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id " +
            "WHERE email = :email")
    List<UserDetailProjection> searchUserAndRolesByEmail(String email);

    Optional<User> searchByEmail(String email);
}