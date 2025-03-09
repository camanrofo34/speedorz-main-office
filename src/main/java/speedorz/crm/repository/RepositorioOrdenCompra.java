package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedorz.crm.domain.entities.OrdenCompra;

/**
 * Repositorio para la gestión de la entidad {@link OrdenCompra}.
 * Proporciona métodos para interactuar con la base de datos mediante JPA.
 */
public interface RepositorioOrdenCompra extends JpaRepository<OrdenCompra, Long> {

}
