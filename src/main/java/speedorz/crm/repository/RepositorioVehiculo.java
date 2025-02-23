package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedorz.crm.domain.Vehiculo;

import java.util.List;

public interface RepositorioVehiculo extends JpaRepository<Vehiculo, Long> {

    List<Vehiculo> findVehiculosByNombreContainsIgnoreCase(String nombre);
}
