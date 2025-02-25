package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import speedorz.crm.domain.Factura;

@Repository
public interface RepositorioFactura extends JpaRepository<Factura, Long> {
}
