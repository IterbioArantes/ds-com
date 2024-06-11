package com.iterbio.dscommerce.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldMessageDTO {

    private String fieldName;
    private String fieldMessage;
}
