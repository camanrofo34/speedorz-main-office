package speedorz.crm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import speedorz.crm.domain.entities.Descuento;
import speedorz.crm.services.ServicioDescuento;
import speedorz.crm.services.impl.ServicioDescuentoImpl;

import java.util.List;

@Controller
@RequestMapping("/descuentos")
public class ControladorDescuento {

    private final ServicioDescuento servicioDescuento;

    @Autowired
    public ControladorDescuento(ServicioDescuentoImpl servicioDescuento) {
        this.servicioDescuento = servicioDescuento;
    }


    @PostMapping
    @PreAuthorize("hasRole('SECRETARIO')")
    public ResponseEntity<Descuento> crearDescuento(@RequestBody Descuento descuento) {
        Descuento respuesta = servicioDescuento.crearDescuento(descuento);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ASESORCOMERCIAL')")
    public ResponseEntity<List<Descuento>> listarDescuentos() {
        List<Descuento> descuentos = servicioDescuento.listarDescuentos();
        return new ResponseEntity<>(descuentos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ASESORCOMERCIAL')")
    public ResponseEntity<Descuento> buscarDescuentoPorId(@PathVariable Long id) {
        Descuento descuento = servicioDescuento.buscarDescuentoPorId(id);
        return new ResponseEntity<>(descuento, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ASESORCOMERCIAL')")

    public ResponseEntity<List<Descuento>> buscarDescuentoPorNombre(@RequestParam String nombre) {
        List<Descuento> descuentos = servicioDescuento.buscarDescuentoPorNombre(nombre);
        return new ResponseEntity<>(descuentos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SECRETARIO')")
    public ResponseEntity<Void> actualizarDescuento(@PathVariable Long id, @RequestBody Descuento descuento) {
        if (!id.equals(descuento.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        servicioDescuento.actualizarDescuento(descuento);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SECRETARIO')")
    public ResponseEntity<Void> eliminarDescuento(@PathVariable Long id) {
        servicioDescuento.eliminarDescuento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
