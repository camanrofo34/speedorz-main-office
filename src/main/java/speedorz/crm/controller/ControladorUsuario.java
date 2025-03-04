package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import speedorz.crm.domain.Usuario;
import speedorz.crm.services.ServicioUsuario;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('ADMINUSUARIOS')")
public class ControladorUsuario {

    private final ServicioUsuario servicioUsuario;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario, PasswordEncoder passwordEncoder) {
        this.servicioUsuario = servicioUsuario;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        Usuario respuesta = servicioUsuario.crearUsuario(usuario);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = servicioUsuario.listarUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(id);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscarUsuarioPorNombreUsuario(@RequestParam String nombreUsuario) {
        List<Usuario> usuarios = servicioUsuario.buscarUsuarioPorNombreUsuario(nombreUsuario);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (!id.equals(usuario.getIdUsuario())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        servicioUsuario.actualizarUsuario(usuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        servicioUsuario.eliminarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstadoUsuario(@PathVariable Long id, @RequestParam String estado) {
        servicioUsuario.cambiarEstadoUsuario(id, estado);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
