package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import speedorz.crm.domain.OrdenCompra;
import speedorz.crm.domain.dto.request.OrdenCompraDTO;
import speedorz.crm.services.ServicioOrdenCompra;
import speedorz.crm.services.impl.ServicioOrdenCompraImpl;

@Controller
@RequestMapping("/ordenescompra")
@PreAuthorize("hasRole('ASESORCOMERCIAL')")
public class ControladorOrdenCompra {

    private final ServicioOrdenCompra servicioOrdenCompra;

    @Autowired
    public ControladorOrdenCompra(ServicioOrdenCompraImpl servicioOrdenCompra) {
        this.servicioOrdenCompra = servicioOrdenCompra;
    }

    @PostMapping
    public ResponseEntity<OrdenCompra> plantarOrdenCompra(@RequestBody OrdenCompraDTO ordenCompra) {
        OrdenCompra respuesta = servicioOrdenCompra.plantarOrdenCompra(ordenCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
}
