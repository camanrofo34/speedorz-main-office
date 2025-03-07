package speedorz.crm.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "orden_vehiculo")
public class OrdenVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrdenVehiculo;

    private int cantidad;

    private BigDecimal precioUnitario;

    private BigDecimal subtotal;

    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_orden_compra")
    private OrdenCompra ordenCompra;

    @ManyToMany
    @JoinTable(
            name = "orden_vehiculo_descuento",
            joinColumns = @JoinColumn(name = "id_orden_vehiculo"),
            inverseJoinColumns = @JoinColumn(name = "id_descuento")
    )
    private Set<Descuento> descuentos;

    @ManyToMany
    @JoinTable(
            name = "orden_vehiculo_impuesto",
            joinColumns = @JoinColumn(name = "id_orden_vehiculo"),
            inverseJoinColumns = @JoinColumn(name = "id_impuesto")
    )
    private Set<Impuesto> impuestos;

}
