package com.example.gestorm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gestorm.model.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("SELECT s FROM Show s WHERE (:type IS NULL OR s.type = :type) "
            + "AND (:status IS NULL OR s.status = :status) "
            + "AND (:search IS NULL OR LOWER(s.title) LIKE LOWER(CONCAT('%', :search, '%')))" )
    Page<Show> findAllFiltered(@Param("type") String type,
                               @Param("status") String status,
                               @Param("search") String search,
                               Pageable pageable);

    long countByTypeIgnoreCase(String type);

    long countByTypeAndStatusIgnoreCase(String type, String status);
}
