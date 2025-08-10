package com.example.gestorm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    public Page<Show> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "title,asc") String sort,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {

        String[] sortParts = sort.split(",");
        Sort sortObj = Sort.by(sortParts[0]);
        if (sortParts.length > 1 && sortParts[1].equalsIgnoreCase("desc")) {
            sortObj = sortObj.descending();
        }
        Pageable pageable = PageRequest.of(page, pageSize, sortObj);
        return showRepository.findAllFiltered(type, status, search, pageable);
    }

    @GetMapping("/stats")
    public Map<String, Map<String, Long>> stats() {
        Map<String, Map<String, Long>> stats = new HashMap<>();
        stats.put("pelicula", buildTypeStats("pelicula"));
        stats.put("serie", buildTypeStats("serie"));
        stats.put("anime", buildTypeStats("anime"));
        return stats;
    }

    private Map<String, Long> buildTypeStats(String type) {
        Map<String, Long> m = new HashMap<>();
        m.put("total", showRepository.countByTypeIgnoreCase(type));
        m.put("visto", showRepository.countByTypeAndStatusIgnoreCase(type, "visto"));
        m.put("viendo", showRepository.countByTypeAndStatusIgnoreCase(type, "viendo"));
        m.put("favorito", showRepository.countByTypeAndStatusIgnoreCase(type, "favorito"));
        m.put("pendiente", showRepository.countByTypeAndStatusIgnoreCase(type, "pendiente"));
        return m;
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
