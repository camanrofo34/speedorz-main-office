package speedorz.crm.services;

import speedorz.crm.domain.entities.OrdenVehiculo;

import java.util.List;

public interface ServicioOrdenVehiculo {

    OrdenVehiculo crearOrdenVehiculo(OrdenVehiculo ordenVehiculo);

    void eliminarOrdenVehiculo(Long id);

    OrdenVehiculo buscarOrdenVehiculoPorId(Long id);

    List<OrdenVehiculo> listarOrdenVehiculos();

}
