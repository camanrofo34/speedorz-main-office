package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import speedorz.crm.domain.Usuario;
import speedorz.crm.services.ServicioUsuario;
import speedorz.crm.services.impl.ServicioUsuarioImpl;
import speedorz.crm.util.JwtUtil;

import java.util.List;

@Controller
@RequestMapping("/autenticacion")
public class ControladorAutenticacion {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;
    private final ServicioUsuario servicioUsuario;


    @Autowired
    public ControladorAutenticacion(JwtUtil jwtUtil, ServicioUsuarioImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.servicioUsuario = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> inicioSesion(@RequestParam String nombreUsuario, @RequestParam String contrasena) {
        List<Usuario> usuario = servicioUsuario.buscarUsuarioPorNombreUsuario(nombreUsuario);
        for (Usuario user : usuario) {
            if (passwordEncoder.matches(contrasena, user.getContrasena())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);
                String token = jwtUtil.generateToken(userDetails.getUsername());
                return ResponseEntity.ok(token);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Usuario o contraseña incorrectos");
    }

}
