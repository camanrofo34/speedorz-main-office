package speedorz.crm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import speedorz.crm.domain.entities.Factura;
import speedorz.crm.repository.RepositorioFactura;
import speedorz.crm.services.impl.ServicioFacturaImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioFacturaTest {

    @Mock
    private RepositorioFactura repositorioFactura;

    @InjectMocks
    private ServicioFacturaImpl servicioFactura;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generarFactura_createsAndReturnsFactura() {
        Factura factura = new Factura();
        when(repositorioFactura.save(factura)).thenReturn(factura);

        Factura result = servicioFactura.generarFactura(factura);

        assertEquals(factura, result);
        verify(repositorioFactura, times(1)).save(factura);
    }

    @Test
    void eliminarFactura_deletesFacturaById() {
        servicioFactura.eliminarFactura(1L);

        verify(repositorioFactura, times(1)).deleteById(1L);
    }

    @Test
    void buscarFacturaPorId_returnsFacturaWhenFound() {
        Factura factura = new Factura();
        when(repositorioFactura.findById(1L)).thenReturn(Optional.of(factura));

        Factura result = servicioFactura.buscarFacturaPorId(1L);

        assertEquals(factura, result);
    }

    @Test
    void buscarFacturaPorId_throwsExceptionWhenFacturaNotFound() {
        when(repositorioFactura.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> servicioFactura.buscarFacturaPorId(1L));
    }

    @Test
    void listarFacturas_returnsAllFacturas() {
        List<Factura> facturas = Arrays.asList(new Factura(), new Factura());
        when(repositorioFactura.findAll()).thenReturn(facturas);

        List<Factura> result = servicioFactura.listarFacturas();

        assertEquals(facturas, result);
    }
}