package com.iterbio.dscommerce.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorDTO extends ErrorResponseDTO{

    private final List<FieldMessageDTO> listFieldMessage = new ArrayList<>();

    public ValidationErrorDTO(Instant timestamp, int status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public void addFieldMessage(String fieldName, String fieldMessage){

        listFieldMessage.removeIf(x -> x.getFieldName().equals(fieldName));

        listFieldMessage.add(FieldMessageDTO.builder()
                .fieldName(fieldName)
                .fieldMessage(fieldMessage)
                .build());
    }

}
