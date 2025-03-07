package speedorz.crm.services;

import speedorz.crm.domain.entities.Descuento;

import java.util.List;

public interface ServicioDescuento {

    Descuento crearDescuento(Descuento descuento);

    void actualizarDescuento(Descuento descuento);

    void eliminarDescuento(Long id);

    List<Descuento> listarDescuentos();

    Descuento buscarDescuentoPorId(Long id);

    List<Descuento> buscarDescuentoPorNombre(String nombre);
}
