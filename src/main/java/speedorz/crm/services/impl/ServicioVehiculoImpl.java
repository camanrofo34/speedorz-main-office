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
    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        return repositorioVehiculo.save(vehiculo);
    }

    @Override
    public void actualizarVehiculo(Vehiculo vehiculo) {
        Vehiculo newVehiculo = repositorioVehiculo.findById(vehiculo.getIdVehiculo()).orElseThrow();
        newVehiculo.setIdVehiculo(vehiculo.getIdVehiculo());
        newVehiculo.setNombre(vehiculo.getNombre());
        newVehiculo.setMarca(vehiculo.getMarca());
        newVehiculo.setModelo(vehiculo.getModelo());
        newVehiculo.setDescripcion(vehiculo.getDescripcion());
        newVehiculo.setPrecio(vehiculo.getPrecio());
        newVehiculo.setStock(vehiculo.getStock());
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
        return repositorioVehiculo.findVehiculosByNombreContainsIgnoreCase(nombreBusqueda);
    }
}
