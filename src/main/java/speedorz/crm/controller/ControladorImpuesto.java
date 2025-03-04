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
public class ControladorImpuesto {

    private final ServicioImpuesto servicioImpuesto;

    @Autowired
    public ControladorImpuesto(ServicioImpuestoImpl servicioImpuesto) {
        this.servicioImpuesto = servicioImpuesto;
    }

    @GetMapping
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ASESORCOMERCIAL')")
    public ResponseEntity<List<Impuesto>> listarImpuestos() {
        List<Impuesto> impuestos = servicioImpuesto.listarImpuestos();
        return new ResponseEntity<>(impuestos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ASESORCOMERCIAL')")
    public ResponseEntity<Impuesto> buscarImpuestoPorId(@PathVariable Long id) {
        Impuesto impuesto = servicioImpuesto.buscarImpuestoPorId(id);
        return new ResponseEntity<>(impuesto, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ASESORCOMERCIAL')")
    public ResponseEntity<List<Impuesto>> buscarImpuestoPorNombre(@RequestParam String nombre) {
        List<Impuesto> impuestos = servicioImpuesto.buscarImpuestoPorNombre(nombre);
        return new ResponseEntity<>(impuestos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SECRETARIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarImpuesto(@PathVariable Long id) {
        servicioImpuesto.eliminarImpuesto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('SECRETARIO')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarImpuesto(@PathVariable Long id, @RequestBody Impuesto impuesto) {
        if (!id.equals(impuesto.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        servicioImpuesto.actualizarImpuesto(impuesto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SECRETARIO')")
    @PostMapping
    public ResponseEntity<Impuesto> crearImpuesto(@RequestBody Impuesto impuesto) {
        Impuesto respuesta = servicioImpuesto.crearImpuesto(impuesto);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }


}
