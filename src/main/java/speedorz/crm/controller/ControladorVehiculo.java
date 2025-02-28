package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import speedorz.crm.domain.Vehiculo;
import speedorz.crm.services.ServicioVehiculo;
import speedorz.crm.services.impl.ServicioVehiculoImpl;

import java.util.List;

@Controller
@RequestMapping("/vehiculos")
@PreAuthorize("hasRole('ADMININVENTARIO')")
public class ControladorVehiculo {

    private final ServicioVehiculo servicioVehiculo;

    @Autowired
    public ControladorVehiculo(ServicioVehiculoImpl servicioVehiculo) {
        this.servicioVehiculo = servicioVehiculo;
    }

    @PostMapping
    public ResponseEntity<Vehiculo> crearVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo respuesta = servicioVehiculo.crearVehiculo(vehiculo);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Vehiculo> listarVehiculos() {
        Vehiculo vehiculo = servicioVehiculo.buscarVehiculoPorId(1L);
        return new ResponseEntity<>(vehiculo, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> buscarVehiculoPorId(@PathVariable Long id) {
        Vehiculo vehiculo = servicioVehiculo.buscarVehiculoPorId(id);
        return new ResponseEntity<>(vehiculo, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Vehiculo>> buscarVehiculoPorNombre(String nombre) {
        List<Vehiculo> vehiculos = servicioVehiculo.buscarVehiculosPorNombre(nombre);
        return new ResponseEntity<>(vehiculos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        if (!id.equals(vehiculo.getIdVehiculo())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        servicioVehiculo.actualizarVehiculo(vehiculo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        servicioVehiculo.eliminarVehiculo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
