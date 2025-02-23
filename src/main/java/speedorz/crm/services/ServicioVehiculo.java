package speedorz.crm.services;

import speedorz.crm.domain.Vehiculo;

import java.util.List;

public interface ServicioVehiculo {

    Vehiculo crearVehiculo(String nombre, String marca, String modelo, String descripcion, int stock, double precio);

    void actualizarVehiculo(Long id, String nombre, String marca, String modelo, String descripcion, int stock, double precio);

    void eliminarVehiculo(Long id);

    List<Vehiculo> listarVehiculos();

    Vehiculo buscarVehiculoPorId(Long id);

    List<Vehiculo> buscarVehiculosPorNombre(String nombre);


}
