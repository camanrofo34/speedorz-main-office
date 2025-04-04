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

import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ServicioUsuarioImpl implements ServicioUsuario, UserDetailsService {

    private final RepositorioUsuario repositorioUsuario;
    private final Logger logger = Logger.getLogger(ServicioUsuarioImpl.class.getName());

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        try {
            logger.log(Level.INFO, "Iniciando el usuario");
            return repositorioUsuario.save(usuario);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al crear Usuario", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        try {
            logger.log(Level.INFO, "Actualizando el usuario {0}", usuario.getIdUsuario());
            Usuario newUsuario = buscarUsuarioPorId(usuario.getIdUsuario());
            newUsuario.setIdUsuario(usuario.getIdUsuario());
            newUsuario.setNombreCompleto(usuario.getNombreCompleto());
            newUsuario.setTelefono(usuario.getTelefono());
            newUsuario.setDireccion(usuario.getDireccion());
            newUsuario.setRol(usuario.getRol());
            repositorioUsuario.save(usuario);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al actualizar Usuario", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminarUsuario(Long id) {
        try {
            logger.log(Level.INFO, "Eliminando el usuario {0}", id);
            repositorioUsuario.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al eliminar Usuario", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Usuario> listarUsuarios() {
        try {
            logger.log(Level.INFO, "Listando Usuarios");
            return repositorioUsuario.findAll();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al listar Usuarios", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        try {
            logger.log(Level.INFO, "Buscando Usuario por ID {0}", id);
            return repositorioUsuario.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar Usuario por ID", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Usuario> buscarUsuarioPorNombreUsuario(String nombreUsuario) {
        try {
            logger.log(Level.INFO, "Buscando Usuario por nombre de usuario {0}", nombreUsuario);
            String nombreUsuarioBusqueda = NormalizadorBusquedaUtil.normalizarTexto(nombreUsuario);
            return repositorioUsuario.findUsuariosByNombreCompletoContainsIgnoreCase(nombreUsuarioBusqueda);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar Usuario por nombre de usuario", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cambiarEstadoUsuario(Long id, String estado) {
        try {
            logger.log(Level.INFO, "Cambiando el estado del usuario {0}", id);
            Usuario usuario = buscarUsuarioPorId(id);
            usuario.setEstado(estado);
            repositorioUsuario.save(usuario);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al cambiar el estado del Usuario", e);
            throw new RuntimeException(e);
        }
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

    @Override
    public boolean sendRecoveryCode(String email) {
        try {
            Usuario usuario = repositorioUsuario.findUsuarioByNombreUsuario(email);
            if (usuario == null) {
                return false; // Usuario no encontrado
            }

            // Genera un código de recuperación de 6 dígitos
            String recoveryCode = String.format("%06d", new Random().nextInt(999999));
            usuario.setRecoveryCode(recoveryCode);

            // Establece la fecha de expiración del código (15 minutos desde ahora)
            Date expirationDate = new Date(System.currentTimeMillis() + 15 * 60 * 1000);
            usuario.setRecoveryCodeExpiration(expirationDate);

            repositorioUsuario.save(usuario);

            // Log para pruebas
            logger.log(Level.INFO, "Código de recuperación generado para {0}: {1}", new Object[]{email, recoveryCode});

            return true; // Operación exitosa
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al generar el código de recuperación", e);
            return false; // Error durante la operación
        }
    }

    @Override
    public boolean resetPassword(String email, String recoveryCode, String newPassword) {
        try {
            Usuario usuario = repositorioUsuario.findUsuarioByNombreUsuario(email);
            if (usuario == null || !isRecoveryCodeValid(usuario, recoveryCode)) {
                return false; // Código de recuperación inválido o usuario no encontrado
            }

            // Actualiza la contraseña del usuario
            usuario.setContrasena(newPassword); // Asegúrate de cifrar la contraseña antes de guardarla
            usuario.setRecoveryCode(null); // Limpia el código de recuperación
            usuario.setRecoveryCodeExpiration(null); // Limpia la fecha de expiración
            repositorioUsuario.save(usuario);

            return true; // Operación exitosa
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al restablecer la contraseña", e);
            return false; // Error durante la operación
        }
    }

    private boolean isRecoveryCodeValid(Usuario usuario, String recoveryCode) {
        return recoveryCode.equals(usuario.getRecoveryCode()) && !isRecoveryCodeExpired(usuario);
    }

    private boolean isRecoveryCodeExpired(Usuario usuario) {
        return usuario.getRecoveryCodeExpiration().before(new Date());
    }
}