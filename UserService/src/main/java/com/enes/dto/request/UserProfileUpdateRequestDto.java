package com.enes.dto.request;

import com.enes.repository.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserProfileUpdateRequestDto {
    Long authid;
    String name;
    String surname;
    String phone;
    String address;
    String avatar;
    Gender gender;
}
