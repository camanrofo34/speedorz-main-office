package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import speedorz.crm.services.ServicioInventario;
import speedorz.crm.services.impl.ServicioInventarioImpl;


/**
 * Controlador para la generación de reportes de inventario.
 *
 * <p>
 *     Proporciona endpoints para la generación de reportes de inventario, pedidos, ventas, cuentas por cobrar y movimiento de inventario.
 *     Los accesos están restringidos según los roles de usuario.
 * </p>
 *
 * @author Camilo
 * @version 1.0
 */
@RestController
@RequestMapping("/reportes")
@PreAuthorize("hasRole('ADMININVENTARIO')")
public class ControladorInventario {

    private final ServicioInventario servicioInventario;

    /**
     * Constructor que inyecta el servicio de inventario.
     *
     * @param servicioInventario Servicio encargado de la gestión de inventario.
     */
    @Autowired
    public  ControladorInventario(ServicioInventarioImpl servicioInventario) {
        this.servicioInventario = servicioInventario;
    }

    /**
     * Genera un reporte de inventario en formato PDF.
     *
     * <p>
     *     Accesible solo para usuarios con el rol "ADMININVENTARIO".
     *     El reporte incluye información sobre los productos en inventario.
     *     Se incluyen detalles como el nombre del producto, cantidad en inventario y precio de venta.
     * </p>
     *
     * @return `ResponseEntity<byte[]>` con el reporte de inventario en formato PDF.
     */
    @GetMapping("/inventario")
    public ResponseEntity<byte[]> generarReporteInventario() {
        try{
            byte[] reporte = servicioInventario.generarReporteInventario();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte-inventario.pdf")
                    .body(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Genera un reporte de pedidos en formato PDF.
     *
     * <p>
     *     Accesible solo para usuarios con el rol "ADMININVENTARIO".
     *     El reporte incluye información sobre los pedidos realizados en el sistema.
     *     Se incluyen detalles como el número de pedido, fecha de creación, cliente, productos y cantidad.
     *</p>
     *
     * @return `ResponseEntity<byte[]>` con el reporte de pedidos en formato PDF.
     */
    @GetMapping("/pedidos")
    public ResponseEntity<byte[]> generarReportePedidos() {
        try{
            byte[] reporte = servicioInventario.generarReportePedidos();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte-pedidos.pdf")
                    .body(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    /**
     * Genera un reporte de ventas en formato PDF.
     *
     * <p>
     *     Accesible solo para usuarios con el rol "ADMININVENTARIO".
     *     El reporte incluye información sobre las ventas realizadas en el sistema.
     *     Se incluyen detalles como el número de venta, fecha de creación, cliente, productos y cantidad.
     * </p>
     *
     * @return `ResponseEntity<byte[]>` con el reporte de ventas en formato PDF.
     */
    @GetMapping("/ventas")
    public ResponseEntity<byte[]> generarReporteVentas() {
        try{
            byte[] reporte = servicioInventario.generarReportePerdidasYGanancias();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte-ventas.pdf")
                    .body(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Genera un reporte de historico de precios en formato PDF.
     *
     * <p>
     *     Accesible solo para usuarios con el rol "ADMININVENTARIO".
     *     El reporte incluye información sobre los precios de los productos en el tiempo.
     *     Se incluyen detalles como el nombre del producto, precio de venta y fecha de cambio.
     * </p>
     *
     * @return `ResponseEntity<byte[]>` con el reporte de historico de precios en formato PDF.
     */
    @GetMapping("/historico")
    public ResponseEntity<byte[]> generarHistoricoPrecios() {
        try{
            byte[] reporte = servicioInventario.generarHistoricoPrecios();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=historico-precios.pdf")
                    .body(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Genera un reporte de cuentas por cobrar en formato PDF.
     *
     * <p>
     *     Accesible solo para usuarios con el rol "ADMININVENTARIO".
     *     El reporte incluye información sobre las cuentas por cobrar en el sistema.
     *     Se incluyen detalles como el número de venta, fecha de creación, cliente, productos y cantidad.
     * </p>
     *
     * @return `ResponseEntity<byte[]>` con el reporte de cuentas por cobrar en formato PDF.
     */
    @GetMapping("/cuentas-por-cobrar")
    public ResponseEntity<byte[]> generarReporteCuentasPorCobrar() {
        try{
            byte[] reporte = servicioInventario.generarReporteCuentasPorCobrar();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte-cuentas-por-cobrar.pdf")
                    .body(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Genera un reporte de movimiento de inventario en formato PDF.
     *
     * <p>
     *     Accesible solo para usuarios con el rol "ADMININVENTARIO".
     *     El reporte incluye información sobre los movimientos de inventario en el sistema.
     *     Se incluyen detalles como el número de movimientos, fecha de creación, producto, cantidad y tipo de movimiento.
     * </p>
     *
     * @return `ResponseEntity<byte[]>` con el reporte de movimiento de inventario en formato PDF.
     */
    @GetMapping("/movimiento-inventario")
    public ResponseEntity<byte[]> generarReporteMovimientoInventario() {
        try{
            byte[] reporte = servicioInventario.generarReporteMovimientoInventario();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte-movimiento-inventario.pdf")
                    .body(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
