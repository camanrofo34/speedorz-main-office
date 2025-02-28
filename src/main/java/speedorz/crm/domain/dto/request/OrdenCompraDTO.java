package speedorz.crm.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrdenCompraDTO {

    private LocalDateTime fecha;
    private long idUsuario;
    private long idCliente;

    private List<OrdenVehiculoDTO> ordenVehiculos;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OrdenVehiculoDTO {
        private Long idVehiculo;
        private int cantidad;
        private List<Long> idDescuentos;
        private List<Long> idImpuestos;
    }

}
