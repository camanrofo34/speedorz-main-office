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
 * <p>
 *     Proporciona endpoints para CRUD y gestión de estados de usuarios.
 * </p>
 *
 * @author Camilo
 * @version 1.0
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
     * Crea un nuevo usuario en el sistema.
     *
     * <p>
     *     Solo accesible para usuarios con el rol "ADMINUSUARIOS".
     *     La contraseña del usuario se encripta antes de ser almacenada en la base de datos.
     *     Se retorna el usuario creado con estado HTTP 201 (CREATED).
     * </p>
     *
     * @return `ResponseEntity<Usuario>` con el usuario creado y estado HTTP 201 (CREATED).
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
     *
     * <p>
     *     Accesible para usuarios con el rol "ADMINUSUARIOS".
     *     Se retorna una lista de usuarios con estado HTTP 200 (OK).
     *     Si no hay usuarios registrados, se retorna un estado HTTP 204 (NO CONTENT).
     * </p>
     *
     * @return `ResponseEntity<List<Usuario>>` con la lista de usuarios y estado HTTP 200 (OK).
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
     *
     * <p>
     *     Accesible para usuarios con el rol "ADMINUSUARIOS".
     *     Se retorna el usuario encontrado con estado HTTP 200 (OK).
     *     Si no se encuentra el usuario, se retorna un estado HTTP 404 (NOT FOUND).
     * </p>
     *
     * @return `ResponseEntity<Usuario>` con el usuario encontrado y estado HTTP 200 (OK).
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
     *
     * <p>
     *     Accesible para usuarios con el rol "ADMINUSUARIOS".
     *     Se retorna una lista de usuarios con estado HTTP 200 (OK).
     *     Si no se encuentran usuarios, se retorna un estado HTTP 204 (NO CONTENT).
     *     La búsqueda es parcial, por lo que se pueden obtener varios resultados.
     * </p>
     *
     * @return `ResponseEntity<List<Usuario>>` con la lista de usuarios encontrados y estado HTTP 200 (OK).
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
     *
     * <p>
     *     Accesible para usuarios con el rol "ADMINUSUARIOS".
     *     Se retorna un estado HTTP 200 (OK) si la actualización fue exitosa.
     *     Si el ID del usuario no coincide con el ID proporcionado, se retorna un estado HTTP 400 (BAD REQUEST).
     *     Si no se encuentra el usuario, se retorna un estado HTTP 404 (NOT FOUND).
     *     La contraseña del usuario se encripta antes de ser almacenada en la base de datos.
     * </p>
     *
     * @return `ResponseEntity<Void>` con estado HTTP 200 (OK) si la actualización fue exitosa.
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
     *
     * <p>
     *     Accesible para usuarios con el rol "ADMINUSUARIOS".
     *     Se retorna un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     *     Si no se encuentra el usuario, se retorna un estado HTTP 404 (NOT FOUND).
     * </p>
     *
     * @return `ResponseEntity<Void>` con estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
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
     *
     * <p>
     *     Accesible para usuarios con el rol "ADMINUSUARIOS".
     *     Se retorna un estado HTTP 200 (OK) si el cambio de estado fue exitoso.
     *     Si no se encuentra el usuario, se retorna un estado HTTP 404 (NOT FOUND).
     * </p>
     *
     * @return `ResponseEntity<Void>` con estado HTTP 200 (OK) si el cambio de estado fue exitoso.
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

