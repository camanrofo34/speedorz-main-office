package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import speedorz.crm.domain.Cliente;
import speedorz.crm.services.ServicioCliente;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@PreAuthorize("hasRole('RECEPCION')")
public class ControladorCliente {

    private final ServicioCliente servicioCliente;

    @Autowired
    public ControladorCliente(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Cliente respuesta = servicioCliente.crearCliente(cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = servicioCliente.listarClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        Cliente cliente = servicioCliente.buscarClientePorId(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarClientePorNombreLegal(@RequestParam String nombreLegal) {
        List<Cliente> clientes = servicioCliente.buscarClientePorNombreLegal(nombreLegal);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        if (!id.equals(cliente.getIdCliente())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        servicioCliente.actualizarCliente(cliente);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        servicioCliente.eliminarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
