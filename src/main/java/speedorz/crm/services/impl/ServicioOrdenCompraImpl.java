package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.*;
import speedorz.crm.domain.dto.request.OrdenCompraDTO;
import speedorz.crm.repository.*;
import speedorz.crm.services.ServicioOrdenCompra;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioOrdenCompraImpl implements ServicioOrdenCompra {

    private final RepositorioOrdenCompra repositorioOrdenCompra;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioCliente repositorioCliente;
    private final RepositorioVehiculo repositorioVehiculo;
    private final RepositorioOrdenVehiculo repositorioOrdenVehiculo;
    private final RepositorioImpuesto repositorioImpuesto;
    private final RepositorioDescuento repositorioDescuento;

    @Autowired
    public ServicioOrdenCompraImpl(RepositorioOrdenCompra repositorioOrdenCompra,
                                   RepositorioUsuario repositorioUsuario,
                                   RepositorioCliente repositorioCliente,
                                   RepositorioVehiculo repositorioVehiculo,
                                   RepositorioOrdenVehiculo repositorioOrdenVehiculo,
                                   RepositorioImpuesto repositorioImpuesto,
                                   RepositorioDescuento repositorioDescuento) {
        this.repositorioOrdenCompra = repositorioOrdenCompra;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioCliente = repositorioCliente;
        this.repositorioVehiculo = repositorioVehiculo;
        this.repositorioOrdenVehiculo = repositorioOrdenVehiculo;
        this.repositorioImpuesto = repositorioImpuesto;
        this.repositorioDescuento = repositorioDescuento;
    }

    @Override
    public OrdenCompra plantarOrdenCompra(OrdenCompraDTO ordenCompra) {

        // Recuperar usuario y cliente
        Usuario usuario = repositorioUsuario.findById(ordenCompra.getIdUsuario()).orElseThrow();
        Cliente cliente = repositorioCliente.findById(ordenCompra.getIdCliente()).orElseThrow();

        // Crear nueva orden de compra
        OrdenCompra nuevaOrdenCompra = new OrdenCompra();
        nuevaOrdenCompra.setResponsable(usuario);
        nuevaOrdenCompra.setCliente(cliente);
        nuevaOrdenCompra.setFecha(ordenCompra.getFecha());
        nuevaOrdenCompra.setTotal(BigDecimal.ZERO);
        nuevaOrdenCompra.setSubtotal(BigDecimal.ZERO);

        nuevaOrdenCompra = repositorioOrdenCompra.save(nuevaOrdenCompra);

        final double[] subtotal = {0.0};
        final double[] totalImpuestos = {0.0};
        final double[] totalDescuentos = {0.0};

        // Procesar cada vehículo en la orden
        OrdenCompra finalNuevaOrdenCompra = nuevaOrdenCompra;
        List<OrdenVehiculo> ordenVehiculos = ordenCompra.getOrdenVehiculos().stream().map(v -> {
            Vehiculo vehiculo = repositorioVehiculo.findById(v.getIdVehiculo()).orElseThrow();
            OrdenVehiculo ordenVehiculo = new OrdenVehiculo();
            ordenVehiculo.setOrdenCompra(finalNuevaOrdenCompra);
            ordenVehiculo.setVehiculo(vehiculo);
            ordenVehiculo.setCantidad(v.getCantidad());
            ordenVehiculo.setPrecioUnitario(BigDecimal.valueOf(vehiculo.getPrecio()));
            if (v.getCantidad() > vehiculo.getStock()) {
                throw new RuntimeException("No hay suficiente stock para el vehículo " + vehiculo.getNombre());
            }else{
                vehiculo.setStock(vehiculo.getStock() - v.getCantidad());
                repositorioVehiculo.save(vehiculo);
            }
            double subtotalVehiculo = v.getCantidad() * vehiculo.getPrecio();
            subtotal[0] += subtotalVehiculo;
            ordenVehiculo.setSubtotal(BigDecimal.valueOf(subtotalVehiculo));
            // Aplicar descuentos
            double finalSubtotalVehiculo = subtotalVehiculo;
            double descuentoTotalVehiculo = v.getIdDescuentos().stream()
                    .map(repositorioDescuento::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .mapToDouble(descuento -> descuento.calcularDescuento(finalSubtotalVehiculo))
                    .sum();

            subtotalVehiculo -= descuentoTotalVehiculo;
            totalDescuentos[0] += descuentoTotalVehiculo;

            double impuestoTotalVehiculo = v.getIdImpuestos().stream()
                    .map(repositorioImpuesto::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .mapToDouble(impuesto -> impuesto.calcularImpuesto(finalSubtotalVehiculo))
                    .sum();
            subtotalVehiculo += impuestoTotalVehiculo;
            totalImpuestos[0] += impuestoTotalVehiculo;

            ordenVehiculo.setTotal(BigDecimal.valueOf(subtotalVehiculo));
            return ordenVehiculo;
        }).toList();

        repositorioOrdenVehiculo.saveAll(ordenVehiculos);

        // Calcular total final
        double total = subtotal[0] + totalImpuestos[0] - totalDescuentos[0];

        nuevaOrdenCompra.setSubtotal(BigDecimal.valueOf(subtotal[0]));
        nuevaOrdenCompra.setTotal(BigDecimal.valueOf(total));

        return repositorioOrdenCompra.save(nuevaOrdenCompra);
    }

    @Override
    public void eliminarOrdenCompra(Long id) {
        repositorioOrdenCompra.deleteById(id);
    }

    @Override
    public OrdenCompra buscarOrdenCompraPorId(Long id) {
        return repositorioOrdenCompra.findById(id).orElseThrow();
    }

    @Override
    public List<OrdenCompra> listarOrdenCompras() {
        return repositorioOrdenCompra.findAll();
    }
}
