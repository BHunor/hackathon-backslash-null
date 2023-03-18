package com.hackathon.Hackathon.service;

import com.hackathon.Hackathon.helper.RegisterUserDto;
import com.hackathon.Hackathon.helper.UserResponseDto;

import javax.mail.MessagingException;

public interface UserService {
    UserResponseDto register(RegisterUserDto registerUserDto) throws MessagingException;
    UserResponseDto getCurrentUserDto();
}
