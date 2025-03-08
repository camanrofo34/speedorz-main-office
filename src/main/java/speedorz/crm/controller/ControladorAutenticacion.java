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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import speedorz.crm.domain.dto.request.LoginRequestDTO;
import speedorz.crm.services.impl.ServicioUsuarioImpl;
import speedorz.crm.util.JwtUtil;

/**
 * Controlador para la autenticación de usuarios en el sistema CRM.
 * <p>
 * Este controlador maneja las solicitudes de inicio de sesión y la generación de tokens JWT
 * para los diferentes roles de usuario en la aplicación.
 * </p>
 *
 * @author Camilo
 * @version 1.0
 */
@Controller
@RequestMapping("/autenticacion")
public class ControladorAutenticacion {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor que inyecta las dependencias necesarias para la autenticación.
     *
     * @param jwtUtil           Utilidad para la generación y validación de tokens JWT.
     * @param userDetailsService Servicio para la gestión de usuarios.
     * @param passwordEncoder   Codificador de contraseñas.
     */
    @Autowired
    public ControladorAutenticacion(JwtUtil jwtUtil, ServicioUsuarioImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método interno para autenticar a un usuario según su rol esperado.
     * <p>
     * Se valida el usuario y contraseña proporcionados, y si son correctos, se genera un token JWT.
     * </p>
     *
     * @param loginRequest Datos de inicio de sesión (nombre de usuario y contraseña).
     * @param rolEsperado  Rol esperado para el usuario.
     * @return `ResponseEntity<?>` con el token JWT si la autenticación es exitosa, o un error en caso contrario.
     */
    private ResponseEntity<?> autenticarUsuario(LoginRequestDTO loginRequest, String rolEsperado) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getNombreUsuario());

            if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority(rolEsperado))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Usuario no autorizado para esta sección");
            }

            if (!passwordEncoder.matches(loginRequest.getContrasena(), userDetails.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Usuario o contraseña incorrectos");
            }

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

    /**
     * Autenticación de usuarios con rol **Recepción**.
     *
     * @param loginRequest Datos de inicio de sesión.
     * @return `ResponseEntity<?>` con el token JWT si la autenticación es exitosa.
     */
    @PostMapping("/login-recepcion")
    public ResponseEntity<?> inicioSesionRecepcion(@RequestBody LoginRequestDTO loginRequest) {
        return autenticarUsuario(loginRequest, "ROLE_RECEPCION");
    }

    /**
     * Autenticación de usuarios con rol **Secretario**.
     *
     * @param loginRequest Datos de inicio de sesión.
     * @return `ResponseEntity<?>` con el token JWT si la autenticación es exitosa.
     */
    @PostMapping("/login-secretario")
    public ResponseEntity<?> inicioSesionSecretario(@RequestBody LoginRequestDTO loginRequest) {
        return autenticarUsuario(loginRequest, "ROLE_SECRETARIO");
    }

    /**
     * Autenticación de usuarios con rol **Administrador de Usuarios**.
     *
     * @param loginRequest Datos de inicio de sesión.
     * @return `ResponseEntity<?>` con el token JWT si la autenticación es exitosa.
     */
    @PostMapping("/login-adminusuarios")
    public ResponseEntity<?> inicioSesionAdminUsuarios(@RequestBody LoginRequestDTO loginRequest) {
        return autenticarUsuario(loginRequest, "ROLE_ADMINUSUARIOS");
    }

    /**
     * Autenticación de usuarios con rol **Administrador de Inventario**.
     *
     * @param loginRequest Datos de inicio de sesión.
     * @return `ResponseEntity<?>` con el token JWT si la autenticación es exitosa.
     */
    @PostMapping("/login-admininventario")
    public ResponseEntity<?> inicioSesionAdminInventario(@RequestBody LoginRequestDTO loginRequest) {
        return autenticarUsuario(loginRequest, "ROLE_ADMININVENTARIO");
    }

    /**
     * Autenticación de usuarios con rol **Asesor Comercial**.
     *
     * @param loginRequest Datos de inicio de sesión.
     * @return `ResponseEntity<?>` con el token JWT si la autenticación es exitosa.
     */
    @PostMapping("/login-asesorcomercial")
    public ResponseEntity<?> inicioSesionAsesorComercial(@RequestBody LoginRequestDTO loginRequest) {
        return autenticarUsuario(loginRequest, "ROLE_ASESORCOMERCIAL");
    }
}


