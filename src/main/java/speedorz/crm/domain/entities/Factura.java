package speedorz.crm.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Factura generada a partir de una orden de compra.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "factura")
public class Factura {

    /**
     * Identificador único de la factura (autogenerado).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de la empresa que expide la factura.
     */
    @Column(nullable = false)
    private String nombreEmpresa;

    /**
     * Fecha de expedición de la factura
     */
    @Column(nullable = false)
    private LocalDate fechaEmision;

    /**
     * Orden de compra que produce la factura.
     */
    @OneToOne
    @JoinColumn(name = "id_orden_compra")
    private OrdenCompra ordenCompra;

    /**
     * Método de pago utilizado para la factura.
     */
    @ManyToOne
    @JoinColumn(name = "id_metodo_pago")
    private MetodoPago metodoPago;

    /**
     * Estado de pago de la factura.
     * OPCIONES: "PAGO", "NO PAGO"
     */
    @Column(nullable = false)
    private String estadoPago;
}
