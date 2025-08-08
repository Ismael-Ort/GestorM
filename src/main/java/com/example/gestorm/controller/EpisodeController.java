package com.example.gestorm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gestorm.model.Episode;
import com.example.gestorm.model.Show;
import com.example.gestorm.repository.EpisodeRepository;
import com.example.gestorm.repository.ShowRepository;

@RestController
@RequestMapping("/api/episodes")
@CrossOrigin
public class EpisodeController {
    private final EpisodeRepository episodeRepository;
    private final ShowRepository showRepository;

    public EpisodeController(EpisodeRepository episodeRepository, ShowRepository showRepository) {
        this.episodeRepository = episodeRepository;
        this.showRepository = showRepository;
    }

    @GetMapping
    public List<Episode> all() {
        return episodeRepository.findAll();
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<Episode>> byShow(@PathVariable Long showId) {
        return showRepository.findById(showId)
            .map(show -> ResponseEntity.ok(episodeRepository.findByShow(show)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Episode> create(@RequestParam Long showId, @RequestBody Episode episode) {
        return showRepository.findById(showId)
            .map(show -> {
                episode.setShow(show);
                return ResponseEntity.ok(episodeRepository.save(episode));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Episode> update(@PathVariable Long id, @RequestBody Episode episode) {
        return episodeRepository.findById(id)
            .map(existing -> {
                existing.setTitle(episode.getTitle());
                existing.setEpisodeNo(episode.getEpisodeNo());
                existing.setDuration(episode.getDuration());
                return ResponseEntity.ok(episodeRepository.save(existing));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (episodeRepository.existsById(id)) {
            episodeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
