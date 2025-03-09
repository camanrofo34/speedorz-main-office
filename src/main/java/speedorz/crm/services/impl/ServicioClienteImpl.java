package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.Cliente;
import speedorz.crm.repository.RepositorioCliente;
import speedorz.crm.services.ServicioCliente;
import speedorz.crm.util.NormalizadorBusquedaUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación del servicio {@link ServicioCliente}.
 * Maneja la lógica de negocio para la gestión de clientes.
 */
@Service
public class ServicioClienteImpl implements ServicioCliente {

    private final RepositorioCliente repositorioCliente;
    private final Logger logger = Logger.getLogger(ServicioClienteImpl.class.getName());

    @Autowired
    public ServicioClienteImpl(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        try {
            logger.log(Level.INFO, "Creando Cliente {0}", cliente.getNombreLegal());
            return repositorioCliente.save(cliente);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al crear Cliente", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        try {
            Cliente newCliente = repositorioCliente.findById(cliente.getIdCliente()).orElseThrow();
            newCliente.setIdCliente(cliente.getIdCliente());
            newCliente.setNombreLegal(cliente.getNombreLegal());
            newCliente.setNumeroIdentificacion(cliente.getNumeroIdentificacion());
            newCliente.setDireccion(cliente.getDireccion());
            newCliente.setTelefono(cliente.getTelefono());
            logger.log(Level.INFO, "Actualizando Cliente {0}", newCliente.getIdCliente());
            repositorioCliente.save(newCliente);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al actualizar Cliente", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminarCliente(Long id) {
        try {
            logger.log(Level.INFO, "Eliminando Cliente {0}", id);
            repositorioCliente.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al eliminar Cliente", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        try {
            logger.log(Level.INFO, "Listando Clientes");
            return repositorioCliente.findAll();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al listar Clientes", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cliente buscarClientePorId(Long id) {
        try {
            logger.log(Level.INFO, "Buscando Cliente por ID {0}", id);
            return repositorioCliente.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar Cliente por ID", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cliente> buscarClientePorNombreLegal(String nombreLegal) {
        try {
            logger.log(Level.INFO, "Buscando Cliente por Nombre Legal {0}", nombreLegal);
            String nombreLegalBusqueda = NormalizadorBusquedaUtil.normalizarTexto(nombreLegal);
            return repositorioCliente.findClientesByNombreLegalContainsIgnoreCase(nombreLegalBusqueda);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar Cliente por Nombre Legal", e);
            throw new RuntimeException(e);
        }
    }
}
