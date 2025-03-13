package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.MovimientoInventario;
import speedorz.crm.domain.entities.Vehiculo;
import speedorz.crm.repository.RepositorioMovimientoInventario;
import speedorz.crm.repository.RepositorioVehiculo;
import speedorz.crm.services.ServicioVehiculo;
import speedorz.crm.util.NormalizadorBusquedaUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación del servicio {@link ServicioVehiculo}.
 * Maneja la lógica de negocio para la gestión de vehículos.
 */
@Service
public class ServicioVehiculoImpl implements ServicioVehiculo {

    private final RepositorioVehiculo repositorioVehiculo;
    private final RepositorioMovimientoInventario repositorioMovimientoInventario;
    private final Logger logger = Logger.getLogger(ServicioVehiculoImpl.class.getName());

    @Autowired
    public ServicioVehiculoImpl(RepositorioVehiculo repositorioVehiculo, RepositorioMovimientoInventario repositorioMovimientoInventario) {
        this.repositorioVehiculo = repositorioVehiculo;
        this.repositorioMovimientoInventario = repositorioMovimientoInventario;
    }

    @Override
    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        try {
            logger.log(Level.INFO, "Creando un vehiculo {0}", vehiculo.getNombre());
            Vehiculo respuesta = repositorioVehiculo.save(vehiculo);
            MovimientoInventario movimientoInventario = new MovimientoInventario();
            movimientoInventario.setVehiculo(respuesta);
            movimientoInventario.setCantidad(respuesta.getStock());
            movimientoInventario.setTipoMovimiento("Entrada");
            movimientoInventario.setFecha(ZonedDateTime.now(ZoneId.of("America/Bogota")).toLocalDateTime());
            repositorioMovimientoInventario.save(movimientoInventario);
            return respuesta;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al crear vehiculo", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizarVehiculo(Vehiculo vehiculo) {
        try {
            logger.log(Level.INFO, "Actualizando vehiculo {0}", vehiculo.getNombre());
            Vehiculo newVehiculo = repositorioVehiculo.findById(vehiculo.getIdVehiculo()).orElseThrow();
            newVehiculo.setIdVehiculo(vehiculo.getIdVehiculo());
            newVehiculo.setNombre(vehiculo.getNombre());
            newVehiculo.setMarca(vehiculo.getMarca());
            newVehiculo.setModelo(vehiculo.getModelo());
            newVehiculo.setDescripcion(vehiculo.getDescripcion());
            newVehiculo.setPrecio(vehiculo.getPrecio());
            if (vehiculo.getStock() > newVehiculo.getStock()) {
                MovimientoInventario movimientoInventario = new MovimientoInventario();
                movimientoInventario.setVehiculo(newVehiculo);
                movimientoInventario.setCantidad(vehiculo.getStock() - newVehiculo.getStock());
                movimientoInventario.setTipoMovimiento("Entrada");
                movimientoInventario.setFecha(ZonedDateTime.now(ZoneId.of("America/Bogota")).toLocalDateTime());
                repositorioMovimientoInventario.save(movimientoInventario);
            } else if (vehiculo.getStock() < newVehiculo.getStock()) {
                MovimientoInventario movimientoInventario = new MovimientoInventario();
                movimientoInventario.setVehiculo(newVehiculo);
                movimientoInventario.setCantidad(newVehiculo.getStock() - vehiculo.getStock());
                movimientoInventario.setTipoMovimiento("Salida");
                movimientoInventario.setFecha(ZonedDateTime.now(ZoneId.of("America/Bogota")).toLocalDateTime());
                repositorioMovimientoInventario.save(movimientoInventario);
            }
            newVehiculo.setStock(vehiculo.getStock());
            repositorioVehiculo.save(vehiculo);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al actualizar vehiculo", e);
            throw new RuntimeException(e);
        }


    }

    @Override
    public void eliminarVehiculo(Long id) {
        try {
            logger.log(Level.INFO, "Eliminando vehiculo {0}", id);
            repositorioVehiculo.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al eliminar vehiculo", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehiculo> listarVehiculos() {
        try {
            logger.log(Level.INFO, "Listando vehiculos");
            return repositorioVehiculo.findAll();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al listar vehiculos", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public Vehiculo buscarVehiculoPorId(Long id) {
        try {
            logger.log(Level.INFO, "Buscando vehiculo {0}", id);
            return repositorioVehiculo.findById(id).orElseThrow();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar vehiculo", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehiculo> buscarVehiculosPorNombre(String nombre) {
        try {
            logger.log(Level.INFO, "Buscando vehiculo por nombre {0}", nombre);
            String nombreBusqueda = NormalizadorBusquedaUtil.normalizarTexto(nombre);
            return repositorioVehiculo.findVehiculosByNombreContainsIgnoreCase(nombreBusqueda);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar vehiculo por nombre", e);
            throw new RuntimeException(e);
        }
    }
}
