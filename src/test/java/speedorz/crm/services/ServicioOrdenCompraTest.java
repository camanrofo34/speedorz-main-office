package speedorz.crm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import speedorz.crm.domain.dto.request.OrdenCompraDTO;
import speedorz.crm.domain.entities.*;
import speedorz.crm.repository.*;
import speedorz.crm.services.impl.ServicioOrdenCompraImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioOrdenCompraTest {

    @Mock
    private RepositorioOrdenCompra repositorioOrdenCompra;
    @Mock
    private RepositorioUsuario repositorioUsuario;
    @Mock
    private RepositorioCliente repositorioCliente;
    @Mock
    private RepositorioVehiculo repositorioVehiculo;
    @Mock
    private RepositorioOrdenVehiculo repositorioOrdenVehiculo;
    @Mock
    private RepositorioImpuesto repositorioImpuesto;
    @Mock
    private RepositorioDescuento repositorioDescuento;

    @InjectMocks
    private ServicioOrdenCompraImpl servicioOrdenCompra;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void plantarOrdenCompra_createsAndReturnsOrdenCompra() {
        OrdenCompraDTO ordenCompraDTO = new OrdenCompraDTO();
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(1L);
        vehiculo.setPrecio(100.0);
        vehiculo.setStock(10);
        OrdenCompraDTO.OrdenVehiculoDTO ordenVehiculo = new OrdenCompraDTO.OrdenVehiculoDTO();
        ordenVehiculo.setIdVehiculo(1L);
        ordenVehiculo.setCantidad(1);
        ordenVehiculo.setIdDescuentos(List.of());
        ordenVehiculo.setIdImpuestos(List.of());
        ordenCompraDTO.setOrdenVehiculos(List.of(ordenVehiculo));

        when(repositorioUsuario.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(repositorioCliente.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(repositorioVehiculo.findById(anyLong())).thenReturn(Optional.of(vehiculo));
        when(repositorioOrdenCompra.save(any(OrdenCompra.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(repositorioOrdenVehiculo.saveAll(anyList())).thenReturn(null);

        OrdenCompra result = servicioOrdenCompra.plantarOrdenCompra(ordenCompraDTO);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(100.0), result.getTotal());
        verify(repositorioOrdenCompra, times(2)).save(any(OrdenCompra.class));
    }

    @Test
    void plantarOrdenCompra_throwsExceptionWhenStockIsInsufficient() {
        OrdenCompraDTO ordenCompraDTO = new OrdenCompraDTO();
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPrecio(100.0);
        vehiculo.setStock(0);
        OrdenCompraDTO.OrdenVehiculoDTO ordenVehiculo = new OrdenCompraDTO.OrdenVehiculoDTO();
        ordenVehiculo.setCantidad(1);
        ordenCompraDTO.setOrdenVehiculos(List.of(ordenVehiculo));

        when(repositorioUsuario.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(repositorioCliente.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(repositorioVehiculo.findById(anyLong())).thenReturn(Optional.of(vehiculo));

        assertThrows(RuntimeException.class, () -> servicioOrdenCompra.plantarOrdenCompra(ordenCompraDTO));
    }

    @Test
    void eliminarOrdenCompra_deletesOrdenCompraById() {
        servicioOrdenCompra.eliminarOrdenCompra(1L);

        verify(repositorioOrdenCompra, times(1)).deleteById(1L);
    }

    @Test
    void buscarOrdenCompraPorId_returnsOrdenCompraWhenFound() {
        OrdenCompra ordenCompra = new OrdenCompra();
        when(repositorioOrdenCompra.findById(1L)).thenReturn(Optional.of(ordenCompra));

        OrdenCompra result = servicioOrdenCompra.buscarOrdenCompraPorId(1L);

        assertEquals(ordenCompra, result);
    }

    @Test
    void buscarOrdenCompraPorId_throwsExceptionWhenOrdenCompraNotFound() {
        when(repositorioOrdenCompra.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> servicioOrdenCompra.buscarOrdenCompraPorId(1L));
    }

    @Test
    void listarOrdenCompras_returnsAllOrdenCompras() {
        List<OrdenCompra> ordenCompras = Arrays.asList(new OrdenCompra(), new OrdenCompra());
        when(repositorioOrdenCompra.findAll()).thenReturn(ordenCompras);

        List<OrdenCompra> result = servicioOrdenCompra.listarOrdenCompras();

        assertEquals(ordenCompras, result);
    }
}