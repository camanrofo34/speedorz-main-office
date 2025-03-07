package speedorz.crm.services;

import speedorz.crm.domain.entities.Usuario;

import java.util.List;

public interface ServicioUsuario {
    Usuario crearUsuario(Usuario usuario);

    void actualizarUsuario(Usuario usuario);

    void eliminarUsuario(Long id);

    List<Usuario> listarUsuarios();

    Usuario buscarUsuarioPorId(Long id);

    List<Usuario> buscarUsuarioPorNombreUsuario(String nombreUsuario);

    void cambiarEstadoUsuario(Long id, String estado);

}
