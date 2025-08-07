package proyecto.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.backend.models.Obra;

public interface ObraRepository extends JpaRepository<Obra, Long> {
    // Additional query methods can be defined here if needed
}