package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import speedorz.crm.domain.entities.OrdenCompra;

import java.util.List;

/**
 * Repositorio para la gestión de la entidad {@link OrdenCompra}.
 * Proporciona métodos para interactuar con la base de datos mediante JPA.
 */
public interface RepositorioOrdenCompra extends JpaRepository<OrdenCompra, Long> {

    @Query(value = """
    SELECT oc.* 
    FROM orden_compra oc 
    WHERE NOT EXISTS (
        SELECT 1 
        FROM factura f 
        WHERE f.id_orden_compra = oc.id_orden_compra
    )
    """, nativeQuery = true)
    List<OrdenCompra> findCuentasPorCobrar();


}
