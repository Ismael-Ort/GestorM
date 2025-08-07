import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ObraService {

    private final ObraRepository obraRepository;

    @Autowired
    public ObraService(ObraRepository obraRepository) {
        this.obraRepository = obraRepository;
    }

    public List<Obra> findAll() {
        return obraRepository.findAll();
    }

    public Optional<Obra> findById(Long id) {
        return obraRepository.findById(id);
    }

    public Obra save(Obra obra) {
        return obraRepository.save(obra);
    }

    public Obra update(Long id, Obra obraDetails) {
        Obra obra = obraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Obra not found with id " + id));
        obra.setTitulo(obraDetails.getTitulo());
        obra.setDescripcion(obraDetails.getDescripcion());
        return obraRepository.save(obra);
    }
}