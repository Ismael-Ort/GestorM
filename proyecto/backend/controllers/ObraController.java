import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @GetMapping
    public List<Obra> getObras() {
        return obraService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obra> getObraById(@PathVariable Long id) {
        Obra obra = obraService.findById(id);
        if (obra != null) {
            return ResponseEntity.ok(obra);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Obra createObra(@RequestBody Obra obra) {
        return obraService.save(obra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Obra> updateObra(@PathVariable Long id, @RequestBody Obra obraDetails) {
        Obra updatedObra = obraService.update(id, obraDetails);
        if (updatedObra != null) {
            return ResponseEntity.ok(updatedObra);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}