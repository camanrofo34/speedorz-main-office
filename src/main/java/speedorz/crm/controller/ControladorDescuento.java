package speedorz.crm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import speedorz.crm.domain.Descuento;
import speedorz.crm.services.ServicioDescuento;
import speedorz.crm.services.impl.ServicioDescuentoImpl;

import java.util.List;

@Controller
@RequestMapping("/descuentos")
@PreAuthorize("hasRole('SECRETARIO')")
public class ControladorDescuento {

    private final ServicioDescuento servicioDescuento;

    @Autowired
    public ControladorDescuento(ServicioDescuentoImpl servicioDescuento) {
        this.servicioDescuento = servicioDescuento;
    }

    @PostMapping
    public ResponseEntity<Descuento> crearDescuento(@RequestBody Descuento descuento) {
        Descuento respuesta = servicioDescuento.crearDescuento(descuento.getNombre(), descuento.getDescripcion(), descuento.getPorcentaje());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Descuento>> listarDescuentos() {
        List<Descuento> descuentos = servicioDescuento.listarDescuentos();
        return new ResponseEntity<>(descuentos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Descuento> buscarDescuentoPorId(@PathVariable Long id) {
        Descuento descuento = servicioDescuento.buscarDescuentoPorId(id);
        return new ResponseEntity<>(descuento, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Descuento>> buscarDescuentoPorNombre(@RequestParam String nombre) {
        List<Descuento> descuentos = servicioDescuento.buscarDescuentoPorNombre(nombre);
        return new ResponseEntity<>(descuentos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarDescuento(@PathVariable Long id, @RequestBody Descuento descuento) {
        servicioDescuento.actualizarDescuento(id, descuento.getNombre(), descuento.getDescripcion(), descuento.getPorcentaje());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDescuento(@PathVariable Long id) {
        servicioDescuento.eliminarDescuento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
