package speedorz.crm.domain;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Impuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private BigDecimal porcentaje;

    @ManyToMany(mappedBy = "impuestos")
    @ToString.Exclude
    private Set<OrdenCompra> ordenes = new HashSet<>();
}
