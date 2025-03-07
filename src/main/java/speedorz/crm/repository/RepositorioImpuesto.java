package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import speedorz.crm.domain.entities.Impuesto;

import java.util.List;

@Repository
public interface RepositorioImpuesto extends JpaRepository<Impuesto, Long> {

    List<Impuesto> findImpuestosByNombreContainsIgnoreCase(String nombre);

}
