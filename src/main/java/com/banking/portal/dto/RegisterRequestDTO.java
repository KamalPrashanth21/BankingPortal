package com.banking.portal.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    @NonNull
    public String username;

    @NonNull
    public String password;

    @NonNull
    public String firstName;

    @NonNull
    public String lastName;

    @NonNull
    @Email
    public String email;

    @NonNull
    public Long phone;

}
