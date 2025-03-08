package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import speedorz.crm.domain.entities.Impuesto;
import speedorz.crm.services.ServicioImpuesto;
import speedorz.crm.services.impl.ServicioImpuestoImpl;

import java.util.List;

/**
 * Controlador para la gestión de impuestos en el sistema CRM.
 * <p>
 * Proporciona endpoints para la creación, consulta, actualización y eliminación de impuestos.
 * Los accesos están restringidos según los roles de usuario.
 * </p>
 *
 * @author Camilo
 * @version 1.0
 */
@Controller
@RequestMapping("/impuestos")
public class ControladorImpuesto {

    private final ServicioImpuesto servicioImpuesto;

    /**
     * Constructor que inyecta el servicio de impuestos.
     *
     * @param servicioImpuesto Servicio encargado de la gestión de impuestos.
     */
    @Autowired
    public ControladorImpuesto(ServicioImpuestoImpl servicioImpuesto) {
        this.servicioImpuesto = servicioImpuesto;
    }

    /**
     * Lista todos los impuestos registrados en el sistema.
     * <p>
     * Accesible para usuarios con los roles "SECRETARIO" y "ASESORCOMERCIAL".
     * </p>
     *
     * @return `ResponseEntity<List<Impuesto>>` con la lista de impuestos y estado HTTP 200 (OK).
     */
    @GetMapping
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ASESORCOMERCIAL')")
    public ResponseEntity<List<Impuesto>> listarImpuestos() {
        List<Impuesto> impuestos = servicioImpuesto.listarImpuestos();
        return new ResponseEntity<>(impuestos, HttpStatus.OK);
    }

    /**
     * Busca un impuesto por su identificador único (ID).
     * <p>
     * Accesible para usuarios con los roles "SECRETARIO" y "ASESORCOMERCIAL".
     * </p>
     *
     * @param id Identificador único del impuesto.
     * @return `ResponseEntity<Impuesto>` con el impuesto encontrado y estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ASESORCOMERCIAL')")
    public ResponseEntity<Impuesto> buscarImpuestoPorId(@PathVariable Long id) {
        Impuesto impuesto = servicioImpuesto.buscarImpuestoPorId(id);
        return new ResponseEntity<>(impuesto, HttpStatus.OK);
    }

    /**
     * Elimina un impuesto del sistema.
     * <p>
     * Solo los usuarios con el rol "SECRETARIO" pueden acceder a este endpoint.
     * </p>
     *
     * @param id Identificador único del impuesto a eliminar.
     * @return `ResponseEntity<Void>` con estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SECRETARIO')")
    public ResponseEntity<Void> eliminarImpuesto(@PathVariable Long id) {
        servicioImpuesto.eliminarImpuesto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Actualiza los datos de un impuesto existente.
     * <p>
     * Solo los usuarios con el rol "SECRETARIO" pueden acceder a este endpoint.
     * </p>
     *
     * @param id       Identificador único del impuesto a actualizar.
     * @param impuesto Objeto Impuesto con los datos actualizados.
     * @return `ResponseEntity<Void>` con estado HTTP 200 (OK) si la actualización fue exitosa,
     * o HTTP 400 (BAD REQUEST) si los IDs no coinciden.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SECRETARIO')")
    public ResponseEntity<Void> actualizarImpuesto(@PathVariable Long id, @RequestBody Impuesto impuesto) {
        if (!id.equals(impuesto.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        servicioImpuesto.actualizarImpuesto(impuesto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Crea un nuevo impuesto en el sistema.
     * <p>
     * Solo los usuarios con el rol "SECRETARIO" pueden acceder a este endpoint.
     * </p>
     *
     * @param impuesto Objeto Impuesto con los datos a registrar.
     * @return `ResponseEntity<Impuesto>` con el impuesto creado y estado HTTP 201 (CREATED).
     */
    @PostMapping
    @PreAuthorize("hasRole('SECRETARIO')")
    public ResponseEntity<Impuesto> crearImpuesto(@RequestBody Impuesto impuesto) {
        Impuesto respuesta = servicioImpuesto.crearImpuesto(impuesto);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
}

