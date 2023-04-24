package com.enes.dto.request;

import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterRequestDto {
    @NotEmpty
    @Size(min = 3)
    String username;
    @NotBlank
    @Size(min = 8)

    String password;
    String repassowrd;
    @Email
    String email;
}
