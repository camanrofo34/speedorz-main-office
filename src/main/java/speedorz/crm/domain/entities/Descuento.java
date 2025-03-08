package speedorz.crm.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Representa un descuento que se puede aplicar a una orden de compra.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Descuento {

    /**
     * Identificador único del descuento (autogenerado).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del descuento.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Descripción del descuento.
     */
    @Column(length = 500)
    private String descripcion;

    /**
     * Porcentaje de descuento a aplicar (Escrito en formato tanto por ciento).
     */
    @Column(nullable = false)
    private BigDecimal porcentaje;

    /**
     * Calcula el descuento a aplicar a un subtotal de vehículo.
     * @param subtotalVehiculo
     * @return descuento a aplicar
     */
    public double calcularDescuento(double subtotalVehiculo) {
        return subtotalVehiculo * (porcentaje.doubleValue() / 100);
    }
}
