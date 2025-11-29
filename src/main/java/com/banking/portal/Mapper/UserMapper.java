package com.banking.portal.Mapper;

import com.banking.portal.dto.AuthResponseDTO;
import com.banking.portal.dto.RegisterRequestDTO;
import com.banking.portal.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.awt.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", constant = "CUSTOMER")
    @Mapping(target = "password", ignore = true)
    User userDTOtoUser(RegisterRequestDTO registerRequestDTO);

//    @Mapping(target = "", source = "")
//    AuthResponseDTO userToAuthResponseDTO(User user);
}
