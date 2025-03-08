package speedorz.crm.domain.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Clase que representa un impuesto que se puede aplicar a un vehículo.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Impuesto {

    /**
     * Identificador único del impuesto (autogenerado).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del impuesto.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Descripción del impuesto.
     */
    @Column(length = 500)
    private String descripcion;

    /**
     * Porcentaje de impuestos a aplicar (Escrito en formato tanto por ciento).
     */
    @Column(nullable = false)
    private BigDecimal porcentaje;

    /**
     * Calcula el impuesto a aplicar a un subtotal de vehículo.
     * @param subtotalVehiculo
     * @return impuesto a aplicar
     */
    public double calcularImpuesto(double subtotalVehiculo) {
        return subtotalVehiculo * (porcentaje.doubleValue()/100);
    }
}
