package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import speedorz.crm.domain.entities.Impuesto;

/**
 * Repositorio para la gestión de la entidad {@link Impuesto}.
 * Proporciona métodos para interactuar con la base de datos mediante JPA.
 */
@Repository
public interface RepositorioImpuesto extends JpaRepository<Impuesto, Long> {
}
