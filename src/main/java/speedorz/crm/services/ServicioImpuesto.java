package speedorz.crm.services;

import speedorz.crm.domain.entities.Impuesto;

import java.util.List;

public interface ServicioImpuesto {

    Impuesto crearImpuesto(Impuesto impuesto);

    void actualizarImpuesto(Impuesto impuesto);

    void eliminarImpuesto(Long id);

    Impuesto buscarImpuestoPorId(Long id);

    List<Impuesto> buscarImpuestoPorNombre(String nombre);

    List<Impuesto> listarImpuestos();

}
