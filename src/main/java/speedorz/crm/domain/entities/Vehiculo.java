package speedorz.crm.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Clase que representa un vehículo en el sistema CRM.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "vehiculo")
public class Vehiculo {

    /**
     * Identificador único del vehículo (autogenerado).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehiculo;

    /**
     * Nombre comercial del vehículo.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Marca del vehículo.
     * Predeterminado: Speedorz
     */
    @Column(nullable = false)
    private String marca;

    /**
     * Modelo del vehículo.
     */
    @Column(nullable = false)
    private String modelo;

    /**
     * Descripción del vehículo.
     */
    @Column(length = 500)
    private String descripcion;

    /**
     * Stock disponible del vehículo.
     */
    @Column(nullable = false)
    private int stock;

    /**
     * Precio del vehículo.
     */
    @Column(nullable = false)
    private BigDecimal precio;
}
