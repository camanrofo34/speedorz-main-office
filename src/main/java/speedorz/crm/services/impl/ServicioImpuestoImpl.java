package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.Impuesto;
import speedorz.crm.repository.RepositorioImpuesto;
import speedorz.crm.services.ServicioImpuesto;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación del servicio {@link ServicioImpuesto}.
 * Maneja la lógica de negocio para la gestión de impuestos.
 */
@Service
public class ServicioImpuestoImpl implements ServicioImpuesto {

    private final RepositorioImpuesto repositorioImpuesto;
    private final Logger logger = Logger.getLogger(ServicioImpuestoImpl.class.getName());

    @Autowired
    public ServicioImpuestoImpl(RepositorioImpuesto repositorioImpuesto) {
        this.repositorioImpuesto = repositorioImpuesto;
    }

    @Override
    public Impuesto crearImpuesto(Impuesto impuesto) {
        try {

            logger.log(Level.INFO, "Creando Impuesto {0}", impuesto.getNombre());
            return repositorioImpuesto.save(impuesto);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al crear Impuesto");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizarImpuesto(Impuesto impuesto) {
        try {
            logger.log(Level.INFO, "Actualizando Impuesto {0}", impuesto.getNombre());
            Impuesto newImpuesto = repositorioImpuesto.findById(impuesto.getId()).orElseThrow();
            newImpuesto.setNombre(impuesto.getNombre());
            newImpuesto.setDescripcion(impuesto.getDescripcion());
            newImpuesto.setPorcentaje(impuesto.getPorcentaje());
            repositorioImpuesto.save(newImpuesto);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al actualizar Impuesto", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminarImpuesto(Long id) {
        try {
            logger.log(Level.INFO, "Eliminando Impuesto {0}", id);
            repositorioImpuesto.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al eliminar Impuesto", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Impuesto buscarImpuestoPorId(Long id) {
        try {
            logger.log(Level.INFO, "Buscando Impuesto {0}", id);
            return repositorioImpuesto.findById(id).orElseThrow(() -> new RuntimeException("Impuesto no encontrado"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar Impuesto", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Impuesto> listarImpuestos() {
        try {
            logger.log(Level.INFO, "Listando Impuestos");
            return repositorioImpuesto.findAll();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al listar Impuestos", e);
            throw new RuntimeException(e);
        }
    }
}
