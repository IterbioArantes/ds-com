package com.iterbio.dscommerce.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserMinDTO {

    private Long id;
    private String name;
}