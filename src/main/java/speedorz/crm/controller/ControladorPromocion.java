package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import speedorz.crm.domain.entities.Vehiculo;
import speedorz.crm.services.ServicioVehiculo;
import speedorz.crm.services.impl.ServicioVehiculoImpl;

import java.util.List;

@RequestMapping("/promocion")
@Controller
public class ControladorPromocion {

    private final ServicioVehiculo servicioVehiculo;

    @Autowired
    public ControladorPromocion(ServicioVehiculoImpl servicioVehiculo) {
        this.servicioVehiculo = servicioVehiculo;
    }

    @GetMapping
    public ResponseEntity<List<Vehiculo>> listarVehiculos() {
        return ResponseEntity.ok(servicioVehiculo.listarVehiculos());
    }
}
