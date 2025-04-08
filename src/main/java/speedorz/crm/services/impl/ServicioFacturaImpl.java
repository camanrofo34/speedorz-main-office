package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import speedorz.crm.domain.entities.Cliente;
import speedorz.crm.domain.entities.Descuento;
import speedorz.crm.domain.entities.Factura;
import speedorz.crm.domain.entities.Impuesto;
import speedorz.crm.domain.entities.OrdenCompra;
import speedorz.crm.domain.entities.OrdenVehiculo;
import speedorz.crm.repository.RepositorioFactura;
import speedorz.crm.services.ServicioFactura;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;

import java.util.stream.Stream;
import com.itextpdf.text.Element;

import java.util.Set;
import java.util.Collections;
import java.io.File;

/**
 * Implementación del servicio {@link ServicioFactura}.
 * Maneja la lógica de negocio para la gestión de facturas.
 */
@Service
public class ServicioFacturaImpl implements ServicioFactura {

    private final RepositorioFactura repositorioFactura;
    private final Logger logger = Logger.getLogger(ServicioFacturaImpl.class.getName());

    @Autowired
    public ServicioFacturaImpl(RepositorioFactura repositorioFactura) {
        this.repositorioFactura = repositorioFactura;
    }

    @Override
    public Factura generarFactura(Factura factura) {
        try {
            logger.log(Level.INFO, "Buscando Factura por ID {0}", factura.getId());
            return repositorioFactura.save(factura);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al generar factura");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminarFactura(Long id) {
        try {
            logger.log(Level.INFO, "Eliminando Factura por ID {0}", id);
            repositorioFactura.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al eliminar factura");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Factura buscarFacturaPorId(Long id) {
        try {
            logger.log(Level.INFO, "Buscando Factura por ID {0}", id);
            return repositorioFactura.findById(id).orElseThrow(() -> new RuntimeException("Factura no encontrada"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar factura por ID", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Factura> listarFacturas() {
        try {
            logger.log(Level.INFO, "Listando Facturas");
            return repositorioFactura.findAll();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al listar facturas");
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] generarFacturaPDF(Long facturaId) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
    
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
    
            // Cargar factura y relaciones
            Factura factura = repositorioFactura.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));
    
            OrdenCompra orden = factura.getOrdenCompra();
            Cliente cliente = orden.getCliente();
    
            // === LOGO ===
            String logoPath = "src/main/resources/speedorz_logo.jpg";
            File logoFile = new File(logoPath);
            if (logoFile.exists()) {
                Image logo = Image.getInstance(logoPath);
                logo.scaleToFit(100, 100);
                logo.setAlignment(Element.ALIGN_RIGHT);
                document.add(logo);
            }
    
            // === TÍTULO ===
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph("FACTURA", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);
    
            // === ENCABEZADO DE FACTURA ===
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 11);
            document.add(new Paragraph("Factura N°: " + factura.getId(), normalFont));
            document.add(new Paragraph("Fecha de Emisión: " + factura.getFechaEmision(), normalFont));
            document.add(new Paragraph("Estado de Pago: " + factura.getEstadoPago(), normalFont));
            document.add(new Paragraph("Empresa: " + factura.getNombreEmpresa(), normalFont));
            if (factura.getMetodoPago() != null) {
                document.add(new Paragraph("Método de Pago: " + factura.getMetodoPago().getNombre(), normalFont));
            }
            document.add(Chunk.NEWLINE);
    
            // === DATOS DEL CLIENTE ===
            Font sectionFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
            document.add(new Paragraph("Datos del Cliente", sectionFont));
            document.add(new Paragraph("Nombre Legal: " + cliente.getNombreLegal(), normalFont));
            document.add(new Paragraph("Identificación: " + cliente.getNumeroIdentificacion(), normalFont));
            document.add(new Paragraph("Dirección: " + cliente.getDireccion(), normalFont));
            document.add(new Paragraph("Teléfono: " + cliente.getTelefono(), normalFont));
            document.add(Chunk.NEWLINE);
    
            // === TABLA DETALLE VEHÍCULOS ===
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2.5f, 1.3f, 2f, 2f, 2.5f, 2.5f, 2f});
    
            Font tableHeaderFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Font tableBodyFont = new Font(Font.FontFamily.HELVETICA, 9);
    
            String[] headers = {"Vehículo", "CNT", "Precio Unit.", "Subtotal", "Descuentos", "Impuestos", "Total"};

            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, tableHeaderFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
            }
    
            BigDecimal totalDescuentos = BigDecimal.ZERO;
            BigDecimal totalImpuestos = BigDecimal.ZERO;
            StringBuilder resumenDescuentos = new StringBuilder();
            StringBuilder resumenImpuestos = new StringBuilder();
    
