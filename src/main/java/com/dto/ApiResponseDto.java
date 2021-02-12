package com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDto {

    private int code;
    private String message;
    private String type;
}
