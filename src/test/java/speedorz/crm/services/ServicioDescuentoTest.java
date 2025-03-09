package speedorz.crm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import speedorz.crm.domain.entities.Descuento;
import speedorz.crm.repository.RepositorioDescuento;
import speedorz.crm.services.impl.ServicioDescuentoImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioDescuentoTest {

    @Mock
    private RepositorioDescuento repositorioDescuento;

    @InjectMocks
    private ServicioDescuentoImpl servicioDescuento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearDescuento_createsAndReturnsDescuento() {
        Descuento descuento = new Descuento();
        when(repositorioDescuento.save(descuento)).thenReturn(descuento);

        Descuento result = servicioDescuento.crearDescuento(descuento);

        assertEquals(descuento, result);
        verify(repositorioDescuento, times(1)).save(descuento);
    }

    @Test
    void actualizarDescuento_updatesExistingDescuento() {
        Descuento descuento = new Descuento();
        descuento.setId(1L);
        when(repositorioDescuento.findById(1L)).thenReturn(Optional.of(descuento));

        servicioDescuento.actualizarDescuento(descuento);

        verify(repositorioDescuento, times(1)).save(descuento);
    }

    @Test
    void actualizarDescuento_throwsExceptionWhenDescuentoNotFound() {
        Descuento descuento = new Descuento();
        descuento.setId(1L);
        when(repositorioDescuento.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> servicioDescuento.actualizarDescuento(descuento));
    }

    @Test
    void eliminarDescuento_deletesDescuentoById() {
        servicioDescuento.eliminarDescuento(1L);

        verify(repositorioDescuento, times(1)).deleteById(1L);
    }

    @Test
    void listarDescuentos_returnsAllDescuentos() {
        List<Descuento> descuentos = Arrays.asList(new Descuento(), new Descuento());
        when(repositorioDescuento.findAll()).thenReturn(descuentos);

        List<Descuento> result = servicioDescuento.listarDescuentos();

        assertEquals(descuentos, result);
    }

    @Test
    void buscarDescuentoPorId_returnsDescuentoWhenFound() {
        Descuento descuento = new Descuento();
        when(repositorioDescuento.findById(1L)).thenReturn(Optional.of(descuento));

        Descuento result = servicioDescuento.buscarDescuentoPorId(1L);

        assertEquals(descuento, result);
    }

    @Test
    void buscarDescuentoPorId_throwsExceptionWhenDescuentoNotFound() {
        when(repositorioDescuento.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> servicioDescuento.buscarDescuentoPorId(1L));
    }
}