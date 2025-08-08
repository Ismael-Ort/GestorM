package com.example.gestorm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gestorm.model.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {}
