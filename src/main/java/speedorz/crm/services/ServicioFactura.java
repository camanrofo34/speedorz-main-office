package speedorz.crm.services;

import speedorz.crm.domain.entities.Factura;

import java.util.List;

public interface ServicioFactura {

    Factura generarFactura(Factura factura);

    void eliminarFactura(Long id);

    Factura buscarFacturaPorId(Long id);

    List<Factura> listarFacturas();
}
