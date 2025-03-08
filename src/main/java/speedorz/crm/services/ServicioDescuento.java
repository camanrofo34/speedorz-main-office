package speedorz.crm.services;

import speedorz.crm.domain.entities.Descuento;
import java.util.List;

/**
 * Servicio para la gestión de descuentos en el sistema CRM.
 * Proporciona operaciones CRUD y búsquedas avanzadas.
 */
public interface ServicioDescuento {

    /**
     * Crea un nuevo descuento en el sistema.
     *
     * @param descuento Objeto con los datos del descuento a registrar.
     * @return Descuento registrado con su ID asignado.
     */
    Descuento crearDescuento(Descuento descuento);

    /**
     * Actualiza la información de un descuento existente.
     *
     * @param descuento Objeto con los datos actualizados del descuento.
     *                 El ID del descuento debe estar presente y ser válido.
     */
    void actualizarDescuento(Descuento descuento);

    /**
     * Elimina un descuento del sistema por su ID.
     *
     * @param id Identificador único del descuento a eliminar.
     */
    void eliminarDescuento(Long id);

    /**
     * Obtiene una lista de todos los descuentos registrados en el sistema.
     *
     * @return Lista de descuentos disponibles.
     */
    List<Descuento> listarDescuentos();

    /**
     * Busca un descuento por su identificador único.
     *
     * @param id ID del descuento a buscar.
     * @return Descuento encontrado o {@code null} si no existe.
     */
    Descuento buscarDescuentoPorId(Long id);
}
