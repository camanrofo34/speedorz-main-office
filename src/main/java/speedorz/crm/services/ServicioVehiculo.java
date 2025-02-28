package speedorz.crm.services;

import speedorz.crm.domain.Vehiculo;

import java.util.List;

public interface ServicioVehiculo {

    Vehiculo crearVehiculo(Vehiculo vehiculo);

    void actualizarVehiculo(Vehiculo vehiculo);

    void eliminarVehiculo(Long id);

    List<Vehiculo> listarVehiculos();

    Vehiculo buscarVehiculoPorId(Long id);

    List<Vehiculo> buscarVehiculosPorNombre(String nombre);


}