            for (OrdenVehiculo ov : orden.getOrdenesVehiculo()) {
                BigDecimal subtotal = ov.getSubtotal();
    
                // === DESCUENTOS ===
                BigDecimal descuentosItem = BigDecimal.ZERO;
                StringBuilder descuentosBuilder = new StringBuilder();
                Set<Descuento> descuentos = ov.getDescuentos();
    
                if (descuentos != null && !descuentos.isEmpty()) {
                    for (Descuento d : descuentos) {
                        BigDecimal valor = BigDecimal.valueOf(d.calcularDescuento(subtotal.doubleValue()));
                        descuentosItem = descuentosItem.add(valor);
                        totalDescuentos = totalDescuentos.add(valor);
    
                        resumenDescuentos.append("- ").append(d.getNombre())
                            .append(" (").append(d.getPorcentaje()).append("%): -$")
                            .append(valor.toBigInteger()).append("\n");
    
                        if (descuentosBuilder.length() > 0) descuentosBuilder.append(", ");
                        descuentosBuilder.append(d.getNombre()).append(" (").append(d.getPorcentaje()).append("%)");
                    }
                } else {
                    descuentosBuilder.append("Ninguno");
                }
    
                // === IMPUESTOS ===
                BigDecimal impuestosItem = BigDecimal.ZERO;
                StringBuilder impuestosBuilder = new StringBuilder();
                Set<Impuesto> impuestos = ov.getImpuestos();
    
                if (impuestos != null && !impuestos.isEmpty()) {
                    for (Impuesto i : impuestos) {
                        BigDecimal valor = BigDecimal.valueOf(i.calcularImpuesto(subtotal.doubleValue()));
                        impuestosItem = impuestosItem.add(valor);
                        totalImpuestos = totalImpuestos.add(valor);
    
                        resumenImpuestos.append("- ").append(i.getNombre())
                            .append(" (").append(i.getPorcentaje()).append("%): +$")
                            .append(valor.toBigInteger()).append("\n");
    
                        if (impuestosBuilder.length() > 0) impuestosBuilder.append(", ");
                        impuestosBuilder.append(i.getNombre()).append(" (").append(i.getPorcentaje()).append("%)");
                    }
                } else {
                    impuestosBuilder.append("Ninguno");
                }
    
                // === FILA ===
                table.addCell(new PdfPCell(new Phrase(ov.getVehiculo().getNombre(), tableBodyFont)));
    
                PdfPCell cantidadCell = new PdfPCell(new Phrase(String.valueOf(ov.getCantidad()), tableBodyFont));
                cantidadCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cantidadCell);
    
                PdfPCell precioCell = new PdfPCell(new Phrase("$" + ov.getPrecioUnitario(), tableBodyFont));
                precioCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(precioCell);
    
                PdfPCell subtotalCell = new PdfPCell(new Phrase("$" + subtotal, tableBodyFont));
                subtotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(subtotalCell);
    
                PdfPCell descCell = new PdfPCell(new Phrase(descuentosBuilder.toString(), tableBodyFont));
                descCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(descCell);
    
                PdfPCell impCell = new PdfPCell(new Phrase(impuestosBuilder.toString(), tableBodyFont));
                impCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(impCell);
    
                PdfPCell totalCell = new PdfPCell(new Phrase("$" + ov.getTotal(), tableBodyFont));
                totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(totalCell);
            }
    
            document.add(table);
            document.add(Chunk.NEWLINE);
    
            // === DETALLES DE DESCUENTOS E IMPUESTOS ===
            if (resumenDescuentos.length() > 0) {
                document.add(new Paragraph("Descuentos aplicados:", sectionFont));
                document.add(new Paragraph(resumenDescuentos.toString(), normalFont));
            }
    
            if (resumenImpuestos.length() > 0) {
                document.add(new Paragraph("Impuestos aplicados:", sectionFont));
                document.add(new Paragraph(resumenImpuestos.toString(), normalFont));
            }
    
            document.add(Chunk.NEWLINE);
    
            // === RESUMEN FINAL ===
            document.add(new Paragraph("Resumen Final", sectionFont));
            document.add(new Paragraph("Subtotal general: $" + orden.getSubtotal(), normalFont));
            document.add(new Paragraph("Total descuentos: -$" + totalDescuentos.toBigInteger(), normalFont));
            document.add(new Paragraph("Total impuestos: +$" + totalImpuestos.toBigInteger(), normalFont));
            document.add(new Paragraph("TOTAL FINAL: $" + orden.getTotal(), normalFont));
    
            document.close();
        } catch (DocumentException e) {
            throw new IOException("Error al generar el PDF", e);
        }
    
        return outputStream.toByteArray();
    }
    

    


@Autowired
private speedorz.crm.repository.RepositorioOrdenCompra repositorioOrdenCompra;

@Override
public Factura generarFacturaDesdeOrden(Long ordenId) {
    OrdenCompra orden = repositorioOrdenCompra.findById(ordenId)
        .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada con ID: " + ordenId));

    Factura factura = new Factura();
    factura.setOrdenCompra(orden);
    factura.setFechaEmision(java.time.LocalDate.now());
    factura.setEstadoPago("PENDIENTE");
    factura.setNombreEmpresa("Speedorz S.A.S");
    //factura.setMetodoPago(orden.getMetodoPago());

    return repositorioFactura.save(factura);
}

}
