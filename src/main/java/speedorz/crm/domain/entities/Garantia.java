package speedorz.crm.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Clase que representa una garantía de un vehículo.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Garantia {

    /**
     * Identificador único de la garantía (autogenerado).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Tipo de garantía.
     * Opciones: "DEVOLUCIÓN", "ARREGLO"
     */
    @Column(nullable = false)
    private String tipo;

    /**
     * Descripción de la garantía.
     */
    @Column(nullable = false)
    private String descripcion;

    /**
     * Fecha de inicio de la garantía.
     */
    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    /**
     * Fecha de fin de la garantía.
     */
    private LocalDateTime fechaFin;

    /**
     * Orden de vehículo a la que pertenece la garantía.
     */
    @ManyToOne
    @JoinColumn(name = "id_orden_vehiculo")
    private OrdenVehiculo ordenVehiculo;
}

