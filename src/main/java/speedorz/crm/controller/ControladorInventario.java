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


}
