package edu.uptc.dominio;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Clase que representa un reporte de interventoría.
 * Se genera automáticamente cada vez que cambia el estado de un contrato.
 * Solo contiene los datos del reporte y sus accesores.
 * La lógica de generación está en {@code ServicioReporte}.
 *
 * @author Alejandra Cuellar, Laura González
 * @version 1.0
 */
public class ReporteInterventoria {

    /**
     * Contrato al que corresponde este reporte.
     */
    private Contrato contrato;

    /**
     * Informe que justifica el cambio de estado del contrato.
     */
    private String informe;

    /**
     * Fecha y hora exacta de creación del reporte.
     */
    private LocalDateTime fechaHora;

    /**
     * Constructor de ReporteInterventoria.
     *
     * @param contrato  Contrato que fue modificado.
     * @param informe   Justificación del cambio de estado.
     * @param fechaHora Fecha y hora de creación.
     */
    public ReporteInterventoria(Contrato contrato, String informe, LocalDateTime fechaHora) {
        this.contrato = contrato;
        this.informe = informe;
        this.fechaHora = fechaHora;
    }

    /** @return Contrato del reporte. */
    public Contrato getContrato(){
        return contrato;
    }

    /** @param contrato Nuevo contrato. */
    public void setContrato(Contrato contrato){
        this.contrato = contrato;
    }

    /** @return Informe del reporte. */
    public String getInforme(){
        return informe;
    }

    /** @param informe Nuevo informe. */
    public void setInforme(String informe){
        this.informe = informe;
    }

    /** @return Fecha y hora del reporte. */
    public LocalDateTime getFechaHora(){
        return fechaHora;
    }

    /** @param fechaHora Nueva fecha y hora. */
    public void setFechaHora(LocalDateTime fechaHora){
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "REPORTE DE INTERVENTORÍA\n" +
                "Fecha y Hora: " + fechaHora.format(formato) + "\n" +
                "Contrato N°: " + contrato.getNumeroContrato() + "\n" +
                "Estado Actual: " + contrato.getEstado() + "\n" +
                "Informe: " + informe;
    }

}
