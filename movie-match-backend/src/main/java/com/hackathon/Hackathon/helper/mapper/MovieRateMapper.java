package com.hackathon.Hackathon.helper.mapper;

import com.hackathon.Hackathon.helper.MovieRateDto;
import com.hackathon.Hackathon.helper.UserDto;
import com.hackathon.Hackathon.model.MovieRateEntity;
import com.hackathon.Hackathon.model.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MovieRateMapper {
    public MovieRateEntity toEntity(MovieRateDto movieRateDto){
        MovieRateEntity movieRateEntity = new MovieRateEntity();
        movieRateEntity.setId(movieRateDto.getId());
        movieRateEntity.setMovieId(movieRateDto.getMovieId());
        movieRateEntity.setUserEntity(movieRateDto.getUserEntity());
        movieRateEntity.setRate(movieRateDto.getRate());
        return movieRateEntity;
    }

    public MovieRateDto toDto(MovieRateEntity movieRateEntity){
        MovieRateDto movieRateDto = new MovieRateDto();
        movieRateDto.setId(movieRateEntity.getId());
        movieRateDto.setMovieId(movieRateEntity.getMovieId());
        movieRateDto.setUserEntity(movieRateEntity.getUserEntity());
        movieRateDto.setRate(movieRateEntity.getRate());
        return movieRateDto;
    }
}
