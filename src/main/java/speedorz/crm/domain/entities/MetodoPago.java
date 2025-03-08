package speedorz.crm.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa un método de pago.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MetodoPago {

    /**
     * Identificador único del método de pago (autogenerado).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del método de pago.
     */
    private String nombre;

    /**
     * Descripción del método de pago.
     */
    private String descripcion;
}

