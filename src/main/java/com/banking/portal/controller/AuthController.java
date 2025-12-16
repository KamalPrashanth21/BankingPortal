package com.banking.portal.controller;

import com.banking.portal.Service.impl.AuthServiceImpl;
import com.banking.portal.dto.AuthResponseDTO;
import com.banking.portal.dto.LoginRequestDTO;
import com.banking.portal.dto.RegisterRequestDTO;
import com.banking.portal.dto.RegisterResponseDTO;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //@Autowired
    private final AuthServiceImpl authService; //fields are usually final to lock in the dependencies during constructor injection

    public AuthController(AuthServiceImpl authService){
        this.authService=authService;
    }

    @PostMapping("/register")
    public ResponseEntity<@NotNull Map<String, Object>> UserRegistration(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) { //need to return a responseEntity incase of errors.
        RegisterResponseDTO registerResponseDTO  = authService.registerUser(registerRequestDTO);
        return new ResponseEntity<>(Map.of("message","User registered successfully!","user", registerResponseDTO), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<@NotNull AuthResponseDTO> UserLogin(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        AuthResponseDTO authResponseDTO = authService.userLogin(loginRequestDTO);
        return ResponseEntity.ok(authResponseDTO);
    }
}
