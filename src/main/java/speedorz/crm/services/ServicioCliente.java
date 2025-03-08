package speedorz.crm.services;

import speedorz.crm.domain.entities.Cliente;
import java.util.List;

/**
 * Servicio para la gestión de clientes en el sistema CRM.
 * Proporciona operaciones CRUD y búsquedas avanzadas.
 */
public interface ServicioCliente {

    /**
     * Crea un nuevo cliente en el sistema.
     *
     * @param cliente Cliente a registrar.
     * @return Cliente registrado con su ID asignado.
     */
    Cliente crearCliente(Cliente cliente);

    /**
     * Actualiza la información de un cliente existente.
     *
     * @param cliente Cliente con los datos actualizados.
     *               El ID del cliente debe estar presente y ser válido.
     */
    void actualizarCliente(Cliente cliente);

    /**
     * Elimina un cliente del sistema por su ID.
     *
     * @param id Identificador único del cliente a eliminar.
     */
    void eliminarCliente(Long id);

    /**
     * Obtiene una lista de todos los clientes registrados en el sistema.
     *
     * @return Lista de clientes existentes.
     */
    List<Cliente> listarClientes();

    /**
     * Busca un cliente por su identificador único.
     *
     * @param id ID del cliente a buscar.
     * @return Cliente encontrado o {@code null} si no existe.
     */
    Cliente buscarClientePorId(Long id);

    /**
     * Busca clientes cuyo nombre legal contenga el término especificado.
     * La búsqueda no distingue entre mayúsculas y minúsculas.
     *
     * @param nombreLegal Nombre (o parte del nombre) del cliente a buscar.
     * @return Lista de clientes que coincidan con el criterio de búsqueda.
     */
    List<Cliente> buscarClientePorNombreLegal(String nombreLegal);
}

