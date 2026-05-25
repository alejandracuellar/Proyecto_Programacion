package edu.uptc.dominio;

import java.time.LocalDate;
import edu.uptc.enums.EstadoContrato;

public class Contrato {
    private String numeroContrato;
    private String objetoContrato;
    private LocalDate fechaCreacion;
    private Contratante contratante;
    private Contratista contratista;
    private double valorContrato;
    private LocalDate plazoEjecucion;
    private EstadoContrato estado;

    public Contrato(String numeroContrato, String objetoContrato, LocalDate fechaCreacion, Contratante contratante,
                    Contratista contratista, double valorContrato, LocalDate plazoEjecucion, EstadoContrato estado) {
        this.numeroContrato = numeroContrato;
        this.objetoContrato = objetoContrato;
        this.fechaCreacion = fechaCreacion;
        this.contratante = contratante;
        this.contratista = contratista;
        this.valorContrato = valorContrato;
        this.plazoEjecucion = plazoEjecucion;
        this.estado = estado;
    }

    public Contrato(){

    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public String getObjetoContrato() {
        return objetoContrato;
    }

    public void setObjetoContrato(String objetoContrato) {
        this.objetoContrato = objetoContrato;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Contratante getContratante() {
        return contratante;
    }

    public void setContratante(Contratante contratante) {
        this.contratante = contratante;
    }

    public Contratista getContratista() {
        return contratista;
    }

    public void setContratista(Contratista contratista) {
        this.contratista = contratista;
    }

    public double getValorContrato() {
        return valorContrato;
    }

    public void setValorContrato(double valorContrato) {
        this.valorContrato = valorContrato;
    }

    public LocalDate getPlazoEjecucion() {
        return plazoEjecucion;
    }

    public void setPlazoEjecucion(LocalDate plazoEjecucion) {
        this.plazoEjecucion = plazoEjecucion;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }
}
