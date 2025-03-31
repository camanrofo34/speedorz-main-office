package speedorz.crm.services;

import java.io.IOException;

/**
 * Servicio para la generación de reportes de inventario.
 * Proporciona métodos para generar reportes de inventario, perdidas y ganancias, pedidos, cuentas por cobrar, historico de precios y movimiento de inventario.
 */
public interface ServicioInventario {

    /**
     * Genera un reporte de inventario.
     *
     * @return un arreglo de bytes que representa el reporte de inventario.
     * @throws IOException
     */
    byte[] generarReporteInventario() throws IOException;

    /**
     * Genera un reporte de perdidas y ganancias.
     *
     * @return un arreglo de bytes que representa el reporte de perdidas y ganancias.
     * @throws IOException
     */
    byte[] generarReportePerdidasYGanancias() throws IOException;

    /**
     * Genera un reporte de pedidos.
     *
     * @return un arreglo de bytes que representa el reporte de pedidos.
     * @throws IOException
     */
    byte[] generarReportePedidos() throws IOException;

    /**
     * Genera un histórico de precios.
     *
     * @return un arreglo de bytes que representa el histórico de precios.
     * @throws IOException
     */
    byte[] generarHistoricoPrecios() throws IOException;

    /**
     * Genera un reporte de cuentas por cobrar.
     *
     * @return un arreglo de bytes que representa el reporte de cuentas por cobrar.
     * @throws IOException
     */
    byte[] generarReporteCuentasPorCobrar() throws IOException;

    /**
     * Genera un reporte de movimiento de inventario.
     *
     * @return un arreglo de bytes que representa el reporte de movimiento de inventario.
     * @throws IOException
     */
    byte[] generarReporteMovimientoInventario() throws IOException;

}
