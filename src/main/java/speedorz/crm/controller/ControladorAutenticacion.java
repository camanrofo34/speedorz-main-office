package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/autenticacion")
public class ControladorAutenticacion {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;
    private final ServicioUsuario servicioUsuario;


    @Autowired
    public ControladorAutenticacion(AuthenticationManager authenticationManager, JwtUtil jwtUtil, ServicioUsuarioImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.servicioUsuario = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> inicioSesion(@RequestParam String nombreUsuario, @RequestParam String contrasena) {
            List<Usuario> usuario = servicioUsuario.buscarUsuarioPorNombreUsuario(nombreUsuario);
            Iterator<Usuario> iterator = usuario.iterator();
            while (iterator.hasNext()) {
                Usuario user = iterator.next();
                if (passwordEncoder.matches(contrasena, user.getContrasena())) {
                    System.out.println("Autenticación exitosa para usuario: " + nombreUsuario);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);
                    String token = jwtUtil.generateToken(userDetails.getUsername());
                    return ResponseEntity.ok(token);
                }
            }
            System.out.println("Autenticación fallida para usuario: " + nombreUsuario);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Usuario o contraseña incorrectos");
    }

}
