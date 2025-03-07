package speedorz.crm.services;

import speedorz.crm.domain.entities.OrdenCompra;
import speedorz.crm.domain.dto.request.OrdenCompraDTO;

import java.util.List;

public interface ServicioOrdenCompra {

    OrdenCompra plantarOrdenCompra(OrdenCompraDTO ordenCompra);

    void eliminarOrdenCompra(Long id);

    OrdenCompra buscarOrdenCompraPorId(Long id);

    List<OrdenCompra> listarOrdenCompras();
}
