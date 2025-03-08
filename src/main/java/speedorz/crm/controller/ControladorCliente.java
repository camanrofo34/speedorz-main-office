package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import speedorz.crm.domain.entities.Cliente;
import speedorz.crm.services.ServicioCliente;

import java.util.List;

/**
 * Controlador para la gestión de clientes en el sistema CRM.
 * <p>
 * Este controlador proporciona endpoints para crear, listar, buscar, actualizar y eliminar clientes.
 * Solo los usuarios con el rol "RECEPCION" pueden acceder a estos endpoints.
 * </p>
 *
 * @author Camilo
 * @version 1.0
 */
@RestController
@RequestMapping("/clientes")
@PreAuthorize("hasRole('RECEPCION')") // Restringe el acceso a usuarios con el rol RECEPCION
public class ControladorCliente {

    private final ServicioCliente servicioCliente;

    /**
     * Constructor que inyecta el servicio de clientes.
     *
     * @param servicioCliente Servicio para la gestión de clientes.
     */
    @Autowired
    public ControladorCliente(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    /**
     * Crea un nuevo cliente en el sistema.
     *
     * @param cliente Objeto Cliente con los datos a registrar.
     * @return `ResponseEntity<Cliente>` con el cliente creado y estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Cliente respuesta = servicioCliente.crearCliente(cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    /**
     * Lista todos los clientes registrados en el sistema.
     *
     * @return `ResponseEntity<List<Cliente>>` con la lista de clientes y estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = servicioCliente.listarClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    /**
     * Busca un cliente por su identificador único (ID).
     *
     * @param id Identificador único del cliente.
     * @return `ResponseEntity<Cliente>` con el cliente encontrado y estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        Cliente cliente = servicioCliente.buscarClientePorId(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    /**
     * Busca clientes por su nombre legal.
     *
     * @param nombreLegal Nombre legal del cliente.
     * @return `ResponseEntity<List<Cliente>>` con la lista de clientes que coinciden y estado HTTP 200 (OK).
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarClientePorNombreLegal(@RequestParam String nombreLegal) {
        List<Cliente> clientes = servicioCliente.buscarClientePorNombreLegal(nombreLegal);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param id      Identificador único del cliente a actualizar.
     * @param cliente Objeto Cliente con los datos actualizados.
     * @return `ResponseEntity<Void>` con estado HTTP 200 (OK) si la actualización fue exitosa,
     * o HTTP 400 (BAD REQUEST) si los IDs no coinciden.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        if (!id.equals(cliente.getIdCliente())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        servicioCliente.actualizarCliente(cliente);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Elimina un cliente del sistema.
     *
     * @param id Identificador único del cliente a eliminar.
     * @return `ResponseEntity<Void>` con estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        servicioCliente.eliminarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
