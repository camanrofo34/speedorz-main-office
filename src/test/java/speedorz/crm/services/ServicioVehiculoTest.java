package speedorz.crm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import speedorz.crm.domain.entities.Vehiculo;
import speedorz.crm.repository.RepositorioHistorialPrecio;
import speedorz.crm.repository.RepositorioMovimientoInventario;
import speedorz.crm.repository.RepositorioVehiculo;
import speedorz.crm.services.impl.ServicioVehiculoImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioVehiculoTest {

    @Mock
    private RepositorioVehiculo repositorioVehiculo;

    @Mock
    private RepositorioMovimientoInventario repositorioMovimientoInventario;

    @Mock
    private RepositorioHistorialPrecio repositorioHistorialPrecio;

    @InjectMocks
    private ServicioVehiculoImpl servicioVehiculo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearVehiculo_createsAndReturnsVehiculo() {
        Vehiculo vehiculo = new Vehiculo();
        when(repositorioVehiculo.save(vehiculo)).thenReturn(vehiculo);
        Vehiculo result = servicioVehiculo.crearVehiculo(vehiculo);

        assertEquals(vehiculo, result);
        verify(repositorioVehiculo, times(1)).save(vehiculo);
    }

    @Test
    void actualizarVehiculo_updatesExistingVehiculo() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(1L);
        when(repositorioVehiculo.findById(1L)).thenReturn(Optional.of(vehiculo));

        servicioVehiculo.actualizarVehiculo(vehiculo);

        verify(repositorioVehiculo, times(1)).save(vehiculo);
    }

    @Test
    void actualizarVehiculo_throwsExceptionWhenVehiculoNotFound() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(1L);
        when(repositorioVehiculo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> servicioVehiculo.actualizarVehiculo(vehiculo));
    }

    @Test
    void eliminarVehiculo_deletesVehiculoById() {
        servicioVehiculo.eliminarVehiculo(1L);

        verify(repositorioVehiculo, times(1)).deleteById(1L);
    }

    @Test
    void listarVehiculos_returnsAllVehiculos() {
        List<Vehiculo> vehiculos = Arrays.asList(new Vehiculo(), new Vehiculo());
        when(repositorioVehiculo.findAll()).thenReturn(vehiculos);

        List<Vehiculo> result = servicioVehiculo.listarVehiculos();

        assertEquals(vehiculos, result);
    }

    @Test
    void buscarVehiculoPorId_returnsVehiculoWhenFound() {
        Vehiculo vehiculo = new Vehiculo();
        when(repositorioVehiculo.findById(1L)).thenReturn(Optional.of(vehiculo));

        Vehiculo result = servicioVehiculo.buscarVehiculoPorId(1L);

        assertEquals(vehiculo, result);
    }

    @Test
    void buscarVehiculoPorId_throwsExceptionWhenVehiculoNotFound() {
        when(repositorioVehiculo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> servicioVehiculo.buscarVehiculoPorId(1L));
    }

    @Test
    void buscarVehiculosPorNombre_returnsVehiculosWhenFound() {
        List<Vehiculo> vehiculos = Arrays.asList(new Vehiculo(), new Vehiculo());
        when(repositorioVehiculo.findVehiculosByNombreContainsIgnoreCase(anyString())).thenReturn(vehiculos);

        List<Vehiculo> result = servicioVehiculo.buscarVehiculosPorNombre("nombre");

        assertEquals(vehiculos, result);
    }
}