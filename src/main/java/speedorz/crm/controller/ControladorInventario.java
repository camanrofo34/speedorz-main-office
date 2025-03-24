package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import speedorz.crm.services.ServicioInventario;
import speedorz.crm.services.impl.ServicioInventarioImpl;

@RestController
@RequestMapping("/reportes")
public class ControladorInventario {

    private final ServicioInventario servicioInventario;

    @Autowired
    public  ControladorInventario(ServicioInventarioImpl servicioInventario) {
        this.servicioInventario = servicioInventario;
    }

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

}
