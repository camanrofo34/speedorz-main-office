package speedorz.crm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import speedorz.crm.domain.entities.Usuario;
import speedorz.crm.repository.RepositorioUsuario;
import speedorz.crm.services.impl.ServicioUsuarioImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioUsuarioTest {

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @InjectMocks
    private ServicioUsuarioImpl servicioUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearUsuario_createsAndReturnsUsuario() {
        Usuario usuario = new Usuario();
        when(repositorioUsuario.save(usuario)).thenReturn(usuario);

        Usuario result = servicioUsuario.crearUsuario(usuario);

        assertEquals(usuario, result);
        verify(repositorioUsuario, times(1)).save(usuario);
    }

    @Test
    void actualizarUsuario_updatesExistingUsuario() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));

        servicioUsuario.actualizarUsuario(usuario);

        verify(repositorioUsuario, times(1)).save(usuario);
    }

    @Test
    void actualizarUsuario_throwsExceptionWhenUsuarioNotFound() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> servicioUsuario.actualizarUsuario(usuario));
    }

    @Test
    void eliminarUsuario_deletesUsuarioById() {
        servicioUsuario.eliminarUsuario(1L);

        verify(repositorioUsuario, times(1)).deleteById(1L);
    }

    @Test
    void listarUsuarios_returnsAllUsuarios() {
        List<Usuario> usuarios = Arrays.asList(new Usuario(), new Usuario());
        when(repositorioUsuario.findAll()).thenReturn(usuarios);

        List<Usuario> result = servicioUsuario.listarUsuarios();

        assertEquals(usuarios, result);
    }

    @Test
    void buscarUsuarioPorId_returnsUsuarioWhenFound() {
        Usuario usuario = new Usuario();
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario result = servicioUsuario.buscarUsuarioPorId(1L);

        assertEquals(usuario, result);
    }

    @Test
    void buscarUsuarioPorId_throwsExceptionWhenUsuarioNotFound() {
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> servicioUsuario.buscarUsuarioPorId(1L));
    }

    @Test
    void buscarUsuarioPorNombreUsuario_returnsUsuariosWhenFound() {
        List<Usuario> usuarios = Arrays.asList(new Usuario(), new Usuario());
        when(repositorioUsuario.findUsuariosByNombreCompletoContainsIgnoreCase(anyString())).thenReturn(usuarios);

        List<Usuario> result = servicioUsuario.buscarUsuarioPorNombreUsuario("nombre");

        assertEquals(usuarios, result);
    }

    @Test
    void cambiarEstadoUsuario_updatesEstadoOfUsuario() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));

        servicioUsuario.cambiarEstadoUsuario(1L, "activo");

        assertEquals("activo", usuario.getEstado());
        verify(repositorioUsuario, times(1)).save(usuario);
    }

    @Test
    void loadUserByUsername_returnsUserDetailsWhenUsuarioFound() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("username");
        usuario.setContrasena("password");
        usuario.setRol("USER");
        when(repositorioUsuario.findUsuarioByNombreUsuario("username")).thenReturn(usuario);

        UserDetails userDetails = servicioUsuario.loadUserByUsername("username");

        assertEquals("username", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_throwsExceptionWhenUsuarioNotFound() {
        when(repositorioUsuario.findUsuarioByNombreUsuario("username")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> servicioUsuario.loadUserByUsername("username"));
    }
}