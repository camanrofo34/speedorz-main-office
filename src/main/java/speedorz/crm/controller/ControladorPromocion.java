package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedorz.crm.domain.entities.Vehiculo;
import speedorz.crm.services.ServicioVehiculo;
import speedorz.crm.services.impl.ServicioVehiculoImpl;

import java.util.List;

/**
 * Controlador para gestionar vehículos en promoción.
 * <p>
 * Expone un endpoint para listar vehículos con promociones activas.
 * </p>
 *
 * @author Camilo
 * @version 1.0
 */
@RestController
@RequestMapping("/vehiculos-promocion")
public class ControladorPromocion {

    private final ServicioVehiculo servicioVehiculo;

    /**
     * Constructor que inyecta el servicio de vehículos.
     *
     * @param servicioVehiculo Servicio encargado de gestionar los vehículos.
     */
    @Autowired
    public ControladorPromocion(ServicioVehiculoImpl servicioVehiculo) {
        this.servicioVehiculo = servicioVehiculo;
    }

    /**
     * Lista todos los vehículos en promoción.
     * <p>
     * Si hay un error, devuelve un estado HTTP 500 con el mensaje correspondiente.
     * </p>
     *
     * @return Lista de vehículos en promoción o mensaje de error.
     */
    @GetMapping
    public ResponseEntity<?> listarVehiculos() {
        try {
            List<Vehiculo> vehiculos = servicioVehiculo.listarVehiculos();
            if (vehiculos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(vehiculos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener los vehículos: " + e.getMessage());
        }
    }
}

