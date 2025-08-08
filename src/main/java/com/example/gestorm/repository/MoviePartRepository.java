package com.example.gestorm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestorm.model.MoviePart;
import com.example.gestorm.model.Show;

public interface MoviePartRepository extends JpaRepository<MoviePart, Long> {
    List<MoviePart> findByShow(Show show);
}
