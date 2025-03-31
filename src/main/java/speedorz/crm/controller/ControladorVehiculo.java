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
 * <p>
 *     Proporciona endpoints para CRUD y búsqueda de vehículos.
 * </p>
 *
 * @author Camilo
 * @version 1.0
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
     * <p>
     *     Solo accesible para usuarios con el rol "ADMININVENTARIO".
     *     Retorna el vehículo creado con estado HTTP 201 (CREATED).
     *     Si el vehículo no se puede crear, retorna estado HTTP 400 (BAD REQUEST).
     * </p>
     *
     * @return `ResponseEntity<Vehiculo>` con el vehículo creado y estado HTTP 201 (CREATED).
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMININVENTARIO')")
    public ResponseEntity<Vehiculo> crearVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo respuesta = servicioVehiculo.crearVehiculo(vehiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    /**
     * Lista todos los vehículos disponibles.
     *
     * <p>
     *     Accesible para usuarios con el rol "ADMININVENTARIO".
     *     Retorna una lista de vehículos con estado HTTP 200 (OK).
     *     Si no hay vehículos registrados, retorna estado HTTP 204 (NO CONTENT).
     * </p>
     *
     * @return `ResponseEntity<List<Vehiculo>>` con la lista de vehículos y estado HTTP 200 (OK).
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
     *
     * <p>
     *     Accesible para usuarios con el rol "ADMININVENTARIO".
     *     Retorna el vehículo encontrado con estado HTTP 200 (OK).
     *     Si no se encuentra el vehículo, retorna estado HTTP 404 (NOT FOUND).
     * </p>
     *
     * @return `ResponseEntity<Vehiculo>` con el vehículo encontrado y estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> buscarVehiculoPorId(@PathVariable Long id) {
        Optional<Vehiculo> vehiculo = Optional.ofNullable(servicioVehiculo.buscarVehiculoPorId(id));
        return vehiculo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Busca vehículos por nombre.
     *
     * <p>
     *     Accesible para usuarios con el rol "ADMININVENTARIO".
     *     Retorna una lista de vehículos con estado HTTP 200 (OK).
     *     Si no se encuentran vehículos, retorna estado HTTP 204 (NO CONTENT).
     * </p>
     *
     * @return `ResponseEntity<List<Vehiculo>>` con la lista de vehículos y estado HTTP 200 (OK).
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
     *
     * <p>
     *     Solo accesible para usuarios con el rol "ADMININVENTARIO".
     *     Retorna estado HTTP 200 (OK) si la actualización fue exitosa.
     *     Si los IDs no coinciden, retorna estado HTTP 400 (BAD REQUEST).
     *     Si el vehículo no existe, retorna estado HTTP 404 (NOT FOUND).
     * </p>
     *
     * @return `ResponseEntity<Void>` con estado HTTP 200 (OK) si la actualización fue exitosa.
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
     *
     * <p>
     *     Solo accesible para usuarios con el rol "ADMININVENTARIO".
     *     Retorna estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     *     Si el vehículo no existe, retorna estado HTTP 404 (NOT FOUND).
     *     Si el vehículo no se puede eliminar, retorna estado HTTP 400 (BAD REQUEST).
     * </p>
     *
     * @return `ResponseEntity<Void>` con estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
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

