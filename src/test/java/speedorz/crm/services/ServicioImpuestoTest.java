package speedorz.crm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import speedorz.crm.domain.entities.Impuesto;
import speedorz.crm.repository.RepositorioImpuesto;
import speedorz.crm.services.impl.ServicioImpuestoImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioImpuestoTest {

    @Mock
    private RepositorioImpuesto repositorioImpuesto;

    @InjectMocks
    private ServicioImpuestoImpl servicioImpuesto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearImpuesto_createsAndReturnsImpuesto() {
        Impuesto impuesto = new Impuesto();
        when(repositorioImpuesto.save(impuesto)).thenReturn(impuesto);

        Impuesto result = servicioImpuesto.crearImpuesto(impuesto);

        assertEquals(impuesto, result);
        verify(repositorioImpuesto, times(1)).save(impuesto);
    }

    @Test
    void actualizarImpuesto_updatesExistingImpuesto() {
        Impuesto impuesto = new Impuesto();
        impuesto.setId(1L);
        when(repositorioImpuesto.findById(1L)).thenReturn(Optional.of(impuesto));

        servicioImpuesto.actualizarImpuesto(impuesto);

        verify(repositorioImpuesto, times(1)).save(impuesto);
    }

    @Test
    void actualizarImpuesto_throwsExceptionWhenImpuestoNotFound() {
        Impuesto impuesto = new Impuesto();
        impuesto.setId(1L);
        when(repositorioImpuesto.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> servicioImpuesto.actualizarImpuesto(impuesto));
    }

    @Test
    void eliminarImpuesto_deletesImpuestoById() {
        servicioImpuesto.eliminarImpuesto(1L);

        verify(repositorioImpuesto, times(1)).deleteById(1L);
    }

    @Test
    void listarImpuestos_returnsAllImpuestos() {
        List<Impuesto> impuestos = Arrays.asList(new Impuesto(), new Impuesto());
        when(repositorioImpuesto.findAll()).thenReturn(impuestos);

        List<Impuesto> result = servicioImpuesto.listarImpuestos();

        assertEquals(impuestos, result);
    }

    @Test
    void buscarImpuestoPorId_returnsImpuestoWhenFound() {
        Impuesto impuesto = new Impuesto();
        when(repositorioImpuesto.findById(1L)).thenReturn(Optional.of(impuesto));

        Impuesto result = servicioImpuesto.buscarImpuestoPorId(1L);

        assertEquals(impuesto, result);
    }

    @Test
    void buscarImpuestoPorId_throwsExceptionWhenImpuestoNotFound() {
        when(repositorioImpuesto.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> servicioImpuesto.buscarImpuestoPorId(1L));
    }
}