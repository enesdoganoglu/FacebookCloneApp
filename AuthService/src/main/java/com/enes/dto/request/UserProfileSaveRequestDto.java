package com.enes.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserProfileSaveRequestDto {
    Long authid;
    String username;
    String email;
}
