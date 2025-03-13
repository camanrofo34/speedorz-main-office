package speedorz.crm.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.OrdenCompra;
import speedorz.crm.repository.RepositorioOrdenCompra;
import speedorz.crm.services.ServicioInformePedidos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ServicioInformePedidosImpl implements ServicioInformePedidos {

    private final RepositorioOrdenCompra repositorioOrdenCompra;

    @Autowired
    public ServicioInformePedidosImpl(RepositorioOrdenCompra repositorioOrdenCompra) {
        this.repositorioOrdenCompra = repositorioOrdenCompra;
    }

    @Override
    public byte[] generarReportePedidos() throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Logo
            String logoPath = "src/main/resources/speedorz_logo.jpg";
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(100, 100);
            logo.setAlignment(Element.ALIGN_RIGHT);
            document.add(logo);

            // Título
            Paragraph title = new Paragraph("Informe de Pedidos", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            // Fecha
            Paragraph date = new Paragraph("Fecha: " + LocalDate.now(), new Font(Font.FontFamily.HELVETICA, 12));
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);

            document.add(new Paragraph("\n"));

            // Datos de ejemplo (Reemplázalos con datos reales de la base de datos)
            List<OrdenCompra> orderData = repositorioOrdenCompra.findAll();

            // Tabla de pedidos
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("OrdenId", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD))));
            table.addCell(new PdfPCell(new Phrase("Cliente", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD))));
            table.addCell(new PdfPCell(new Phrase("Responsable", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD))));
            table.addCell(new PdfPCell(new Phrase("Fecha", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD))));
            table.addCell(new PdfPCell(new Phrase("Subtotal", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD))));
            table.addCell(new PdfPCell(new Phrase("Total", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD))));

            for (OrdenCompra row : orderData) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(row.getIdOrdenCompra()))));
                table.addCell(new PdfPCell(new Phrase(row.getCliente().getNombreLegal())));
                table.addCell(new PdfPCell(new Phrase(row.getResponsable().getNombreCompleto())));
                table.addCell(new PdfPCell(new Phrase(row.getFecha().toString())));
                table.addCell(new PdfPCell(new Phrase(row.getSubtotal().toString())));
                table.addCell(new PdfPCell(new Phrase(row.getTotal().toString())));
            }
            document.add(table);

            document.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}