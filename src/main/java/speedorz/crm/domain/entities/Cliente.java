package speedorz.crm.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa un cliente en el sistema CRM.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cliente")
public class Cliente {

    /**
     * Identificador único del cliente (autogenerado).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    /**
     * Nombre legal del cliente o empresa.
     */
    @Column(nullable = false)
    private String nombreLegal;

    /**
     * Número de identificación del cliente (único).
     */
    @Column(nullable = false, unique = true)
    private String numeroIdentificacion;

    /**
     * Dirección del cliente.
     */
    @Column(nullable = false)
    private String direccion;

    /**
     * Teléfono de contacto del cliente.
     */
    @Column(nullable = false)
    private String telefono;
}

