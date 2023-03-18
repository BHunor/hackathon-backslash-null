package com.hackathon.Hackathon.controller;


import com.hackathon.Hackathon.config.TokenAuthenticationService;
import com.hackathon.Hackathon.config.UserAuthenticationProvider;
import com.hackathon.Hackathon.helper.LoginUserDto;
import com.hackathon.Hackathon.helper.RegisterUserDto;
import com.hackathon.Hackathon.service.UserService;
import com.hackathon.Hackathon.util.Utility;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
@CrossOrigin
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final Utility utility;
    private final TokenAuthenticationService tokenAuthenticationService;

    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto, HttpServletResponse httpServletResponse){
        try{
            UsernamePasswordAuthenticationToken usr = new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(),
                    loginUserDto.getPassword());
            Authentication auth = userAuthenticationProvider.authenticate(usr);

            tokenAuthenticationService.authenticationResponse(httpServletResponse, auth);
            SecurityContextHolder.getContext().setAuthentication(auth);

            return ResponseEntity.ok().body(userService.getCurrentUserDto());

        }catch (ServiceException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto){
        try{
            return ResponseEntity.ok().body(userService.register(registerUserDto));
        }catch (ServiceException | MessagingException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
