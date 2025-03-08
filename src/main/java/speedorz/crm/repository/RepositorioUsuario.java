package speedorz.crm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import speedorz.crm.domain.entities.Descuento;
import speedorz.crm.domain.entities.Usuario;

import java.util.List;

/**
 * Repositorio para la gestión de la entidad {@link Usuario}.
 * Proporciona métodos para interactuar con la base de datos mediante JPA.
 */
@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    /**
     * Busca usuarios cuyo nombre de usuario contenga la cadena especificada, sin distinguir mayúsculas de minúsculas.
     *
     * @param nombreUsuario Parte del nombre de usuario a buscar.
     * @return Lista de usuarios que coinciden con la búsqueda.
     */
    List<Usuario> findUsuariosByNombreCompletoContainsIgnoreCase(String nombreUsuario);

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param nombreUsuario Nombre de usuario a buscar.
     * @return Usuario encontrado o null si no existe.
     */
    Usuario findUsuarioByNombreUsuario(String nombreUsuario);
}
