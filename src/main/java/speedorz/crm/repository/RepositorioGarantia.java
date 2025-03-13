package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import speedorz.crm.domain.entities.Garantia;

/**
 * Repositorio para la gestión de la entidad {@link Garantia}.
 * Proporciona métodos para interactuar con la base de datos mediante JPA.
 */
@Repository
public interface RepositorioGarantia extends JpaRepository<Garantia, Long> {
}
