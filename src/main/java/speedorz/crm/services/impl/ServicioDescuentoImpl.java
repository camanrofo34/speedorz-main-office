package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.Descuento;
import speedorz.crm.repository.RepositorioDescuento;
import speedorz.crm.services.ServicioDescuento;
import speedorz.crm.util.NormalizadorBusquedaUtil;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServicioDescuentoImpl implements ServicioDescuento {

    private final RepositorioDescuento repositorioDescuento;

    @Autowired
    public ServicioDescuentoImpl(RepositorioDescuento repositorioDescuento) {
        this.repositorioDescuento = repositorioDescuento;
    }


    @Override
    public Descuento crearDescuento(Descuento descuento) {
        return repositorioDescuento.save(descuento);
    }

    @Override
    public void actualizarDescuento(Descuento descuento) {
        Descuento newDescuento = repositorioDescuento.findById(descuento.getId()).orElseThrow();
        newDescuento.setNombre(descuento.getNombre());
        newDescuento.setPorcentaje(descuento.getPorcentaje());
        newDescuento.setPorcentaje(descuento.getPorcentaje());
        repositorioDescuento.save(newDescuento);
    }

    @Override
    public void eliminarDescuento(Long id) {
        repositorioDescuento.deleteById(id);
    }

    @Override
    public List<Descuento> listarDescuentos() {
        return repositorioDescuento.findAll();
    }

    @Override
    public Descuento buscarDescuentoPorId(Long id) {
        return repositorioDescuento.findById(id).orElseThrow();
    }

    @Override
    public List<Descuento> buscarDescuentoPorNombre(String nombre) {
        String nombreBusqueda = NormalizadorBusquedaUtil.normalizarTexto(nombre);
        return repositorioDescuento.findDescuentosByNombreContainsIgnoreCase(nombreBusqueda);
    }
}
