package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.Cliente;
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
    public Cliente crearCliente(Cliente cliente) {
        return repositorioCliente.save(cliente);
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        Cliente newCliente = repositorioCliente.findById(cliente.getIdCliente()).orElseThrow();
        newCliente.setIdCliente(cliente.getIdCliente());
        newCliente.setNombreLegal(cliente.getNombreLegal());
        newCliente.setNumeroIdentificacion(cliente.getNumeroIdentificacion());
        newCliente.setDireccion(cliente.getDireccion());
        newCliente.setTelefono(cliente.getTelefono());
        repositorioCliente.save(newCliente);
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
        return repositorioCliente.findById(id).orElseThrow(()-> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public List<Cliente> buscarClientePorNombreLegal(String nombreLegal) {
        String nombreLegalBusqueda = NormalizadorBusquedaUtil.normalizarTexto(nombreLegal);
        return repositorioCliente.findClientesByNombreLegalContainsIgnoreCase(nombreLegalBusqueda);
    }
}
