package speedorz.crm.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Clase que representa un movimiento de inventario.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovimientoInventario {

    /**
     * Identificador único del movimiento de inventario (autogenerado).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Fecha del movimiento de inventario.
     */
    private LocalDateTime fecha;

    /**
     * Cantidad de productos que se movieron.
     */
    private int cantidad;

    /**
     * Tipo de movimiento.
     * Opciones: "ENTRANTE", "SALIENTE"
     */
    private String tipoMovimiento;

    /**
     * Producto que se movió.
     */
    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    /**
     * Orden de vehículo a la que pertenece el movimiento de inventario.
     */
    @ManyToOne
    @JoinColumn(name = "id_orden_vehiculo")
    private OrdenVehiculo ordenVehiculo;
}
