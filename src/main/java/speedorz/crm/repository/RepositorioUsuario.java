package speedorz.crm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import speedorz.crm.domain.Usuario;

import java.util.List;


@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    List<Usuario> findUsuariosByNombreUsuarioContainsIgnoreCase(String nombreUsuario);

    Usuario findUsuarioByNombreUsuario(String nombreUsuario);
}
