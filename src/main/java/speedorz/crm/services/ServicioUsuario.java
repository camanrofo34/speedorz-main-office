package speedorz.crm.services;

import speedorz.crm.domain.entities.Usuario;

import java.util.List;

/**
 * Servicio para la gestión de usuarios dentro del CRM.
 * Proporciona operaciones para la creación, actualización, eliminación y consulta de usuarios.
 */
public interface ServicioUsuario {

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usuario Entidad que representa el usuario a registrar.
     * @return El Usuario creado con su identificador asignado.
     */
    Usuario crearUsuario(Usuario usuario);

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuario Entidad que contiene la información actualizada del usuario.
     */
    void actualizarUsuario(Usuario usuario);

    /**
     * Elimina un usuario del sistema por su ID.
     *
     * @param id Identificador único del usuario a eliminar.
     */
    void eliminarUsuario(Long id);

    /**
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     *
     * @return Lista de usuarios disponibles.
     */
    List<Usuario> listarUsuarios();

    /**
     * Busca un usuario por su identificador único.
     *
     * @param id ID del usuario a buscar.
     * @return Usuario encontrado o {@code null} si no existe.
     */
    Usuario buscarUsuarioPorId(Long id);

    /**
     * Busca usuarios cuyo nombre de usuario coincida parcial o totalmente con el criterio de búsqueda.
     *
     * @param nombreUsuario Nombre de usuario o parte del nombre a buscar.
     * @return Lista de usuarios que coincidan con el criterio de búsqueda.
     */
    List<Usuario> buscarUsuarioPorNombreUsuario(String nombreUsuario);

    /**
     * Cambia el estado de un usuario en el sistema (por ejemplo, activo/inactivo).
     *
     * @param id     Identificador único del usuario cuyo estado se modificará.
     * @param estado Nuevo estado a asignar al usuario.
     */
    void cambiarEstadoUsuario(Long id, String estado);
}

