package speedorz.crm.services;

import org.springframework.security.core.userdetails.UserDetails;
import speedorz.crm.domain.Usuario;

import java.util.List;

public interface ServicioUsuario {
    Usuario crearUsuario(String nombreUsuario, String contrasena, String nombreCompleto, String cedula, String direccion, String telefono, String rol, String estado);

    void actualizarUsuario(Long id, String nombreUsuario, String contrasena, String nombreCompleto, String cedula, String rol, String estado);

    void eliminarUsuario(Long id);

    List<Usuario> listarUsuarios();

    Usuario buscarUsuarioPorId(Long id);

    List<Usuario> buscarUsuarioPorNombreUsuario(String nombreUsuario);

    void cambiarEstadoUsuario(Long id, String estado);

}
