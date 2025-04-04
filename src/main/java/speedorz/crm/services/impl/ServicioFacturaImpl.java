package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
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

        // Obtener la factura con toda la información
        Factura factura = repositorioFactura.findById(facturaId)
            .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        OrdenCompra orden = factura.getOrdenCompra();
        Cliente cliente = orden.getCliente();

        // ENCABEZADO
        document.add(new Paragraph("Factura N°: " + factura.getId()));
        document.add(new Paragraph("Fecha de Emisión: " + factura.getFechaEmision()));
        document.add(new Paragraph("Estado de Pago: " + factura.getEstadoPago()));
        document.add(new Paragraph("Empresa: " + factura.getNombreEmpresa()));
        document.add(new Paragraph("Método de Pago: " + factura.getMetodoPago().getNombre()));
        document.add(Chunk.NEWLINE);

        // DATOS DEL CLIENTE
        document.add(new Paragraph("Cliente: " + cliente.getNombreLegal()));
        document.add(new Paragraph("NIT / Cédula: " + cliente.getNumeroIdentificacion()));
        document.add(new Paragraph("Dirección: " + cliente.getDireccion()));
        document.add(new Paragraph("Teléfono: " + cliente.getTelefono()));
        document.add(Chunk.NEWLINE);

        // DETALLE DE VEHÍCULOS
        BigDecimal totalDescuentos = BigDecimal.ZERO;
        BigDecimal totalImpuestos = BigDecimal.ZERO;

        document.add(new Paragraph("Detalle de vehículos:"));

        for (OrdenVehiculo ov : orden.getOrdenesVehiculo()) {
            document.add(new Paragraph("Vehículo: " + ov.getVehiculo().getNombre()));
            document.add(new Paragraph("Cantidad: " + ov.getCantidad()));
            document.add(new Paragraph("Precio Unitario: $" + ov.getPrecioUnitario()));
            document.add(new Paragraph("Subtotal: $" + ov.getSubtotal()));

            // Descuentos
            BigDecimal descuentos = ov.getDescuentos().stream()
                .map(d -> BigDecimal.valueOf(d.calcularDescuento(ov.getSubtotal().doubleValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            totalDescuentos = totalDescuentos.add(descuentos);
            for (Descuento d : ov.getDescuentos()) {
                document.add(new Paragraph("Descuento: " + d.getNombre() + " - " + d.getPorcentaje() + "%"));
            }

            // Impuestos
            BigDecimal impuestos = ov.getImpuestos().stream()
                .map(i -> BigDecimal.valueOf(i.calcularImpuesto(ov.getSubtotal().doubleValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            totalImpuestos = totalImpuestos.add(impuestos);
            for (Impuesto i : ov.getImpuestos()) {
                document.add(new Paragraph("Impuesto: " + i.getNombre() + " - " + i.getPorcentaje() + "%"));
            }

            document.add(new Paragraph("Total vehículo: $" + ov.getTotal()));
            document.add(Chunk.NEWLINE);
        }

        // TOTALES
        document.add(new Paragraph("Subtotal general: $" + orden.getSubtotal()));
        document.add(new Paragraph("Total descuentos: -$" + totalDescuentos));
        document.add(new Paragraph("Total impuestos: +$" + totalImpuestos));
        document.add(new Paragraph("TOTAL FINAL: $" + orden.getTotal()));

        document.close();
    } catch (DocumentException e) {
        throw new IOException("Error al generar el PDF", e);
    }

    return outputStream.toByteArray();
}

    
}
