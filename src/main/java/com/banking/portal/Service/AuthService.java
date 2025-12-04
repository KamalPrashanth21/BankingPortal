package com.banking.portal.Service;

import com.banking.portal.Mapper.UserMapper;
import com.banking.portal.annotations.AuditUserCreation;
import com.banking.portal.dto.AuthResponseDTO;
import com.banking.portal.dto.LoginRequestDTO;
import com.banking.portal.dto.RegisterRequestDTO;
import com.banking.portal.dto.RegisterResponseDTO;
import com.banking.portal.entity.User;
import com.banking.portal.exception.EmailAlreadyExistsException;
import com.banking.portal.exception.UsernameAlreadyExistsException;
import com.banking.portal.repository.UserRepository;
import com.banking.portal.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private JwtUtil jwtUtil; //Fiedl Injection

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, UserMapper userMapper, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
        this.authenticationManager=authenticationManager;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil=jwtUtil;
    }

    @AuditUserCreation
    public RegisterResponseDTO registerUser(RegisterRequestDTO registerRequestDTO){
            if(userRepository.existsByUsername(registerRequestDTO.getUsername())){
                throw new UsernameAlreadyExistsException("Username already exists!");
            }
            if(userRepository.existsByEmail(registerRequestDTO.getEmail())){
                throw new EmailAlreadyExistsException("Email already exists!");
            }
            //now this means that the current user is a new user
        User userEntity = userMapper.userDTOtoUser(registerRequestDTO); //converted into a user entity
        //String encodedUserEntity - The userEntity should not contain raw passwords
        userEntity.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword())); //So while mapping password is ignored & set only after encoding
        User user = userRepository.save(userEntity); //save method returns back the userEntity
        return userMapper.toDTO(user);
    }

    public AuthResponseDTO userLogin(LoginRequestDTO loginRequestDTO){
//        if(!userRepository.existsByUsername(loginRequestDTO.getUsername())){
//            throw new IllegalArgumentException("User doesn't exist");
//        } As the authenticationManager handles invalid cases by throwing exceptions
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),loginRequestDTO.getPassword())); //We are not handling the returned object since, incase of any exceptions, the authenticate method detects & throws it.
        //The primary objective of a valid user is enough for us to generate a token.
        String token = jwtUtil.generateToken(loginRequestDTO.getUsername());
        return new AuthResponseDTO(token);
    }
}
