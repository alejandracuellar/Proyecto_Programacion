package edu.uptc.dominio;

import edu.uptc.dominio.Usuario;

public class Contratista {

    private boolean entidadPublica;
    private String areaDesempenio;


    public Contratista(boolean entidadPublica, String areaDesempenio) {
        this.entidadPublica = entidadPublica;
        this.areaDesempenio = areaDesempenio;
    }

    public Contratista(){}

    public boolean isEntidadPublica() {
        return entidadPublica;
    }

    public void setEntidadPublica(boolean entidadPublica) {
        this.entidadPublica = entidadPublica;
    }

    public String getAreaDesempenio() {
        return areaDesempenio;
    }

    public void setAreaDesempenio(String areaDesempenio) {
        this.areaDesempenio = areaDesempenio;
    }
}
