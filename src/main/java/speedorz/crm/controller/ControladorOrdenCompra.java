package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import speedorz.crm.domain.dto.request.OrdenCompraDTO;
import speedorz.crm.domain.entities.OrdenCompra;
import speedorz.crm.services.ServicioOrdenCompra;
import speedorz.crm.services.impl.ServicioOrdenCompraImpl;

/**
 * Controlador para gestionar órdenes de compra en el sistema CRM.
 * <p>
 * Solo accesible para usuarios con el rol "ASESORCOMERCIAL".
 * </p>
 *
 * @author Camilo
 * @version 1.0
 */
@RestController
@RequestMapping("/ordenescompra")
@PreAuthorize("hasRole('ASESORCOMERCIAL')")
public class ControladorOrdenCompra {

    private final ServicioOrdenCompra servicioOrdenCompra;

    /**
     * Constructor que inyecta el servicio de órdenes de compra.
     *
     * @param servicioOrdenCompra Servicio encargado de la gestión de órdenes de compra.
     */
    @Autowired
    public ControladorOrdenCompra(ServicioOrdenCompraImpl servicioOrdenCompra) {
        this.servicioOrdenCompra = servicioOrdenCompra;
    }

    /**
     * Crea una nueva orden de compra en el sistema.
     * <p>
     * Accesible solo para usuarios con el rol "ASESORCOMERCIAL".
     * </p>
     *
     * @param ordenCompra DTO con los datos de la orden de compra.
     * @return `ResponseEntity<OrdenCompra>` con la orden creada y estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<?> crearOrdenCompra(@RequestBody OrdenCompraDTO ordenCompra) {
        try {
            OrdenCompra respuesta = servicioOrdenCompra.plantarOrdenCompra(ordenCompra);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la orden de compra: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

