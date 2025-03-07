package speedorz.crm.services;

import speedorz.crm.domain.entities.Cliente;

import java.util.List;


public interface ServicioCliente {

    Cliente crearCliente(Cliente cliente);

    void actualizarCliente(Cliente cliente);

    void eliminarCliente(Long id);

    List<Cliente> listarClientes();

    Cliente buscarClientePorId(Long id);

    List<Cliente> buscarClientePorNombreLegal(String nombreLegal);

}
