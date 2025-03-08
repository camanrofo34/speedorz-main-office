package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import speedorz.crm.domain.entities.Vehiculo;
import speedorz.crm.services.ServicioVehiculo;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de vehículos.
 * Proporciona endpoints para CRUD y búsqueda de vehículos.
 */
@RestController
@RequestMapping("/vehiculos")
public class ControladorVehiculo {

    private final ServicioVehiculo servicioVehiculo;

    @Autowired
    public ControladorVehiculo(ServicioVehiculo servicioVehiculo) {
        this.servicioVehiculo = servicioVehiculo;
    }

    /**
     * Crea un nuevo vehículo.
     * Solo puede ser ejecutado por usuarios con el rol de ADMININVENTARIO.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMININVENTARIO')")
    public ResponseEntity<Vehiculo> crearVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo respuesta = servicioVehiculo.crearVehiculo(vehiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    /**
     * Lista todos los vehículos disponibles.
     */
    @GetMapping
    public ResponseEntity<List<Vehiculo>> listarVehiculos() {
        List<Vehiculo> vehiculos = servicioVehiculo.listarVehiculos();
        if (vehiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehiculos);
    }

    /**
     * Busca un vehículo por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> buscarVehiculoPorId(@PathVariable Long id) {
        Optional<Vehiculo> vehiculo = Optional.ofNullable(servicioVehiculo.buscarVehiculoPorId(id));
        return vehiculo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Busca vehículos por nombre.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Vehiculo>> buscarVehiculoPorNombre(@RequestParam String nombre) {
        List<Vehiculo> vehiculos = servicioVehiculo.buscarVehiculosPorNombre(nombre);
        if (vehiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehiculos);
    }

    /**
     * Actualiza la información de un vehículo.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMININVENTARIO')")
    public ResponseEntity<Void> actualizarVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        if (!id.equals(vehiculo.getIdVehiculo())) {
            return ResponseEntity.badRequest().build();
        }
        if (servicioVehiculo.buscarVehiculoPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        servicioVehiculo.actualizarVehiculo(vehiculo);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina un vehículo por su ID.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMININVENTARIO')")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        if (servicioVehiculo.buscarVehiculoPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        servicioVehiculo.eliminarVehiculo(id);
        return ResponseEntity.noContent().build();
    }
}

