package speedorz.crm.services;

import speedorz.crm.domain.entities.Impuesto;

import java.util.List;

/**
 * Servicio para la gestión de impuestos en el sistema CRM.
 * Proporciona operaciones para la creación, actualización, eliminación y consulta de impuestos.
 */
public interface ServicioImpuesto {

    /**
     * Crea un nuevo impuesto en el sistema.
     *
     * @param impuesto Objeto que representa el impuesto a registrar.
     * @return Impuesto creado con su identificador asignado.
     */
    Impuesto crearImpuesto(Impuesto impuesto);

    /**
     * Actualiza la información de un impuesto existente.
     *
     * @param impuesto Objeto con los datos actualizados del impuesto.
     */
    void actualizarImpuesto(Impuesto impuesto);

    /**
     * Elimina un impuesto del sistema por su ID.
     *
     * @param id Identificador único del impuesto a eliminar.
     */
    void eliminarImpuesto(Long id);

    /**
     * Busca un impuesto por su identificador único.
     *
     * @param id ID del impuesto a buscar.
     * @return Impuesto encontrado o {@code null} si no existe.
     */
    Impuesto buscarImpuestoPorId(Long id);

    /**
     * Obtiene una lista de todos los impuestos registrados en el sistema.
     *
     * @return Lista de impuestos disponibles.
     */
    List<Impuesto> listarImpuestos();
}

