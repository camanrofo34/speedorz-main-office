package speedorz.crm.services;

import java.io.IOException;

public interface ServicioInformePedidos {
    byte[] generarReportePedidos() throws IOException;
}