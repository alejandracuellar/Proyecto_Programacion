package edu.uptc.dominio;

import java.time.LocalDateTime;

public class ReporteInterventoria {
    private Contrato contrato;
    private String informe;
    private LocalDateTime fechaHora;

    public ReporteInterventoria(Contrato contrato, String informe, LocalDateTime fechaHora) {
        this.contrato = contrato;
        this.informe = informe;
        this.fechaHora = fechaHora;
    }

    public ReporteInterventoria(){}

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public String getInforme() {
        return informe;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
