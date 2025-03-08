package speedorz.crm.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para la creación de una orden de compra.
 * Contiene la información del usuario, cliente y los vehículos a comprar.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrdenCompraDTO {

    /**
     * Fecha y hora en que se genera la orden de compra.
     */
    private LocalDateTime fecha;

    /**
     * ID del usuario que registra la orden de compra.
     */
    private long idUsuario;

    /**
     * ID del cliente al que se le realiza la orden de compra.
     */
    private long idCliente;

    /**
     * Lista de vehículos incluidos en la orden de compra.
     */
    private List<OrdenVehiculoDTO> ordenVehiculos;

    /**
     * DTO interno que representa los vehículos dentro de la orden de compra.
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OrdenVehiculoDTO {

        /**
         * ID del vehículo que se está comprando.
         */
        private Long idVehiculo;

        /**
         * Cantidad de unidades del vehículo en la orden de compra.
         */
        private int cantidad;

        /**
         * Lista de IDs de los descuentos aplicados a este vehículo.
         */
        private List<Long> idDescuentos;

        /**
         * Lista de IDs de los impuestos aplicados a este vehículo.
         */
        private List<Long> idImpuestos;
    }
}

