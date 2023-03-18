package com.hackathon.Hackathon.service.implementation;

import com.hackathon.Hackathon.helper.RegisterUserDto;
import com.hackathon.Hackathon.helper.UserDto;
import com.hackathon.Hackathon.helper.UserResponseDto;
import com.hackathon.Hackathon.helper.mapper.UserMapper;
import com.hackathon.Hackathon.model.UserEntity;
import com.hackathon.Hackathon.repository.UserRepository;
import com.hackathon.Hackathon.service.UserService;
import com.hackathon.Hackathon.util.Utility;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper = new UserMapper();
    private final Utility utility;
    @Override
    public UserResponseDto register(RegisterUserDto registerUserDto){
        Optional<UserEntity> optUserEntity = userRepository.findByEmail(registerUserDto.getEmail());
        if(optUserEntity.isPresent()) {
            throw new IllegalArgumentException("This Email is already in use");
        }
        UserDto userDto = new UserDto();
        userDto.setFirstName(registerUserDto.getFirstName());
        userDto.setLastName(registerUserDto.getLastName());
        userDto.setEmail(registerUserDto.getEmail());
        userDto.setPassword(registerUserDto.getPassword());
        userDto.setRole(registerUserDto.getRole());
        UserEntity userEntity = userMapper.toEntity(userDto);
        UserEntity newUser = userRepository.save(userEntity);
        return userMapper.ToResponseUserDto(newUser);
    }

    @Override
    public UserResponseDto getCurrentUserDto() {
        return userMapper.ToResponseUserDto(utility.getCurrentUser());
    }
}
