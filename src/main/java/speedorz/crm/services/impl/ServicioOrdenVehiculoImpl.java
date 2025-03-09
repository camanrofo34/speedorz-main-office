package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import speedorz.crm.domain.entities.OrdenVehiculo;
import speedorz.crm.repository.RepositorioOrdenVehiculo;
import speedorz.crm.services.ServicioOrdenVehiculo;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación del servicio {@link ServicioOrdenVehiculo}.
 * Maneja la lógica de negocio para la gestión de órdenes de vehículos.
 */
@Repository
public class ServicioOrdenVehiculoImpl implements ServicioOrdenVehiculo {

    private final RepositorioOrdenVehiculo repositorioOrdenVehiculo;
    private final Logger logger = Logger.getLogger(ServicioOrdenVehiculoImpl.class.getName());

    @Autowired
    public ServicioOrdenVehiculoImpl(RepositorioOrdenVehiculo repositorioOrdenVehiculo) {
        this.repositorioOrdenVehiculo = repositorioOrdenVehiculo;
    }

    @Override
    public OrdenVehiculo crearOrdenVehiculo(OrdenVehiculo ordenVehiculo) {
        try {
            logger.log(Level.INFO, "Creando Orden de Vehículo {0}", ordenVehiculo.getVehiculo().getIdVehiculo());
            return repositorioOrdenVehiculo.save(ordenVehiculo);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al crear Orden de Vehículo", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void eliminarOrdenVehiculo(Long id) {
        try {
            logger.log(Level.INFO, "Eliminando Orden de Vehiculo {0}", id);
            repositorioOrdenVehiculo.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al eliminar Orden de Vehiculo", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrdenVehiculo buscarOrdenVehiculoPorId(Long id) {
        try {
            logger.log(Level.INFO, "Buscando Orden de Vehiculo {0}", id);
            return repositorioOrdenVehiculo.findById(id).orElseThrow();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar Orden de Vehiculo", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<OrdenVehiculo> listarOrdenVehiculos() {
        try {
            logger.log(Level.INFO, "Listando Ordenes de Vehiculo");
            return repositorioOrdenVehiculo.findAll();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al listar Ordenes de Vehiculo", e);
            throw new RuntimeException(e);
        }
    }
}
