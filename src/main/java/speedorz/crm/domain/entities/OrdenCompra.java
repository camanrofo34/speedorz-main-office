package speedorz.crm.domain.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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
    private Long idOrdenCompra;

    private LocalDateTime fecha;

    private BigDecimal subtotal;

    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario responsable;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
