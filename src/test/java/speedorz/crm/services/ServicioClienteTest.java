package speedorz.crm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import speedorz.crm.domain.entities.Cliente;
import speedorz.crm.repository.RepositorioCliente;
import speedorz.crm.services.impl.ServicioClienteImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioClienteTest {

    @Mock
    private RepositorioCliente repositorioCliente;

    @InjectMocks
    private ServicioClienteImpl servicioCliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearCliente_createsAndReturnsCliente() {
        Cliente cliente = new Cliente();
        when(repositorioCliente.save(cliente)).thenReturn(cliente);

        Cliente result = servicioCliente.crearCliente(cliente);

        assertEquals(cliente, result);
        verify(repositorioCliente, times(1)).save(cliente);
    }

    @Test
    void actualizarCliente_updatesExistingCliente() {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1L);
        when(repositorioCliente.findById(1L)).thenReturn(Optional.of(cliente));

        servicioCliente.actualizarCliente(cliente);

        verify(repositorioCliente, times(1)).save(cliente);
    }

    @Test
    void actualizarCliente_throwsExceptionWhenClienteNotFound() {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1L);
        when(repositorioCliente.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> servicioCliente.actualizarCliente(cliente));
    }

    @Test
    void eliminarCliente_deletesClienteById() {
        servicioCliente.eliminarCliente(1L);

        verify(repositorioCliente, times(1)).deleteById(1L);
    }

    @Test
    void listarClientes_returnsAllClientes() {
        List<Cliente> clientes = Arrays.asList(new Cliente(), new Cliente());
        when(repositorioCliente.findAll()).thenReturn(clientes);

        List<Cliente> result = servicioCliente.listarClientes();

        assertEquals(clientes, result);
    }

    @Test
    void buscarClientePorId_returnsClienteWhenFound() {
        Cliente cliente = new Cliente();
        when(repositorioCliente.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = servicioCliente.buscarClientePorId(1L);

        assertEquals(cliente, result);
    }

    @Test
    void buscarClientePorId_throwsExceptionWhenClienteNotFound() {
        when(repositorioCliente.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> servicioCliente.buscarClientePorId(1L));
    }

    @Test
    void buscarClientePorNombreLegal_returnsClientesWhenFound() {
        List<Cliente> clientes = Arrays.asList(new Cliente(), new Cliente());
        when(repositorioCliente.findClientesByNombreLegalContainsIgnoreCase(anyString())).thenReturn(clientes);

        List<Cliente> result = servicioCliente.buscarClientePorNombreLegal("test");

        assertEquals(clientes, result);
    }
}