package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.domain.Descuento;
import speedorz.crm.repository.RepositorioDescuento;
import speedorz.crm.services.ServicioDescuento;

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
    public Descuento crearDescuento(String nombre, String descripcion, BigDecimal porcentaje) {
        Descuento descuento = new Descuento();
        descuento.setNombre(nombre);
        descuento.setDescripcion(descripcion);
        descuento.setPorcentaje(porcentaje);
        return repositorioDescuento.save(descuento);
    }

    @Override
    public void actualizarDescuento(Long id, String nombre, String descripcion, BigDecimal porcentaje) {
        Descuento descuento = repositorioDescuento.findById(id).orElseThrow();
        descuento.setNombre(nombre);
        descuento.setDescripcion(descripcion);
        descuento.setPorcentaje(porcentaje);
        repositorioDescuento.save(descuento);
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
        return repositorioDescuento.findDescuentosByNombreContainsIgnoreCase(nombre);
    }
}
