package com.banking.portal.controller;

import com.banking.portal.Service.AuthService;
import com.banking.portal.dto.AuthResponseDTO;
import com.banking.portal.dto.LoginRequestDTO;
import com.banking.portal.dto.RegisterRequestDTO;
import com.banking.portal.entity.User;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //@Autowired
    private final AuthService authService; //fields are usually final to lock in the dependencies during constructor injection

    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/register")
    public ResponseEntity<@NotNull String> UserRegistration(@Valid @RequestBody RegisterRequestDTO registerRequestDTO){ //need to return a responseEntity incase of errors.
        try{
            User user = authService.registerUser(registerRequestDTO);
            return new ResponseEntity<>("User is registered successfully! Welcome, "+user.getUsername(), HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>("An exception has occurred!"+ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<@NotNull AuthResponseDTO> UserLogin(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        AuthResponseDTO authResponseDTO = authService.userLogin(loginRequestDTO);
        return ResponseEntity.ok(authResponseDTO);
    }
}
