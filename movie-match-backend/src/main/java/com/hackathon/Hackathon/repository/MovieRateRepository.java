package com.hackathon.Hackathon.repository;

import com.hackathon.Hackathon.model.MovieRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRateRepository extends JpaRepository<MovieRateEntity, Long> {
}
