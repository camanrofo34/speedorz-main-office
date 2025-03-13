package speedorz.crm.services.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.Factura;
import speedorz.crm.repository.RepositorioFactura;
import speedorz.crm.services.ServicioInformePerdidasYGanancias;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ServicioInformePerdidasYGananciasImpl implements ServicioInformePerdidasYGanancias {

    private final RepositorioFactura repositorioFactura;
    private final Logger logger = Logger.getLogger(ServicioInformePerdidasYGananciasImpl.class.getName());

    @Autowired
    public ServicioInformePerdidasYGananciasImpl(RepositorioFactura repositorioFactura) {
        this.repositorioFactura = repositorioFactura;
    }

    @Override
    public byte[] generarReportePerdidasYGanancias() throws IOException {
        List<Factura> facturasPagas = repositorioFactura.findByEstadoPago("PAGO");
        List<Factura> facturasNoPagas = repositorioFactura.findByEstadoPago("NO PAGO");

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            document.add(new Paragraph("Reporte de Pérdidas y Ganancias"));
            document.add(new Paragraph("Facturas Pagas:"));
            for (Factura factura : facturasPagas) {
                document.add(new Paragraph("Factura ID: " + factura.getId() + ", Empresa: " + factura.getNombreEmpresa() + ", Total: " + factura.getOrdenCompra().getTotal()));
            }

            document.add(new Paragraph("Facturas No Pagas:"));
            for (Factura factura : facturasNoPagas) {
                document.add(new Paragraph("Factura ID: " + factura.getId() + ", Empresa: " + factura.getNombreEmpresa() + ", Total: " + factura.getOrdenCompra().getTotal()));
            }

            document.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            logger.log(Level.SEVERE, "Error al generar el reporte PDF", e);
            throw new IOException("Error al generar el reporte PDF", e);
        }
    }
}