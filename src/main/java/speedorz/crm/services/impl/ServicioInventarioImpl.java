package speedorz.crm.services.impl;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.Factura;
import speedorz.crm.domain.entities.HistorialPrecio;
import speedorz.crm.domain.entities.OrdenCompra;
import speedorz.crm.domain.entities.Vehiculo;
import speedorz.crm.repository.RepositorioFactura;
import speedorz.crm.repository.RepositorioHistorialPrecio;
import speedorz.crm.repository.RepositorioOrdenCompra;
import speedorz.crm.repository.RepositorioVehiculo;
import speedorz.crm.services.ServicioInventario;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ServicioInventarioImpl implements ServicioInventario {

    private final RepositorioVehiculo repositorioVehiculo;
    private final RepositorioFactura repositorioFactura;
    private  final RepositorioOrdenCompra repositorioOrdenCompra;
    private final RepositorioHistorialPrecio repositorioHistorialPrecio;

    @Autowired
    public ServicioInventarioImpl(RepositorioVehiculo repositorioVehiculo, RepositorioFactura repositorioFactura, RepositorioOrdenCompra repositorioOrdenCompra, RepositorioHistorialPrecio repositorioHistorialPrecio) {
        this.repositorioVehiculo = repositorioVehiculo;
        this.repositorioFactura = repositorioFactura;
        this.repositorioOrdenCompra = repositorioOrdenCompra;
        this.repositorioHistorialPrecio = repositorioHistorialPrecio;
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

    @Override
    public byte[] generarHistoricoPrecios() throws IOException {
        List<Vehiculo> vehiculos = repositorioVehiculo.findAll();

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
            Paragraph title = new Paragraph("Histórico de Precios de Vehículos", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            // Fecha
            Paragraph date = new Paragraph("Fecha: " + LocalDate.now(), new Font(Font.FontFamily.HELVETICA, 12));
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);

            document.add(new Paragraph("\n"));

            for (Vehiculo vehiculo : vehiculos) {
                document.add(new Paragraph("Vehículo: " + vehiculo.getMarca() + " " + vehiculo.getModelo()));
                document.add(new Paragraph("Historial de Precios:"));

                List<HistorialPrecio> historialPrecios = repositorioHistorialPrecio.findHistorialPreciosByVehiculo(vehiculo);
                if (historialPrecios.isEmpty()) {
                    document.add(new Paragraph("No hay registros de precios."));
                } else {
                    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                    for (HistorialPrecio historial : historialPrecios) {
                        dataset.addValue(historial.getPrecio(), "Precio", historial.getFechaRegistro().toString());
                    }

                    JFreeChart chart = ChartFactory.createLineChart(
                            "Evolución del Precio - " + vehiculo.getMarca() + " " + vehiculo.getModelo(),
                            "Fecha",
                            "Precio",
                            dataset,
                            PlotOrientation.VERTICAL,
                            true,
                            true,
                            false
                    );

                    ByteArrayOutputStream chartBaos = new ByteArrayOutputStream();
                    ChartUtilities.writeChartAsPNG(chartBaos, chart, 500, 300);
                    Image chartImage = Image.getInstance(chartBaos.toByteArray());
                    chartImage.scaleToFit(500, 300);
                    document.add(chartImage);
                }
                document.add(new Paragraph("\n"));
            }

            document.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            throw new IOException("Error al generar el reporte PDF", e);
        }
    }


    @Override
    public byte[] generarReportePerdidasYGanancias() throws IOException {
        List<Factura> facturasPagas = repositorioFactura.findByEstadoPago("PAGO");
        List<Factura> facturasNoPagas = repositorioFactura.findByEstadoPago("NO PAGO");

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
            Paragraph title = new Paragraph("Informe de Ventas", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            // Fecha
            Paragraph date = new Paragraph("Fecha: " + LocalDate.now(), new Font(Font.FontFamily.HELVETICA, 12));
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);

            document.add(new Paragraph("\n"));

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
            throw new IOException("Error al generar el reporte PDF", e);
        }
    }


}


