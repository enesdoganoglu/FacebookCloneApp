package com.enes.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginResponseDto {
    Integer statusCode;
    String message;

}
