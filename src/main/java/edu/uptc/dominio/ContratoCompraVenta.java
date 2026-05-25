package edu.uptc.dominio;

import edu.uptc.dominio.Contrato;

public class ContratoCompraVenta {

    private String item;
    private String marca;
    private String modelo;
    private String serie;
    private double valorUnitario;
    private int cantidadAdquirir;

    public ContratoCompraVenta(String item, String marca, String modelo, String serie, double valorUnitario, int cantidadAdquirir) {
        this.item = item;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.valorUnitario = valorUnitario;
        this.cantidadAdquirir = cantidadAdquirir;
    }

    public ContratoCompraVenta(){}


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getCantidadAdquirir() {
        return cantidadAdquirir;
    }

    public void setCantidadAdquirir(int cantidadAdquirir) {
        this.cantidadAdquirir = cantidadAdquirir;
    }
}
