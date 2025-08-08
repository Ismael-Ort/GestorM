package com.example.gestorm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gestorm.model.MoviePart;
import com.example.gestorm.repository.MoviePartRepository;
import com.example.gestorm.repository.ShowRepository;

@RestController
@RequestMapping("/api/movie-parts")
@CrossOrigin
public class MoviePartController {
    private final MoviePartRepository partRepository;
    private final ShowRepository showRepository;

    public MoviePartController(MoviePartRepository partRepository, ShowRepository showRepository) {
        this.partRepository = partRepository;
        this.showRepository = showRepository;
    }

    @GetMapping
    public List<MoviePart> all() {
        return partRepository.findAll();
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<MoviePart>> byShow(@PathVariable Long showId) {
        return showRepository.findById(showId)
            .map(show -> ResponseEntity.ok(partRepository.findByShow(show)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MoviePart> create(@RequestParam Long showId, @RequestBody MoviePart part) {
        return showRepository.findById(showId)
            .map(show -> {
                part.setShow(show);
                return ResponseEntity.ok(partRepository.save(part));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoviePart> update(@PathVariable Long id, @RequestBody MoviePart part) {
        return partRepository.findById(id)
            .map(existing -> {
                existing.setTitle(part.getTitle());
                existing.setPartNo(part.getPartNo());
                existing.setDuration(part.getDuration());
                return ResponseEntity.ok(partRepository.save(existing));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (partRepository.existsById(id)) {
            partRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
