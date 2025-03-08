package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import speedorz.crm.domain.entities.Descuento;
import speedorz.crm.services.ServicioDescuento;
import speedorz.crm.services.impl.ServicioDescuentoImpl;

import java.util.List;

/**
 * Controlador para la gestión de descuentos en el sistema CRM.
 * <p>
 * Proporciona endpoints para la creación, consulta, actualización y eliminación de descuentos.
 * El acceso a los métodos está restringido según los roles de usuario.
 * </p>
 *
 * @author Camilo
 * @version 1.0
 */
@Controller
@RequestMapping("/descuentos")
public class ControladorDescuento {

    private final ServicioDescuento servicioDescuento;

    /**
     * Constructor que inyecta el servicio de descuentos.
     *
     * @param servicioDescuento Servicio para la gestión de descuentos.
     */
    @Autowired
    public ControladorDescuento(ServicioDescuentoImpl servicioDescuento) {
        this.servicioDescuento = servicioDescuento;
    }

    /**
     * Crea un nuevo descuento en el sistema.
     * <p>
     * Solo los usuarios con el rol "SECRETARIO" pueden acceder a este endpoint.
     * </p>
     *
     * @param descuento Objeto Descuento con los datos a registrar.
     * @return `ResponseEntity<Descuento>` con el descuento creado y estado HTTP 201 (CREATED).
     */
    @PostMapping
    @PreAuthorize("hasRole('SECRETARIO')")
    public ResponseEntity<Descuento> crearDescuento(@RequestBody Descuento descuento) {
        Descuento respuesta = servicioDescuento.crearDescuento(descuento);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    /**
     * Lista todos los descuentos registrados en el sistema.
     * <p>
     * Accesible para usuarios con los roles "SECRETARIO" y "ASESORCOMERCIAL".
     * </p>
     *
     * @return `ResponseEntity<List<Descuento>>` con la lista de descuentos y estado HTTP 200 (OK).
     */
    @GetMapping
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ASESORCOMERCIAL')")
    public ResponseEntity<List<Descuento>> listarDescuentos() {
        List<Descuento> descuentos = servicioDescuento.listarDescuentos();
        return new ResponseEntity<>(descuentos, HttpStatus.OK);
    }

    /**
     * Busca un descuento por su identificador único (ID).
     * <p>
     * Accesible para usuarios con los roles "SECRETARIO" y "ASESORCOMERCIAL".
     * </p>
     *
     * @param id Identificador único del descuento.
     * @return `ResponseEntity<Descuento>` con el descuento encontrado y estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ASESORCOMERCIAL')")
    public ResponseEntity<Descuento> buscarDescuentoPorId(@PathVariable Long id) {
        Descuento descuento = servicioDescuento.buscarDescuentoPorId(id);
        return new ResponseEntity<>(descuento, HttpStatus.OK);
    }

    /**
     * Actualiza los datos de un descuento existente.
     * <p>
     * Solo los usuarios con el rol "SECRETARIO" pueden acceder a este endpoint.
     * </p>
     *
     * @param id        Identificador único del descuento a actualizar.
     * @param descuento Objeto Descuento con los datos actualizados.
     * @return `ResponseEntity<Void>` con estado HTTP 200 (OK) si la actualización fue exitosa,
     * o HTTP 400 (BAD REQUEST) si los IDs no coinciden.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SECRETARIO')")
    public ResponseEntity<Void> actualizarDescuento(@PathVariable Long id, @RequestBody Descuento descuento) {
        if (!id.equals(descuento.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        servicioDescuento.actualizarDescuento(descuento);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Elimina un descuento del sistema.
     * <p>
     * Solo los usuarios con el rol "SECRETARIO" pueden acceder a este endpoint.
     * </p>
     *
     * @param id Identificador único del descuento a eliminar.
     * @return `ResponseEntity<Void>` con estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SECRETARIO')")
    public ResponseEntity<Void> eliminarDescuento(@PathVariable Long id) {
        servicioDescuento.eliminarDescuento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
