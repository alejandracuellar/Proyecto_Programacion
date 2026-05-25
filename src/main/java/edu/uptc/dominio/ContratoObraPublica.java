package edu.uptc.dominio;

import edu.uptc.dominio.Contrato;

public class ContratoObraPublica {

    private String ubicacionObraPublica;
    private double areaIntervencion;

    public ContratoObraPublica(String ubicacionObraPublica, double areaIntervencion) {
        this.ubicacionObraPublica = ubicacionObraPublica;
        this.areaIntervencion = areaIntervencion;
    }

    public ContratoObraPublica(){

    }

    public String getUbicacionObraPublica() {
        return ubicacionObraPublica;
    }

    public void setUbicacionObraPublica(String ubicacionObraPublica) {
        this.ubicacionObraPublica = ubicacionObraPublica;
    }

    public double getAreaIntervencion() {
        return areaIntervencion;
    }

    public void setAreaIntervencion(double areaIntervencion) {
        this.areaIntervencion = areaIntervencion;
    }
}
