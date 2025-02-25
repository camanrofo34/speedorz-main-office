package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import speedorz.crm.domain.Impuesto;
import speedorz.crm.services.ServicioImpuesto;
import speedorz.crm.services.impl.ServicioImpuestoImpl;
import java.util.List;

@Controller
@RequestMapping("/impuestos")
@PreAuthorize("hasRole('SECRETARIO')")
public class ControladorImpuesto {

    private final ServicioImpuesto servicioImpuesto;

    @Autowired
    public ControladorImpuesto(ServicioImpuestoImpl servicioImpuesto) {
        this.servicioImpuesto = servicioImpuesto;
    }

    @GetMapping
    public ResponseEntity<List<Impuesto>> listarImpuestos() {
        List<Impuesto> impuestos = servicioImpuesto.listarImpuestos();
        return new ResponseEntity<>(impuestos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Impuesto> buscarImpuestoPorId(@PathVariable Long id) {
        Impuesto impuesto = servicioImpuesto.buscarImpuestoPorId(id);
        return new ResponseEntity<>(impuesto, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Impuesto>> buscarImpuestoPorNombre(@RequestParam String nombre) {
        List<Impuesto> impuestos = servicioImpuesto.buscarImpuestoPorNombre(nombre);
        return new ResponseEntity<>(impuestos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarImpuesto(@PathVariable Long id) {
        servicioImpuesto.eliminarImpuesto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarImpuesto(@PathVariable Long id, @RequestBody Impuesto impuesto) {
        servicioImpuesto.actualizarImpuesto(id, impuesto.getNombre(), impuesto.getPorcentaje(), impuesto.getDescripcion());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Impuesto> crearImpuesto(@RequestBody Impuesto impuesto) {
        Impuesto respuesta = servicioImpuesto.crearImpuesto(impuesto.getNombre(), impuesto.getPorcentaje(), impuesto.getDescripcion());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }


}
