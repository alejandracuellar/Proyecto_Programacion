package edu.uptc.dominio;

import edu.uptc.dominio.Contrato;

public class ContratoPrestacionServicio {
    private String perfilRequerido;
    private String entregables;
    private double valorHonorarioMensual;

    public ContratoPrestacionServicio(String perfilRequerido, String entregables, double valorHonorarioMensual) {
        this.perfilRequerido = perfilRequerido;
        this.entregables = entregables;
        this.valorHonorarioMensual = valorHonorarioMensual;
    }

    public ContratoPrestacionServicio(){}

    public String getPerfilRequerido() {
        return perfilRequerido;
    }

    public void setPerfilRequerido(String perfilRequerido) {
        this.perfilRequerido = perfilRequerido;
    }

    public String getEntregables() {
        return entregables;
    }

    public void setEntregables(String entregables) {
        this.entregables = entregables;
    }

    public double getValorHonorarioMensual() {
        return valorHonorarioMensual;
    }

    public void setValorHonorarioMensual(double valorHonorarioMensual) {
        this.valorHonorarioMensual = valorHonorarioMensual;
    }
}
