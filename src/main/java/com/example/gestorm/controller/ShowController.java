package com.example.gestorm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gestorm.model.Show;
import com.example.gestorm.repository.ShowRepository;

@RestController
@RequestMapping("/api/shows")
@CrossOrigin
public class ShowController {
    private final ShowRepository showRepository;

    public ShowController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @GetMapping
    public List<Show> all() {
        return showRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> get(@PathVariable Long id) {
        return showRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Show create(@RequestBody Show show) {
        return showRepository.save(show);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Show> update(@PathVariable Long id, @RequestBody Show show) {
        return showRepository.findById(id)
            .map(existing -> {
                existing.setTitle(show.getTitle());
                existing.setType(show.getType());
                existing.setStatus(show.getStatus());
                existing.setDescription(show.getDescription());
                return ResponseEntity.ok(showRepository.save(existing));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (showRepository.existsById(id)) {
            showRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
