package edu.uptc.controlador;

import edu.uptc.dominio.ReporteInterventoria;
import edu.uptc.servicios.ServicioReporte;
import java.util.List;

/**
 * Controlador para la consulta de reportes de interventoría.
 * Actúa como intermediario entre la vista (Application) y el ServicioReporte.
 * No contiene JOptionPane ni lógica de negocio.
 *
 * @author Alejandra Cuellar, Laura Gonzalez, Elkin Pineda
 * @version 1.0
 */
public class ControladorReporte {

    private ServicioReporte servicioReporte;

    /**
     * Constructor del ControladorReporte.
     *
     * @param servicioReporte Servicio de lógica de reportes.
     */
    public ControladorReporte(ServicioReporte servicioReporte) {
        this.servicioReporte = servicioReporte;
    }

    /**
     * Retorna todos los reportes de interventoría registrados.
     *
     * @return Lista de reportes (puede estar vacía).
     */
    public List<ReporteInterventoria> obtenerTodosLosReportes() {
        return servicioReporte.obtenerTodosLosReportes();
    }

    /**
     * Retorna los reportes de interventoría de un contrato específico.
     *
     * @param numeroContrato Número del contrato.
     * @return Lista de reportes del contrato (puede estar vacía).
     */
    public List<ReporteInterventoria> obtenerReportesPorContrato(String numeroContrato) {
        return servicioReporte.obtenerReportesPorContrato(numeroContrato);
    }
}