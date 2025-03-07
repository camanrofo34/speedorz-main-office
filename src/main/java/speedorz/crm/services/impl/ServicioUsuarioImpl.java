package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.Usuario;
import speedorz.crm.repository.RepositorioUsuario;
import speedorz.crm.services.ServicioUsuario;
import speedorz.crm.util.NormalizadorBusquedaUtil;

import java.util.List;

@Service
public class ServicioUsuarioImpl implements ServicioUsuario, UserDetailsService {

    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return repositorioUsuario.save(usuario);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        Usuario newUsuario = repositorioUsuario.findById(usuario.getIdUsuario()).orElseThrow();
        newUsuario.setIdUsuario(usuario.getIdUsuario());
        newUsuario.setNombreCompleto(usuario.getNombreCompleto());
        newUsuario.setTelefono(usuario.getTelefono());
        newUsuario.setDireccion(usuario.getDireccion());
        newUsuario.setRol(usuario.getRol());
        repositorioUsuario.save(usuario);
    }

    @Override
    public void eliminarUsuario(Long id) {
        repositorioUsuario.deleteById(id);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return repositorioUsuario.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        return repositorioUsuario.findById(id).orElseThrow();
    }

    @Override
    public List<Usuario> buscarUsuarioPorNombreUsuario(String nombreUsuario) {
        String nombreUsuarioBusqueda = NormalizadorBusquedaUtil.normalizarTexto(nombreUsuario);
        return repositorioUsuario.findUsuariosByNombreCompletoContainsIgnoreCase(nombreUsuarioBusqueda);
    }

    @Override
    public void cambiarEstadoUsuario(Long id, String estado) {
        Usuario usuario = repositorioUsuario.findById(id).orElseThrow();
        usuario.setEstado(estado);
        repositorioUsuario.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repositorioUsuario.findUsuarioByNombreUsuario(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        return new User(
                usuario.getNombreUsuario(),
                usuario.getContrasena(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()))
        );
    }
}
