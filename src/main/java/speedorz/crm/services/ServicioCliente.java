package speedorz.crm.services;

import speedorz.crm.domain.Cliente;

import java.util.List;


public interface ServicioCliente {

    Cliente crearCliente(String nombreLegal, String numeroIdentificacion, String direccion, String telefono);

    void actualizarCliente(Long id, String nombreLegal, String numeroIdentificacion, String direccion, String telefono);

    void eliminarCliente(Long id);

    List<Cliente> listarClientes();

    Cliente buscarClientePorId(Long id);

    List<Cliente> buscarClientePorNombreLegal(String nombreLegal);

}
