package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedorz.crm.domain.entities.OrdenVehiculo;

public interface RepositorioOrdenVehiculo extends JpaRepository<OrdenVehiculo, Long> {
}
