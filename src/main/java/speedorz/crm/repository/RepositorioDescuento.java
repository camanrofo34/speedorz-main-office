package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedorz.crm.domain.entities.Descuento;

/**
 * Repositorio para la gestión de la entidad {@link Descuento}.
 * Proporciona métodos para interactuar con la base de datos mediante JPA.
 */
public interface RepositorioDescuento extends JpaRepository<Descuento, Long> {

}
