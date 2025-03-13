package speedorz.crm.services;

import java.io.IOException;

public interface ServicioInventario {

    byte[] generarReporteInventario() throws IOException;
}
