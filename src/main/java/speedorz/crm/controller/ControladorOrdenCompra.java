package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import speedorz.crm.domain.dto.request.OrdenCompraDTO;
import speedorz.crm.domain.entities.OrdenCompra;
import speedorz.crm.domain.entities.Factura;
import speedorz.crm.services.ServicioFactura;
import speedorz.crm.services.ServicioOrdenCompra;

import java.io.IOException;

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
    private final ServicioFactura servicioFactura;

    /**
     * Constructor que inyecta los servicios de órdenes de compra y facturación.
     *
     * @param servicioOrdenCompra Servicio encargado de la gestión de órdenes de compra.
     * @param servicioFactura Servicio encargado de la generación de facturas.
     */
    @Autowired
    public ControladorOrdenCompra(ServicioOrdenCompra servicioOrdenCompra, ServicioFactura servicioFactura) {
        this.servicioOrdenCompra = servicioOrdenCompra;
        this.servicioFactura = servicioFactura;
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

    /**
     * Genera un PDF de la factura asociada a una orden de compra.
     *
     * @param facturaId ID de la factura a generar en PDF.
     * @return `ResponseEntity<byte[]>` con el contenido del PDF y estado HTTP 200 (OK).
     */
    @GetMapping("/{facturaId}/pdf")
    public ResponseEntity<byte[]> generarFacturaPDF(@PathVariable Long facturaId) {
        try {
            byte[] pdfBytes = servicioFactura.generarFacturaPDF(facturaId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename("factura_" + facturaId + ".pdf")
                    .build());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Genera y guarda una factura a partir de una orden de compra existente.
     *
     * @param ordenId ID de la orden de compra asociada a la factura.
     * @return `ResponseEntity` con la factura creada o un error si falla.
     */
    @PostMapping("/{ordenId}/factura")
    public ResponseEntity<?> generarFactura(@PathVariable Long ordenId) {
        try {
            Factura factura = servicioFactura.generarFacturaDesdeOrden(ordenId);
            return ResponseEntity.status(HttpStatus.CREATED).body(factura);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar la factura: " + e.getMessage());
        }
    }
}