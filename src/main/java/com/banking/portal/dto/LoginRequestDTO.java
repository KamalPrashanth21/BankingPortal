package com.banking.portal.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NonNull
    public String username;
    @NonNull
    public String password;
}
