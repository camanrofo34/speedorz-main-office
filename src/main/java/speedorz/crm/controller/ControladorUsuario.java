package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import speedorz.crm.domain.entities.Usuario;
import speedorz.crm.services.ServicioUsuario;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de usuarios.
 * Proporciona endpoints para CRUD y gestión de estados de usuarios.
 */
@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {

    private final ServicioUsuario servicioUsuario;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario, PasswordEncoder passwordEncoder) {
        this.servicioUsuario = servicioUsuario;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Crea un nuevo usuario con la contraseña encriptada.
     * Solo los administradores de usuarios pueden ejecutar esta acción.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINUSUARIOS')")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        Usuario respuesta = servicioUsuario.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    /**
     * Lista todos los usuarios registrados en el sistema.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINUSUARIOS')")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = servicioUsuario.listarUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Busca un usuario por su ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINUSUARIOS')")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = Optional.ofNullable(servicioUsuario.buscarUsuarioPorId(id));
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Busca usuarios por su nombre de usuario.
     */
    @GetMapping("/buscar")
    @PreAuthorize("hasRole('ADMINUSUARIOS')")
    public ResponseEntity<List<Usuario>> buscarUsuarioPorNombreUsuario(@RequestParam String nombreUsuario) {
        List<Usuario> usuarios = servicioUsuario.buscarUsuarioPorNombreUsuario(nombreUsuario);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Actualiza la información de un usuario.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINUSUARIOS')")
    public ResponseEntity<Void> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (!id.equals(usuario.getIdUsuario())) {
            return ResponseEntity.badRequest().build();
        }
        if (servicioUsuario.buscarUsuarioPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        servicioUsuario.actualizarUsuario(usuario);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina un usuario por su ID.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINUSUARIOS')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (servicioUsuario.buscarUsuarioPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        servicioUsuario.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Cambia el estado de un usuario (activo/inactivo).
     */
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMINUSUARIOS')")
    public ResponseEntity<Void> cambiarEstadoUsuario(@PathVariable Long id, @RequestParam String estado) {
        if (servicioUsuario.buscarUsuarioPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        servicioUsuario.cambiarEstadoUsuario(id, estado);
        return ResponseEntity.ok().build();
    }
}

