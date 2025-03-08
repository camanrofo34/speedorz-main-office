package speedorz.crm.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Representa un usuario en el sistema CRM.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "usuario")
public class Usuario {

    /**
     * Identificador único del usuario (autogenerado).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    /**
     * Nombre de usuario (único).
     * Formato: "%@speedorz.com"
     */
    @Column(nullable = false, unique = true)
    private String nombreUsuario;

    /**
     * Nombre completo del usuario.
     */
    @Column(nullable = false)
    private String nombreCompleto;

    /**
     * Contraseña del usuario.
     * Cifrada con BCrypt
     */
    @Column(nullable = false)
    private String contrasena;

    /**
     * Cédula del usuario (única).
     */
    @Column(nullable = false, unique = true)
    private String cedula;

    /**
     * Dirección del usuario.
     */
    @Column(nullable = false)
    private String direccion;

    /**
     * Teléfono de contacto del usuario.
     */
    @Column(nullable = false)
    private String telefono;

    /**
     * Rol del usuario.
     * Opciones: "ADMINUSUARIOS", "ADMININVENTARIO", "RECEPCION", "SECRETARIO", "ASESORCOMERCIAL"
     */
    @Column(nullable = false)
    private String rol;

    /**
     * Estado del usuario.
     * Opciones: "ACTIVO", "INACTIVO"
     */
    @Column(nullable = false)
    private String estado;
}
