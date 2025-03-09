package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.Descuento;
import speedorz.crm.repository.RepositorioDescuento;
import speedorz.crm.services.ServicioDescuento;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación del servicio {@link ServicioDescuento}.
 * Maneja la lógica de negocio para la gestión de descuentos.
 */
@Service
public class ServicioDescuentoImpl implements ServicioDescuento {

    private final RepositorioDescuento repositorioDescuento;
    private final Logger logger = Logger.getLogger(ServicioDescuentoImpl.class.getName());

    @Autowired
    public ServicioDescuentoImpl(RepositorioDescuento repositorioDescuento) {
        this.repositorioDescuento = repositorioDescuento;
    }


    @Override
    public Descuento crearDescuento(Descuento descuento) {
        try {
            logger.log(Level.INFO, "Creando Descuento {0}", descuento.getNombre());
            return repositorioDescuento.save(descuento);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al crear Descuento", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizarDescuento(Descuento descuento) {
        try {
            logger.log(Level.INFO, "Actualizando Descuento {0}", descuento.getNombre());
            Descuento newDescuento = repositorioDescuento.findById(descuento.getId()).orElseThrow();
            newDescuento.setNombre(descuento.getNombre());
            newDescuento.setPorcentaje(descuento.getPorcentaje());
            newDescuento.setPorcentaje(descuento.getPorcentaje());
            repositorioDescuento.save(newDescuento);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al actualizar Descuento", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void eliminarDescuento(Long id) {
        try {
            logger.log(Level.INFO, "Eliminando Descuento {0}", id);
            repositorioDescuento.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al eliminar Descuento", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Descuento> listarDescuentos() {
        try {
            logger.log(Level.INFO, "Listando descuentos");
            return repositorioDescuento.findAll();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al listar Descuentos", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Descuento buscarDescuentoPorId(Long id) {
        try {
            logger.log(Level.INFO, "Buscando Descuento por ID {0}", id);
            return repositorioDescuento.findById(id).orElseThrow(() -> new RuntimeException("Descuento no encontrado"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar Descuento por ID", e);
            throw new RuntimeException(e);
        }
    }

}
