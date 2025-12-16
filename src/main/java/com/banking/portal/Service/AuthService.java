package com.banking.portal.Service;

import com.banking.portal.dto.AuthResponseDTO;
import com.banking.portal.dto.LoginRequestDTO;
import com.banking.portal.dto.RegisterRequestDTO;
import com.banking.portal.dto.RegisterResponseDTO;

public interface AuthService {

    RegisterResponseDTO registerUser(RegisterRequestDTO registerRequestDTO);
    AuthResponseDTO userLogin(LoginRequestDTO loginRequestDTO);

}
