package com.hackathon.Hackathon.helper.mapper;

import com.hackathon.Hackathon.helper.UserDto;
import com.hackathon.Hackathon.helper.UserResponseDto;
import com.hackathon.Hackathon.helper.UserReturnDto;
import com.hackathon.Hackathon.model.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public UserEntity toEntity(UserDto userDto){
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userDto.getId());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setRole(userDto.getRole() == null ? "user" : userDto.getRole());

        return userEntity;
    }

    public String passwordBCryptEncoder(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public List<UserEntity> toEntityList(List<UserDto> userDtos){
        return userDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public UserDto toDto(UserEntity userEntity){
        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setRole(userEntity.getRole());
        userDto.setDateOfBirth(userEntity.getDateOfBirth());
        return userDto;
    }

    public List<UserDto> toDtoList(List<UserEntity> userEntity){
        return userEntity.stream().map(this::toDto).collect(Collectors.toList());
    }


    public UserResponseDto ToResponseUserDto(UserEntity userEntity){
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(userEntity.getId());
        userResponseDto.setEmail(userEntity.getEmail());
        userResponseDto.setFirstName(userEntity.getFirstName());
        userResponseDto.setLastName(userEntity.getLastName());
        userResponseDto.setDateOfBirth(userEntity.getDateOfBirth());
        return userResponseDto;
    }

    public UserReturnDto ToUserReturnDto(UserEntity userEntity){
        UserReturnDto userReturnDto = new UserReturnDto();

        userReturnDto.setId(userEntity.getId());
        userReturnDto.setEmail(userEntity.getEmail());
        userReturnDto.setFirstName(userEntity.getFirstName());
        userReturnDto.setLastName(userEntity.getLastName());
        userReturnDto.setRole(userEntity.getRole());
        userReturnDto.setDateOfBirth(userEntity.getDateOfBirth());
        return userReturnDto;
    }

    public List<UserReturnDto> ToUserReturnDtoList(List<UserEntity> userEntities){
        return userEntities.stream().map(this::ToUserReturnDto).collect(Collectors.toList());
    }
}
