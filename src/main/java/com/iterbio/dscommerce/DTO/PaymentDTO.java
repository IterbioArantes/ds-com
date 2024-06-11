package com.iterbio.dscommerce.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class PaymentDTO {

    private Long id;
    private Instant moment;
}
