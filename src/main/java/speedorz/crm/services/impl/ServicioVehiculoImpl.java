package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.Vehiculo;
import speedorz.crm.repository.RepositorioVehiculo;
import speedorz.crm.services.ServicioVehiculo;
import speedorz.crm.util.NormalizadorBusquedaUtil;

import java.util.List;

@Service
public class ServicioVehiculoImpl implements ServicioVehiculo {

    private final RepositorioVehiculo repositorioVehiculo;

    @Autowired
    public ServicioVehiculoImpl(RepositorioVehiculo repositorioVehiculo) {
        this.repositorioVehiculo = repositorioVehiculo;
    }

    @Override
    public Vehiculo crearVehiculo(String nombre, String marca, String modelo, String descripcion, int stock, double precio) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setNombre(nombre);
        vehiculo.setMarca(marca);
        vehiculo.setModelo(modelo);
        vehiculo.setDescripcion(descripcion);
        vehiculo.setStock(stock);
        vehiculo.setPrecio(precio);
        return repositorioVehiculo.save(vehiculo);
    }

    @Override
    public void actualizarVehiculo(Long id, String nombre, String marca, String modelo, String descripcion, int stock, double precio) {
        Vehiculo vehiculo = repositorioVehiculo.findById(id).orElseThrow();
        vehiculo.setNombre(nombre);
        vehiculo.setMarca(marca);
        vehiculo.setModelo(modelo);
        vehiculo.setDescripcion(descripcion);
        vehiculo.setStock(stock);
        vehiculo.setPrecio(precio);
        repositorioVehiculo.save(vehiculo);
    }

    @Override
    public void eliminarVehiculo(Long id) {
        repositorioVehiculo.deleteById(id);
    }

    @Override
    public List<Vehiculo> listarVehiculos() {
        return repositorioVehiculo.findAll();
    }

    @Override
    public Vehiculo buscarVehiculoPorId(Long id) {
        return repositorioVehiculo.findById(id).orElseThrow();
    }

    @Override
    public List<Vehiculo> buscarVehiculosPorNombre(String nombre) {
        String nombreBusqueda = NormalizadorBusquedaUtil.normalizarTexto(nombre);
        return repositorioVehiculo.findVehiculosByNombreContainsIgnoreCase(nombre);
    }
}
