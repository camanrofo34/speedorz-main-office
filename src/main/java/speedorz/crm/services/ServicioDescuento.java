package speedorz.crm.services;

import speedorz.crm.domain.Descuento;

import java.math.BigDecimal;
import java.util.List;

public interface ServicioDescuento {

    Descuento crearDescuento(String nombre, String descripcion, BigDecimal porcentaje);

    void actualizarDescuento(Long id, String nombre, String descripcion, BigDecimal porcentaje);

    void eliminarDescuento(Long id);

    List<Descuento> listarDescuentos();

    Descuento buscarDescuentoPorId(Long id);

    List<Descuento> buscarDescuentoPorNombre(String nombre);
}
