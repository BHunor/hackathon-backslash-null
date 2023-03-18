package com.hackathon.Hackathon.controller;

import com.hackathon.Hackathon.helper.MovieRateDto;
import com.hackathon.Hackathon.helper.RegisterUserDto;
import com.hackathon.Hackathon.service.MovieRateService;
import com.hackathon.Hackathon.service.UserService;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;

@Controller
@RequestMapping("/movierate")
@CrossOrigin
@AllArgsConstructor
public class MovieRateController {


    private final MovieRateService movieRateService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody MovieRateDto movieRateDto){
        try{
            return ResponseEntity.ok().body(movieRateService.create(movieRateDto));
        }catch (ServiceException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
