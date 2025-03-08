package speedorz.crm.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * Clase que representa el historial de precios de un vehículo.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HistorialPrecio {

    /**
     * Identificador único del historial de precios (autogenerado).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Precio del vehículo.
     */
    private BigDecimal precio;

    /**
     * Fecha de registro del precio.
     */
    private LocalDateTime fechaRegistro;

    /**
     * Vehículo al que pertenece el historial de precios.
     */
    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;
}

