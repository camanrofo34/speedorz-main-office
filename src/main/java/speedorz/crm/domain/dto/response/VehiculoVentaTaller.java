package speedorz.crm.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class VehiculoVentaTaller {
    private String marca;
    private String modelo;
    private int year;
    private BigDecimal precio;
}
