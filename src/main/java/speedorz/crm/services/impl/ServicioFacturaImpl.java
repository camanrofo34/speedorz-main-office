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
import java.util.stream.Stream;
import com.itextpdf.text.Element;


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
    
            Factura factura = repositorioFactura.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));
    
            OrdenCompra orden = factura.getOrdenCompra();
            Cliente cliente = orden.getCliente();
    
            // TÍTULO
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            document.add(new Paragraph("FACTURA", titleFont));
            document.add(Chunk.NEWLINE);
    
            // ENCABEZADO
            document.add(new Paragraph("Factura N°: " + factura.getId()));
            document.add(new Paragraph("Fecha de Emisión: " + factura.getFechaEmision()));
            document.add(new Paragraph("Estado de Pago: " + factura.getEstadoPago()));
            document.add(new Paragraph("Empresa: " + factura.getNombreEmpresa()));
            if (factura.getMetodoPago() != null) {
                document.add(new Paragraph("Método de Pago: " + factura.getMetodoPago().getNombre()));
            }
            document.add(Chunk.NEWLINE);
    
            // DATOS DEL CLIENTE
            Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            document.add(new Paragraph("Datos del Cliente", sectionFont));
            document.add(new Paragraph("Nombre Legal: " + cliente.getNombreLegal()));
            document.add(new Paragraph("Identificación: " + cliente.getNumeroIdentificacion()));
            document.add(new Paragraph("Dirección: " + cliente.getDireccion()));
            document.add(new Paragraph("Teléfono: " + cliente.getTelefono()));
            document.add(Chunk.NEWLINE);
    
            // TABLA DETALLE VEHÍCULOS
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2.5f, 1f, 2f, 2f, 2.5f, 2.5f, 2f});
    
            // Cabeceras
            Stream.of("Vehículo", "Cant", "Precio Unit.", "Subtotal", "Descuentos", "Impuestos", "Total")
                .forEach(header -> {
                    PdfPCell cell = new PdfPCell(new Phrase(header, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                });
    
            BigDecimal totalDescuentos = BigDecimal.ZERO;
            BigDecimal totalImpuestos = BigDecimal.ZERO;
            StringBuilder resumenDescuentos = new StringBuilder();
            StringBuilder resumenImpuestos = new StringBuilder();
    
            for (OrdenVehiculo ov : orden.getOrdenesVehiculo()) {
                BigDecimal subtotal = ov.getSubtotal();
    
                // Descuentos
                BigDecimal descuentosItem = BigDecimal.ZERO;
                StringBuilder descuentosBuilder = new StringBuilder();
                for (Descuento d : ov.getDescuentos()) {
                    BigDecimal valor = BigDecimal.valueOf(d.calcularDescuento(subtotal.doubleValue()));
                    descuentosItem = descuentosItem.add(valor);
                    totalDescuentos = totalDescuentos.add(valor);
                    resumenDescuentos.append("- ")
                        .append(d.getNombre())
                        .append(" (").append(d.getPorcentaje()).append("%): -$")
                        .append(valor.toBigInteger()).append("\n");
                    if (descuentosBuilder.length() > 0) descuentosBuilder.append(", ");
                    descuentosBuilder.append(d.getNombre()).append(" (").append(d.getPorcentaje()).append("%)");
                }
                if (descuentosBuilder.length() == 0) {
                    descuentosBuilder.append("Ninguno");
                }
    
                // Impuestos
                BigDecimal impuestosItem = BigDecimal.ZERO;
                StringBuilder impuestosBuilder = new StringBuilder();
                for (Impuesto i : ov.getImpuestos()) {
                    BigDecimal valor = BigDecimal.valueOf(i.calcularImpuesto(subtotal.doubleValue()));
                    impuestosItem = impuestosItem.add(valor);
                    totalImpuestos = totalImpuestos.add(valor);
                    resumenImpuestos.append("- ")
                        .append(i.getNombre())
                        .append(" (").append(i.getPorcentaje()).append("%): +$")
                        .append(valor.toBigInteger()).append("\n");
                    if (impuestosBuilder.length() > 0) impuestosBuilder.append(", ");
                    impuestosBuilder.append(i.getNombre()).append(" (").append(i.getPorcentaje()).append("%)");
                }
                if (impuestosBuilder.length() == 0) {
                    impuestosBuilder.append("Ninguno");
                }
    
                // Fila
                table.addCell(ov.getVehiculo().getNombre());
                table.addCell(String.valueOf(ov.getCantidad()));
                table.addCell("$" + ov.getPrecioUnitario());
                table.addCell("$" + subtotal);
                table.addCell(descuentosBuilder.toString());
                table.addCell(impuestosBuilder.toString());
                table.addCell("$" + ov.getTotal());
            }
    
            document.add(table);
            document.add(Chunk.NEWLINE);
    
            // TOTALES
            document.add(new Paragraph("Resumen Final", sectionFont));
            document.add(new Paragraph("Subtotal general: $" + orden.getSubtotal()));
            document.add(new Paragraph("Total descuentos: -$" + totalDescuentos.toBigInteger()));
            document.add(new Paragraph("Total impuestos: +$" + totalImpuestos.toBigInteger()));
            document.add(new Paragraph("TOTAL FINAL: $" + orden.getTotal()));
            document.add(Chunk.NEWLINE);
    
            // DETALLES DE DESCUENTOS E IMPUESTOS
            if (resumenDescuentos.length() > 0) {
                document.add(new Paragraph("Descuentos aplicados:", sectionFont));
                document.add(new Paragraph(resumenDescuentos.toString()));
            }
            if (resumenImpuestos.length() > 0) {
                document.add(new Paragraph("Impuestos aplicados:", sectionFont));
                document.add(new Paragraph(resumenImpuestos.toString()));
            }
    
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
