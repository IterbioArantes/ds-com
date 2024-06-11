package com.iterbio.dscommerce.services;

import com.iterbio.dscommerce.DTO.UserDTO;
import com.iterbio.dscommerce.entities.Role;
import com.iterbio.dscommerce.entities.User;
import com.iterbio.dscommerce.projections.UserDetailProjection;
import com.iterbio.dscommerce.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        List<UserDetailProjection> list = userRepository.searchUserAndRolesByEmail(email);

        if(list.isEmpty()){
            throw new UsernameNotFoundException("Email not Found.");
        }

        User user =  User.builder()
                .email(list.get(0).getEmail())
                .password(list.get(0).getPassword())
                .build();

        list.forEach(x -> user.addRoles(new Role(x.getRoleId(),x.getAuthority())));

        return user;
    }

    protected User authenticated(){

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");

            return userRepository.searchByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email not Found."));

        }catch(Exception e){
            throw new UsernameNotFoundException("Authenticated failed.");
        }
    }

    @Transactional(readOnly = true)
    public UserDTO getMe(){

        User user = authenticated();
        return entityToDTO(user);
    }


    private UserDTO entityToDTO(User user){

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .build();

        user.getRoles().forEach(x -> userDTO.getRoles().add(x.getAuthority()));

        return userDTO;
    }

}