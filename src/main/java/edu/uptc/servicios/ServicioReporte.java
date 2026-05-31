package edu.uptc.servicios;

import edu.uptc.dominio.Contrato;
import edu.uptc.dominio.ReporteInterventoria;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de lógica de negocio para la gestión de reportes de interventoría.
 * Genera y almacena los reportes automáticamente cada vez que cambia
 * el estado de un contrato.
 * Forma parte de la capa de servicios en la arquitectura n-capas.
 *
 * @author Alejandra Cuellar, Laura Gonzalez, Elkin Pineda
 * @version 1.0
 */
public class ServicioReporte {

    /** Lista de reportes de interventoría generados en el sistema. */
    private List<ReporteInterventoria> reportes;

    /**
     * Constructor que inicializa la lista de reportes vacía.
     */
    public ServicioReporte() {
        reportes = new ArrayList<>();
    }

    /**
     * Genera y registra un nuevo reporte de interventoría para el contrato dado.
     * Este método es invocado automáticamente desde {@code ServicioContrato}
     * cada vez que se cambia el estado de un contrato.
     *
     * @param contrato  Contrato cuyo estado fue modificado.
     * @param informe   Texto que justifica el cambio de estado.
     * @param fechaHora Fecha y hora exacta de generación del reporte.
     * @return El reporte generado y registrado.
     * @throws IllegalArgumentException Si el informe está vacío.
     */
    public ReporteInterventoria generarReporte(Contrato contrato, String informe, LocalDateTime fechaHora) {
        if (informe == null || informe.trim().isEmpty()) {
            throw new IllegalArgumentException("El informe del reporte de interventoría es obligatorio.");
        }
        ReporteInterventoria reporte = new ReporteInterventoria(contrato, informe.trim(), fechaHora);
        reportes.add(reporte);
        return reporte;
    }

    /**
     * Retorna todos los reportes de interventoría registrados en el sistema.
     *
     * @return Lista de todos los reportes (puede estar vacía).
     */
    public List<ReporteInterventoria> obtenerTodosLosReportes() {
        return new ArrayList<>(reportes);
    }

    /**
     * Retorna los reportes de interventoría de un contrato específico.
     *
     * @param numeroContrato Número del contrato a filtrar.
     * @return Lista de reportes del contrato (puede estar vacía).
     */
    public List<ReporteInterventoria> obtenerReportesPorContrato(String numeroContrato) {
        List<ReporteInterventoria> resultado = new ArrayList<>();
        for (ReporteInterventoria r : reportes) {
            if (r.getContrato().getNumeroContrato().equals(numeroContrato)) {
                resultado.add(r);
            }
        }
        return resultado;
    }
}