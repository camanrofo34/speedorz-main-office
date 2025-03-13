package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.dto.request.OrdenCompraDTO;
import speedorz.crm.domain.entities.*;
import speedorz.crm.repository.*;
import speedorz.crm.services.ServicioOrdenCompra;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación del servicio {@link ServicioOrdenCompra}.
 * Maneja la lógica de negocio para la gestión de órdenes de compra.
 */
@Service
public class ServicioOrdenCompraImpl implements ServicioOrdenCompra {

    private final RepositorioOrdenCompra repositorioOrdenCompra;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioCliente repositorioCliente;
    private final RepositorioVehiculo repositorioVehiculo;
    private final RepositorioOrdenVehiculo repositorioOrdenVehiculo;
    private final RepositorioImpuesto repositorioImpuesto;
    private final RepositorioDescuento repositorioDescuento;
    private final RepositorioMovimientoInventario repositorioMovimientoInventario;

    private final Logger logger = Logger.getLogger(ServicioOrdenCompraImpl.class.getName());

    @Autowired
    public ServicioOrdenCompraImpl(RepositorioOrdenCompra repositorioOrdenCompra,
                                   RepositorioUsuario repositorioUsuario,
                                   RepositorioCliente repositorioCliente,
                                   RepositorioVehiculo repositorioVehiculo,
                                   RepositorioOrdenVehiculo repositorioOrdenVehiculo,
                                   RepositorioImpuesto repositorioImpuesto,
                                   RepositorioDescuento repositorioDescuento,
                                   RepositorioMovimientoInventario repositorioMovimientoInventario) {
        this.repositorioOrdenCompra = repositorioOrdenCompra;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioCliente = repositorioCliente;
        this.repositorioVehiculo = repositorioVehiculo;
        this.repositorioOrdenVehiculo = repositorioOrdenVehiculo;
        this.repositorioImpuesto = repositorioImpuesto;
        this.repositorioDescuento = repositorioDescuento;
        this.repositorioMovimientoInventario = repositorioMovimientoInventario;
    }

    @Override
    public OrdenCompra plantarOrdenCompra(OrdenCompraDTO ordenCompra) {
        try {
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

            // Guardar para obtener el ID
            nuevaOrdenCompra = repositorioOrdenCompra.save(nuevaOrdenCompra);

            // Iniciar valores totales
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
                } else {
                    vehiculo.setStock(vehiculo.getStock() - v.getCantidad());
                    repositorioVehiculo.save(vehiculo);
                    MovimientoInventario movimientoInventario = new MovimientoInventario();
                    movimientoInventario.setVehiculo(vehiculo);
                    movimientoInventario.setCantidad(v.getCantidad());
                    movimientoInventario.setTipoMovimiento("Salida");
                    movimientoInventario.setFecha(ordenCompra.getFecha());
                    repositorioMovimientoInventario.save(movimientoInventario);
                }
                // Calcular subtotal final
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

                // Aplicar impuestos
                double impuestoTotalVehiculo = v.getIdImpuestos().stream()
                        .map(repositorioImpuesto::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .mapToDouble(impuesto -> impuesto.calcularImpuesto(finalSubtotalVehiculo))
                        .sum();
                subtotalVehiculo += impuestoTotalVehiculo;
                totalImpuestos[0] += impuestoTotalVehiculo;

                // Calcular y aplicar total final
                ordenVehiculo.setTotal(BigDecimal.valueOf(subtotalVehiculo));
                return ordenVehiculo;
            }).toList();

            // Guardar ordenes de vehículos
            repositorioOrdenVehiculo.saveAll(ordenVehiculos);

            // Calcular total final
            double total = subtotal[0] + totalImpuestos[0] - totalDescuentos[0];

            // Actualizar orden de compra con totales
            nuevaOrdenCompra.setSubtotal(BigDecimal.valueOf(subtotal[0]));
            nuevaOrdenCompra.setTotal(BigDecimal.valueOf(total));

            // Guardar orden de compra
            return repositorioOrdenCompra.save(nuevaOrdenCompra);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al plantar orden de compra", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminarOrdenCompra(Long id) {
        try {
            logger.log(Level.INFO, "Eliminando Orden de Compra {0}", id);
            repositorioOrdenCompra.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al eliminar Orden de Compra", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public OrdenCompra buscarOrdenCompraPorId(Long id) {
        try {
            logger.log(Level.INFO, "Buscando Orden de Compra {0}", id);
            return repositorioOrdenCompra.findById(id).orElseThrow();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar Orden de Compra", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<OrdenCompra> listarOrdenCompras() {
        try {
            logger.log(Level.INFO, "Listando Ordenes de Compra");
            return repositorioOrdenCompra.findAll();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al listar Ordenes de Compra", e);
            throw new RuntimeException(e);
        }
    }
}

