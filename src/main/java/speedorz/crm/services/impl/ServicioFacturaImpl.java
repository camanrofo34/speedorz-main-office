package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.Factura;
import speedorz.crm.repository.RepositorioFactura;
import speedorz.crm.services.ServicioFactura;

import java.util.List;

/**
 * Implementación del servicio {@link ServicioFactura}.
 * Maneja la lógica de negocio para la gestión de facturas.
 */
@Service
public class ServicioFacturaImpl implements ServicioFactura {

    private final RepositorioFactura repositorioFactura;

    @Autowired
    public ServicioFacturaImpl(RepositorioFactura repositorioFactura) {
        this.repositorioFactura = repositorioFactura;
    }

    @Override
    public Factura generarFactura(Factura factura) {
        return repositorioFactura.save(factura);
    }

    @Override
    public void eliminarFactura(Long id) {
        repositorioFactura.deleteById(id);
    }

    @Override
    public Factura buscarFacturaPorId(Long id) {
        return repositorioFactura.findById(id).orElseThrow();
    }

    @Override
    public List<Factura> listarFacturas() {
        return repositorioFactura.findAll();
    }
}
