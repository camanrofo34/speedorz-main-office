package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.entities.Impuesto;
import speedorz.crm.repository.RepositorioImpuesto;
import speedorz.crm.services.ServicioImpuesto;
import speedorz.crm.util.NormalizadorBusquedaUtil;

import java.util.List;

@Service
public class ServicioImpuestoImpl implements ServicioImpuesto {

    private final RepositorioImpuesto repositorioImpuesto;

    @Autowired
    public ServicioImpuestoImpl(RepositorioImpuesto repositorioImpuesto) {
        this.repositorioImpuesto = repositorioImpuesto;
    }

    @Override
    public Impuesto crearImpuesto(Impuesto impuesto) {
        return repositorioImpuesto.save(impuesto);
    }

    @Override
    public void actualizarImpuesto(Impuesto impuesto) {
        Impuesto newImpuesto = repositorioImpuesto.findById(impuesto.getId()).orElseThrow();
        newImpuesto.setNombre(impuesto.getNombre());
        newImpuesto.setDescripcion(impuesto.getDescripcion());
        newImpuesto.setPorcentaje(impuesto.getPorcentaje());
        repositorioImpuesto.save(newImpuesto);
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
        String nombreBusqueda = NormalizadorBusquedaUtil.normalizarTexto(nombre);
        return repositorioImpuesto.findImpuestosByNombreContainsIgnoreCase(nombreBusqueda);
    }

    @Override
    public List<Impuesto> listarImpuestos() {
        return repositorioImpuesto.findAll();
    }
}
