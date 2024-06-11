package com.iterbio.dscommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@NoArgsConstructor
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "categories")
    private final Set<Product> products = new HashSet<>();

    public Category(Long id){
        this.id = id;
    }
}
