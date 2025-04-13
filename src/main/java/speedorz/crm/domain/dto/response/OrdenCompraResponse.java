package speedorz.crm.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCompraResponse {

    private Long idOrdenCompra;

    private String nombreCliente;

    private LocalDateTime fecha;

    private BigDecimal total;

}
