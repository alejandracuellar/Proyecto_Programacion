package edu.uptc.servicios;

import edu.uptc.dominio.Contrato;
import edu.uptc.dominio.ReporteInterventoria;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de lógica de negocio para reportes de interventoría.
 * Genera y almacena reportes automáticamente cada vez que cambia el estado de un contrato.
 * No realiza ninguna interacción con el usuario.
 *
 * @author Alejandra Cuellar, Laura Gonzalez
 * @version 1.0
 */
public class ServicioReporte {

    private List<ReporteInterventoria> reportes;

    /** Constructor: inicializa la lista de reportes. */
    public ServicioReporte() {
        reportes = new ArrayList<>();
    }

    /**
     * Genera y registra un reporte de interventoría para el contrato indicado.
     * Es invocado automáticamente desde {@link ServicioContrato} al cambiar estado.
     *
     * @param contrato  Contrato cuyo estado fue modificado.
     * @param informe   Justificación del cambio.
     * @param fechaHora Fecha y hora de generación.
     * @return Reporte generado.
     * @throws IllegalArgumentException Si el informe está vacío.
     */
    public ReporteInterventoria generarReporte(Contrato contrato, String informe, LocalDateTime fechaHora) {
        if (informe == null || informe.trim().isEmpty())
            throw new IllegalArgumentException("El informe del reporte es obligatorio.");
        ReporteInterventoria reporte = new ReporteInterventoria(contrato, informe.trim(), fechaHora);
        reportes.add(reporte);
        return reporte;
    }

    /**
     * Retorna todos los reportes de interventoría registrados.
     *
     * @return Lista de reportes (puede estar vacía).
     */
    public List<ReporteInterventoria> obtenerTodosLosReportes() {
        return new ArrayList<>(reportes);
    }

    /**
     * Retorna los reportes de un contrato específico.
     *
     * @param numeroContrato Número del contrato.
     * @return Lista de reportes del contrato (puede estar vacía).
     */
    public List<ReporteInterventoria> obtenerReportesPorContrato(String numeroContrato) {
        List<ReporteInterventoria> resultado = new ArrayList<>();
        for (ReporteInterventoria r : reportes)
            if (r.getContrato().getNumeroContrato().equals(numeroContrato)) resultado.add(r);
        return resultado;
    }
}
