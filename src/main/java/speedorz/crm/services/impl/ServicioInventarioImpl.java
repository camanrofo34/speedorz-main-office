package speedorz.crm.services.impl;


import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.TextAnchor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.*;
import speedorz.crm.repository.*;
import speedorz.crm.services.ServicioInventario;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementación del servicio de inventario {@link ServicioInventario}.
 * Proporciona métodos para generar reportes de inventario, pedidos, historial de precios, cuentas por cobrar y movimiento de inventario.
 */
@Service
public class ServicioInventarioImpl implements ServicioInventario {

    private final RepositorioVehiculo repositorioVehiculo;
    private final RepositorioFactura repositorioFactura;
    private  final RepositorioOrdenCompra repositorioOrdenCompra;
    private final RepositorioHistorialPrecio repositorioHistorialPrecio;
    private final RepositorioMovimientoInventario repositorioMovimientoInventario;

    @Autowired
    public ServicioInventarioImpl(RepositorioVehiculo repositorioVehiculo,
                                  RepositorioFactura repositorioFactura,
                                  RepositorioOrdenCompra repositorioOrdenCompra,
                                  RepositorioHistorialPrecio repositorioHistorialPrecio,
                                  RepositorioMovimientoInventario repositorioMovimientoInventario) {
        this.repositorioVehiculo = repositorioVehiculo;
        this.repositorioFactura = repositorioFactura;
        this.repositorioOrdenCompra = repositorioOrdenCompra;
        this.repositorioHistorialPrecio = repositorioHistorialPrecio;
        this.repositorioMovimientoInventario = repositorioMovimientoInventario;
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

// Configuración de la apariencia del gráfico
                    CategoryPlot plot = chart.getCategoryPlot();
                    plot.setBackgroundPaint(Color.WHITE); // Fondo blanco
                    plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
                    plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

// Obtener valores mínimo y máximo del dataset para ajustar la escala del eje Y
                    double minValue = Double.MAX_VALUE;
                    double maxValue = Double.MIN_VALUE;
                    for (HistorialPrecio historial : historialPrecios) {
                        double precio = historial.getPrecio().doubleValue();
                        if (precio < minValue) minValue = precio;
                        if (precio > maxValue) maxValue = precio;
                    }

// Ajustar la escala del eje Y con margen superior
                    double margen = (maxValue - minValue) * 0.15; // Un 15% de margen superior
                    if (margen == 0) margen = maxValue * 0.10; // Evitar margen 0 si todos los valores son iguales
                    plot.getRangeAxis().setRange(minValue - margen, maxValue + margen);

// Resaltar los puntos del gráfico
                    LineAndShapeRenderer renderer = new LineAndShapeRenderer();
                    renderer.setSeriesShapesVisible(0, true); // Hacer visibles los puntos
                    renderer.setSeriesPaint(0, Color.BLUE); // Color de la línea
                    renderer.setSeriesStroke(0, new BasicStroke(2.0f)); // Grosor de la línea
                    renderer.setSeriesShapesFilled(0, true); // Rellenar los puntos

// Agregar etiquetas con los valores exactos sobre los puntos, ajustando su posición
                    renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator() {
                        @Override
                        public String generateLabel(CategoryDataset dataset, int row, int column) {
                            return dataset.getValue(row, column).toString();
                        }
                    });
                    renderer.setBaseItemLabelsVisible(true);
                    renderer.setBaseItemLabelPaint(Color.BLACK); // Color del texto

// Ajustar la posición de las etiquetas para mejorar visibilidad
                    for (int i = 0; i < historialPrecios.size(); i++) {
                        ItemLabelPosition position;
                        if (i % 2 == 0) {
                            position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER);
                        } else {
                            position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_CENTER);
                        }
                        renderer.setSeriesPositiveItemLabelPosition(0, position);
                    }

                    plot.setRenderer(renderer);

// Generar imagen del gráfico
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
    public byte[] generarReporteCuentasPorCobrar() throws IOException {
        List<OrdenCompra> cuentasPorCobrar = repositorioOrdenCompra.findCuentasPorCobrar();

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
            Paragraph title = new Paragraph("Informe de Cuentas por Cobrar", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            // Fecha
            Paragraph date = new Paragraph("Fecha: " + LocalDate.now(), new Font(Font.FontFamily.HELVETICA, 12));
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);

            document.add(new Paragraph("\n"));

            // Tabla con datos de las órdenes de compra sin factura
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Encabezados
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            table.addCell(new PdfPCell(new Phrase("ID Orden", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Fecha", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Cliente", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Total", headerFont)));

            // Datos
            for (OrdenCompra orden : cuentasPorCobrar) {
                table.addCell(String.valueOf(orden.getIdOrdenCompra()));
                table.addCell(orden.getFecha().toString());
                table.addCell(orden.getCliente().getNombreLegal());
                table.addCell("$" + orden.getTotal().toString());
            }

            document.add(table);

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

    @Override
    public byte[] generarReporteMovimientoInventario() throws IOException {
        List<MovimientoInventario> movimientos = repositorioMovimientoInventario.findAll();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Verificar existencia del logo antes de cargarlo
            String logoPath = "src/main/resources/speedorz_logo.jpg";
            File logoFile = new File(logoPath);
            if (logoFile.exists()) {
                Image logo = Image.getInstance(logoPath);
                logo.scaleToFit(100, 100);
                logo.setAlignment(Element.ALIGN_RIGHT);
                document.add(logo);
            }

            // Título
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Informe de Movimiento de Inventario", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            // Fecha
            Font dateFont = new Font(Font.FontFamily.HELVETICA, 12);
            Paragraph date = new Paragraph("Fecha: " + LocalDate.now(), dateFont);
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Reporte de Movimiento de Inventario"));

            // Tabla con datos de los movimientos de inventario
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Encabezados con formato
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            String[] headers = {"Movimiento Id", "Tipo", "Fecha", "Cantidad", "Vehículo"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
            }

            // Datos de la tabla
            Font dataFont = new Font(Font.FontFamily.HELVETICA, 12);
            for (MovimientoInventario movimiento : movimientos) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(movimiento.getId()), dataFont)));
                table.addCell(new PdfPCell(new Phrase(movimiento.getTipoMovimiento(), dataFont)));
                table.addCell(new PdfPCell(new Phrase(movimiento.getFecha().toString(), dataFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(movimiento.getCantidad()), dataFont)));
                table.addCell(new PdfPCell(new Phrase(movimiento.getVehiculo().getNombre(), dataFont)));
            }

            // Se agrega la tabla al documento
            document.add(table);
            document.close();

            return baos.toByteArray();
        } catch (DocumentException e) {
            throw new IOException("Error al generar el reporte PDF", e);
        }
    }




}


