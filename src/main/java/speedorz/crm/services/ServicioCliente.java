package speedorz.crm.services;

import speedorz.crm.domain.Cliente;

import java.util.List;


public interface ServicioCliente {

    public void crearCliente(String nombreLegal, String numeroIdentificacion, String direccion, String telefono);

    public void actualizarCliente(Long id, String nombreLegal, String numeroIdentificacion, String direccion, String telefono);

    public void eliminarCliente(Long id);

    public List<Cliente> listarClientes();

    public Cliente buscarClientePorId(Long id);

    public List<Cliente> buscarClientePorNombreLegal(String nombreLegal);
}
