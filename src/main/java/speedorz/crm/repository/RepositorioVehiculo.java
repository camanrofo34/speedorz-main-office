package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedorz.crm.domain.entities.Vehiculo;

import java.util.List;

/**
 * Repositorio para la gestión de la entidad {@link Vehiculo}.
 * Proporciona métodos para interactuar con la base de datos mediante JPA.
 */
public interface RepositorioVehiculo extends JpaRepository<Vehiculo, Long> {

    /**
     * Busca vehículos cuyo nombre comercial contenga la cadena especificada, sin distinguir mayúsculas de minúsculas.
     *
     * @param nombre Parte del nombre de vehículo a buscar.
     * @return Lista de vehículos que coinciden con la búsqueda.
     */
    List<Vehiculo> findVehiculosByNombreContainsIgnoreCase(String nombre);
}
