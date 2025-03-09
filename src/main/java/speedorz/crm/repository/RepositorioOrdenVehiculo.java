package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedorz.crm.domain.entities.OrdenVehiculo;

/**
 * Repositorio para la gestión de la entidad {@link OrdenVehiculo}.
 * Proporciona métodos para interactuar con la base de datos mediante JPA.
 */
public interface RepositorioOrdenVehiculo extends JpaRepository<OrdenVehiculo, Long> {
}
