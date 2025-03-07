package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import speedorz.crm.domain.entities.OrdenVehiculo;
import speedorz.crm.repository.RepositorioOrdenVehiculo;
import speedorz.crm.services.ServicioOrdenVehiculo;

import java.util.List;

/**
 * Implementación del servicio {@link ServicioOrdenVehiculo}.
 * Maneja la lógica de negocio para la gestión de órdenes de vehículos.
 */
@Repository
public class ServicioOrdenVehiculoImpl implements ServicioOrdenVehiculo {

    private final RepositorioOrdenVehiculo repositorioOrdenVehiculo;

    @Autowired
    public ServicioOrdenVehiculoImpl(RepositorioOrdenVehiculo repositorioOrdenVehiculo) {
        this.repositorioOrdenVehiculo = repositorioOrdenVehiculo;
    }

    @Override
    public OrdenVehiculo crearOrdenVehiculo(OrdenVehiculo ordenVehiculo) {
        return repositorioOrdenVehiculo.save(ordenVehiculo);
    }

    @Override
    public void eliminarOrdenVehiculo(Long id) {
        repositorioOrdenVehiculo.deleteById(id);
    }

    @Override
    public OrdenVehiculo buscarOrdenVehiculoPorId(Long id) {
        return repositorioOrdenVehiculo.findById(id).orElseThrow();
    }

    @Override
    public List<OrdenVehiculo> listarOrdenVehiculos() {
        return repositorioOrdenVehiculo.findAll();
    }
}
