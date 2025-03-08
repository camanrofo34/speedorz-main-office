package speedorz.crm.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Clase que representa una orden de vehículo.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orden_vehiculo")
public class OrdenVehiculo {

    /**
     * Identificador único de la orden de vehículo (autogenerado).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrdenVehiculo;

    /**
     * Cantidad de vehículos.
     */
    private int cantidad;

    /**
     * Precio unitario del vehículo a la fecha
     */
    private BigDecimal precioUnitario;

    /**
     * Subtotal de la orden de vehículo.
     */
    private BigDecimal subtotal;

    /**
     * Total de la orden de vehículo.
     */
    private BigDecimal total;

    /**
     * Vehículo de la orden.
     */
    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    /**
     * Orden de compra a la que pertenece la orden de vehículo.
     */
    @ManyToOne
    @JoinColumn(name = "id_orden_compra")
    private OrdenCompra ordenCompra;

    /**
     * Descuentos aplicados a la compra
     */
    @ManyToMany
    @JoinTable(
            name = "orden_vehiculo_descuento",
            joinColumns = @JoinColumn(name = "id_orden_vehiculo"),
            inverseJoinColumns = @JoinColumn(name = "id_descuento")
    )
    private Set<Descuento> descuentos;

    /**
     * Impuestos aplicados a la compra
     */
    @ManyToMany
    @JoinTable(
            name = "orden_vehiculo_impuesto",
            joinColumns = @JoinColumn(name = "id_orden_vehiculo"),
            inverseJoinColumns = @JoinColumn(name = "id_impuesto")
    )
    private Set<Impuesto> impuestos;

}
