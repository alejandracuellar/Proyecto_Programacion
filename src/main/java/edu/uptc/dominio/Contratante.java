package edu.uptc.dominio;

import edu.uptc.dominio.Usuario;

public class Contratante {

    private String sector;
    private String nivelEntidad;
    private String codigoEntidad;

    public Contratante(String sector, String nivelEntidad, String codigoEntidad) {
        this.sector = sector;
        this.nivelEntidad = nivelEntidad;
        this.codigoEntidad = codigoEntidad;
    }

    public Contratante(){

    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getNivelEntidad() {
        return nivelEntidad;
    }

    public void setNivelEntidad(String nivelEntidad) {
        this.nivelEntidad = nivelEntidad;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }
}
