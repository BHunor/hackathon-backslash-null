package com.hackathon.Hackathon.service.implementation;

import com.hackathon.Hackathon.helper.MovieRateDto;
import com.hackathon.Hackathon.helper.mapper.MovieRateMapper;
import com.hackathon.Hackathon.helper.mapper.UserMapper;
import com.hackathon.Hackathon.model.MovieRateEntity;
import com.hackathon.Hackathon.model.UserEntity;
import com.hackathon.Hackathon.repository.MovieRateRepository;
import com.hackathon.Hackathon.repository.UserRepository;
import com.hackathon.Hackathon.service.MovieRateService;
import com.hackathon.Hackathon.util.Utility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieRateServiceImpl implements MovieRateService {
    private final UserRepository userRepository;
    private final MovieRateRepository movieRateRepository;
    private final MovieRateMapper movieRateMapper = new MovieRateMapper();
    private final Utility utility;
    @Override
    public MovieRateDto create(MovieRateDto movieRateDto) {
        Optional<UserEntity> optUserEntity = userRepository.findById(utility.getCurrentUser().getId());
        if(optUserEntity.isEmpty()) {
            throw new IllegalArgumentException("This user does not exist!");
        }
        if(movieRateDto.getRate() < 1 || movieRateDto.getRate() > 10){
            throw new IllegalArgumentException("This rate is not valid!");
        }
        MovieRateEntity movieRateEntity = movieRateMapper.toEntity(movieRateDto);
        movieRateEntity.setUserEntity(utility.getCurrentUser());
        return movieRateMapper.toDto(movieRateRepository.save(movieRateEntity));
    }
}
