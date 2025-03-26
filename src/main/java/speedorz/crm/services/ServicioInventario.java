package speedorz.crm.services;

import java.io.IOException;

public interface ServicioInventario {

    byte[] generarReporteInventario() throws IOException;

    byte[] generarReportePerdidasYGanancias() throws IOException;

    byte[] generarReportePedidos() throws IOException;

    byte[] generarHistoricoPrecios() throws IOException;

    byte[] generarReporteCuentasPorCobrar() throws IOException;

    byte[] generarReporteMovimientoInventario() throws IOException;

}
