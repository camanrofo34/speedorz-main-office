package speedorz.crm.domain.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Clase que representa una orden de compra.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "orden_compra")
public class OrdenCompra {

    /**
     * Identificador único de la orden de compra (autogenerado).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrdenCompra;

    /**
     * Fecha de la orden de compra.
     */
    private LocalDateTime fecha;

    /**
     * Subtotal de la orden de compra.
     */
    private BigDecimal subtotal;

    /**
     * Total de la orden de compra.
     */
    private BigDecimal total;

    /**
     * Asesor comercial encargado de plantar la orden de compra
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario responsable;

    /**
     * Cliente al que pertenece la orden de compra.
     */
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
