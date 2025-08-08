package com.example.gestorm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestorm.model.Episode;
import com.example.gestorm.model.Show;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    List<Episode> findByShow(Show show);
}
