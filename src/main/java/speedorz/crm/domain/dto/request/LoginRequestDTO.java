package speedorz.crm.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para representar la solicitud de inicio de sesión.
 * Contiene el nombre de usuario y la contraseña ingresados por el usuario.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDTO {

    /**
     * Nombre de usuario para autenticación.
     * Debe coincidir con un usuario registrado en el sistema.
     */
    private String nombreUsuario;

    /**
     * Contraseña en texto plano proporcionada por el usuario.
     * Se recomienda encriptarla antes de almacenarla o compararla con la base de datos.
     */
    private String contrasena;
}

