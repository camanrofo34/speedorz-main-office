package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import speedorz.crm.domain.dto.request.LoginRequestDTO;
import speedorz.crm.services.impl.ServicioUsuarioImpl;
import speedorz.crm.util.JwtUtil;

@Controller
@RequestMapping("/autenticacion")
public class ControladorAutenticacion {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ControladorAutenticacion(JwtUtil jwtUtil, ServicioUsuarioImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login-recepcion")
    public ResponseEntity<?> inicioSesionRecepcion(@RequestBody LoginRequestDTO loginRequest) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getNombreUsuario());

            // Verificar si el usuario tiene el rol adecuado
            if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_RECEPCION"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Usuario no autorizado para esta sección");
            }

            // Verificar si la contraseña es correcta
            if (!passwordEncoder.matches(loginRequest.getContrasena(), userDetails.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Usuario o contraseña incorrectos");
            }

            // Generar token y construir la respuesta
            String token = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(token);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Usuario no encontrado");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Contraseña incorrecta");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }

    @PostMapping("/login-secretario")
    public ResponseEntity<?> inicioSesionSecretario(@RequestBody LoginRequestDTO loginRequest) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getNombreUsuario());

            // Verificar si el usuario tiene el rol adecuado
            if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SECRETARIO"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Usuario no autorizado para esta sección");
            }

            // Verificar si la contraseña es correcta
            if (!passwordEncoder.matches(loginRequest.getContrasena(), userDetails.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Usuario o contraseña incorrectos");
            }

            // Generar token y construir la respuesta
            String token = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(token);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Usuario no encontrado");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Contraseña incorrecta");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }

    @PostMapping("/login-adminusuarios")
    public ResponseEntity<?> inicioSesionAdminUsuarios(@RequestBody LoginRequestDTO loginRequest) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getNombreUsuario());

            // Verificar si el usuario tiene el rol adecuado
            if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINUSUARIOS"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Usuario no autorizado para esta sección");
            }

            // Verificar si la contraseña es correcta
            if (!passwordEncoder.matches(loginRequest.getContrasena(), userDetails.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Usuario o contraseña incorrectos");
            }

            // Generar token y construir la respuesta
            String token = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(token);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Usuario no encontrado");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Contraseña incorrecta");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }

    @PostMapping("/login-admininventario")
    public ResponseEntity<?> inicioSesionAdminInventario(@RequestBody LoginRequestDTO loginRequest) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getNombreUsuario());

            // Verificar si el usuario tiene el rol adecuado
            if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMININVENTARIO"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Usuario no autorizado para esta sección");
            }

            // Verificar si la contraseña es correcta
            if (!passwordEncoder.matches(loginRequest.getContrasena(), userDetails.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Usuario o contraseña incorrectos");
            }

            // Generar token y construir la respuesta
            String token = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(token);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Usuario no encontrado");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Contraseña incorrecta");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }

    @PostMapping("/login-asesorcomercial")
    public ResponseEntity<?> inicioSesionAsesorComercial(@RequestBody LoginRequestDTO loginRequest) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getNombreUsuario());

            // Verificar si el usuario tiene el rol adecuado
            if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ASESORCOMERCIAL"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Usuario no autorizado para esta sección");
            }

            // Verificar si la contraseña es correcta
            if (!passwordEncoder.matches(loginRequest.getContrasena(), userDetails.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Usuario o contraseña incorrectos");
            }

            // Generar token y construir la respuesta
            String token = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(token);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Usuario no encontrado");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Contraseña incorrecta");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }
}
