package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.Cliente;
import speedorz.crm.repository.RepositorioCliente;
import speedorz.crm.services.ServicioCliente;
import speedorz.crm.util.NormalizadorBusquedaUtil;

import java.util.List;

@Service
public class ServicioClienteImpl implements ServicioCliente {

    private final RepositorioCliente repositorioCliente;

    @Autowired
    public ServicioClienteImpl(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }
    @Override
    public Cliente crearCliente(String nombreLegal, String numeroIdentificacion, String direccion, String telefono) {
        Cliente cliente = new Cliente();
        cliente.setNombreLegal(nombreLegal);
        cliente.setNumeroIdentificacion(numeroIdentificacion);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        return repositorioCliente.save(cliente);
    }

    @Override
    public void actualizarCliente(Long id, String nombreLegal, String numeroIdentificacion, String direccion, String telefono) {
        Cliente cliente = repositorioCliente.findById(id).orElseThrow();
        cliente.setNombreLegal(nombreLegal);
        cliente.setNumeroIdentificacion(numeroIdentificacion);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        repositorioCliente.save(cliente);
    }

    @Override
    public void eliminarCliente(Long id) {
        repositorioCliente.deleteById(id);
    }

    @Override
    public List<Cliente> listarClientes() {
        return repositorioCliente.findAll();
    }

    @Override
    public Cliente buscarClientePorId(Long id) {
        return repositorioCliente.findById(id).orElseThrow();
    }

    @Override
    public List<Cliente> buscarClientePorNombreLegal(String nombreLegal) {
        String nombreLegalBusqueda = NormalizadorBusquedaUtil.normalizarTexto(nombreLegal);
        return repositorioCliente.findClientesByNombreLegalContainsIgnoreCase(nombreLegalBusqueda);
    }
}
