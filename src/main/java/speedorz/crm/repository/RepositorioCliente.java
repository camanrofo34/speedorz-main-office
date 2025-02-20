package speedorz.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import speedorz.crm.domain.Cliente;

import java.util.List;

@Repository
public interface RepositorioCliente extends JpaRepository<Cliente, Long> {

    List<Cliente> findClienteByNombreLegal(String nombreLegal);

}
