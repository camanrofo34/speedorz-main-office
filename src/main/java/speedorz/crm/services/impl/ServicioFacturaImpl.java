package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.Factura;
import speedorz.crm.repository.RepositorioFactura;
import speedorz.crm.services.ServicioFactura;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación del servicio {@link ServicioFactura}.
 * Maneja la lógica de negocio para la gestión de facturas.
 */
@Service
public class ServicioFacturaImpl implements ServicioFactura {

    private final RepositorioFactura repositorioFactura;
    private final Logger logger = Logger.getLogger(ServicioFacturaImpl.class.getName());

    @Autowired
    public ServicioFacturaImpl(RepositorioFactura repositorioFactura) {
        this.repositorioFactura = repositorioFactura;
    }

    @Override
    public Factura generarFactura(Factura factura) {
        try {
            logger.log(Level.INFO, "Buscando Factura por ID {0}", factura.getId());
            return repositorioFactura.save(factura);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al generar factura");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminarFactura(Long id) {
        try {
            logger.log(Level.INFO, "Eliminando Factura por ID {0}", id);
            repositorioFactura.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al eliminar factura");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Factura buscarFacturaPorId(Long id) {
        try {
            logger.log(Level.INFO, "Buscando Factura por ID {0}", id);
            return repositorioFactura.findById(id).orElseThrow(() -> new RuntimeException("Factura no encontrada"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar factura por ID", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Factura> listarFacturas() {
        try {
            logger.log(Level.INFO, "Listando Facturas");
            return repositorioFactura.findAll();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al listar facturas");
            throw new RuntimeException(e);
        }
    }
}
