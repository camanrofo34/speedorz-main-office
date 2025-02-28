package speedorz.crm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import speedorz.crm.domain.Usuario;

import java.util.List;
import java.util.Optional;


@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    List<Usuario> findUsuariosByNombreCompletoContainsIgnoreCase(String nombreUsuario);

    Usuario findUsuarioByNombreUsuario(String nombreUsuario);
}
