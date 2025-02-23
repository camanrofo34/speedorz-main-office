package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.Impuesto;
import speedorz.crm.repository.RepositorioImpuesto;
import speedorz.crm.services.ServicioImpuesto;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServicioImpuestoImpl implements ServicioImpuesto {

    private final RepositorioImpuesto repositorioImpuesto;

    @Autowired
    public ServicioImpuestoImpl(RepositorioImpuesto repositorioImpuesto) {
        this.repositorioImpuesto = repositorioImpuesto;
    }

    @Override
    public Impuesto crearImpuesto(String nombre, BigDecimal porcentaje, String descripcion) {
        Impuesto impuesto = new Impuesto();
        impuesto.setNombre(nombre);
        impuesto.setPorcentaje(porcentaje);
        impuesto.setDescripcion(descripcion);
        return repositorioImpuesto.save(impuesto);
    }

    @Override
    public void actualizarImpuesto(Long id, String nombre, BigDecimal porcentaje, String descripcion) {
        Impuesto impuesto = repositorioImpuesto.findById(id).orElseThrow();
        impuesto.setNombre(nombre);
        impuesto.setPorcentaje(porcentaje);
        impuesto.setDescripcion(descripcion);
        repositorioImpuesto.save(impuesto);
    }

    @Override
    public void eliminarImpuesto(Long id) {
        repositorioImpuesto.deleteById(id);
    }

    @Override
    public Impuesto buscarImpuestoPorId(Long id) {
        return repositorioImpuesto.findById(id).orElseThrow();
    }

    @Override
    public List<Impuesto> buscarImpuestoPorNombre(String nombre) {
        return repositorioImpuesto.findImpuestosByNombreContainsIgnoreCase(nombre);
    }

    @Override
    public List<Impuesto> listarImpuestos() {
        return repositorioImpuesto.findAll();
    }
}
