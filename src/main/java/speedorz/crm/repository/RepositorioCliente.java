package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import speedorz.crm.domain.entities.Cliente;

import java.util.List;

/**
 * Repositorio para la gestión de la entidad {@link Cliente}.
 * Proporciona métodos para interactuar con la base de datos mediante JPA.
 */
@Repository
public interface RepositorioCliente extends JpaRepository<Cliente, Long> {

    /**
     * Busca clientes cuyo nombre legal contenga la cadena especificada, sin distinguir mayúsculas de minúsculas.
     *
     * @param nombreLegal Parte del nombre legal a buscar.
     * @return Lista de clientes que coinciden con la búsqueda.
     */
    List<Cliente> findClientesByNombreLegalContainsIgnoreCase(String nombreLegal);
}
