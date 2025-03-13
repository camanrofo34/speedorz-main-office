package speedorz.crm.services.impl;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.Vehiculo;
import speedorz.crm.repository.RepositorioVehiculo;
import speedorz.crm.services.ServicioInventario;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ServicioInventarioImpl implements ServicioInventario {

    private final RepositorioVehiculo repositorioVehiculo;

    @Autowired
    public  ServicioInventarioImpl(RepositorioVehiculo repositorioVehiculo) {
        this.repositorioVehiculo = repositorioVehiculo;
    }

    @Override
    public byte[] generarReporteInventario() throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Logo (Ajusta la ruta según la ubicación del logo)
            String logoPath = "src/main/resources/speedorz_logo.jpg";
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(100, 100);
            logo.setAlignment(Element.ALIGN_RIGHT);
            document.add(logo);

            // Título
            Paragraph title = new Paragraph("Informe de Inventario", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            // Fecha
            Paragraph date = new Paragraph("Fecha: " + LocalDate.now(), new Font(Font.FontFamily.HELVETICA, 12));
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);

            document.add(new Paragraph("\n"));

            // Datos de ejemplo (Reemplázalos con datos reales de la base de datos)
            List<Vehiculo> inventoryData = repositorioVehiculo.findAll();

            // Tabla de inventario
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("VehículoId", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD))));
            table.addCell(new PdfPCell(new Phrase("Nombre", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD))));
            table.addCell(new PdfPCell(new Phrase("Descripción", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD))));
            table.addCell(new PdfPCell(new Phrase("Modelo", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD))));
            table.addCell(new PdfPCell(new Phrase("PrecioActual", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD))));
            table.addCell(new PdfPCell(new Phrase("Stock Actual", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD))));

            for (Vehiculo row : inventoryData) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(row.getIdVehiculo()))));
                table.addCell(new PdfPCell(new Phrase(row.getNombre())));
                table.addCell(new PdfPCell(new Phrase(row.getDescripcion())));
                table.addCell(new PdfPCell(new Phrase(row.getModelo())));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(row.getPrecio()))));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(row.getStock()))));
            }
            document.add(table);

            document.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}


