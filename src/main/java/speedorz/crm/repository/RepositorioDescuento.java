package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedorz.crm.domain.Descuento;

import java.util.List;

public interface RepositorioDescuento extends JpaRepository<Descuento, Long> {

    List<Descuento> findDescuentosByNombreContainsIgnoreCase(String nombre);

}
