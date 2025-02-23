package speedorz.crm.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreEmpresa;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @OneToOne
    @JoinColumn(name = "orden_compra_id", nullable = false, unique = true)
    private OrdenCompra ordenCompra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @ManyToMany
    @JoinTable(
            name = "factura_descuento",
            joinColumns = @JoinColumn(name = "factura_id"),
            inverseJoinColumns = @JoinColumn(name = "descuento_id")
    )
    private Set<Descuento> descuentos = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "factura_impuesto",
            joinColumns = @JoinColumn(name = "factura_id"),
            inverseJoinColumns = @JoinColumn(name = "impuesto_id")
    )
    private Set<Impuesto> impuestos = new HashSet<>();

    @Column(nullable = false)
    private BigDecimal total;
}
