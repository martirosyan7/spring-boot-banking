package com.banking.springbootbanking.dto.mapper;

import com.banking.springbootbanking.dto.LocalUserDTO;
import com.banking.springbootbanking.model.LocalUser;

public class LocalUserMapper {
public static LocalUser mapToUser(LocalUserDTO userDto) {
        LocalUser user = new LocalUser(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getAddress(),
                userDto.getCards(),
                userDto.getAccounts()
        );
        return user;
    }

    public static LocalUserDTO mapToUserDto(LocalUser user) {
        LocalUserDTO userDto = new LocalUserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getAddress(),
                user.getCards(),
                user.getAccounts()
        );
        return userDto;
    }
}
