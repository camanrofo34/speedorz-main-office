package speedorz.crm.domain;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "orden_compra")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Usuario vendedor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<OrdenVehiculo> ordenVehiculos = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "orden_descuento",
            joinColumns = @JoinColumn(name = "orden_id"),
            inverseJoinColumns = @JoinColumn(name = "descuento_id")
    )
    @ToString.Exclude
    private Set<Descuento> descuentos = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "orden_impuesto",
            joinColumns = @JoinColumn(name = "orden_id"),
            inverseJoinColumns = @JoinColumn(name = "impuesto_id")
    )
    @ToString.Exclude
    private Set<Impuesto> impuestos = new HashSet<>();

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal total;

    @OneToOne(mappedBy = "ordenCompra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Factura factura;
}
