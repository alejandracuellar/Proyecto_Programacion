package edu.uptc.dominio;
import java.time.LocalDate;

/**
 * Contrato de Compraventa de bienes.
 * Contiene únicamente los atributos específicos de este tipo de contrato.
 * La regla de validación (cantidad × valorUnitario == valorContrato)
 * está implementada en {@code ServicioContrato}.
 * Hereda de {@link Contrato}.
 *
 * @author Alejandra Cuellar, Laura Gonzalez, Elkin Pineda
 * @version 1.0
 */
public class ContratoCompraVenta extends Contrato{

    /** Nombre del ítem o bien a adquirir. */
    private String item;

    /** Marca del bien. */
    private String marca;

    /** Modelo del bien. */
    private String modelo;

    /** Serie o referencia del bien. */
    private String serie;

    /** Valor unitario de cada bien. */
    private double valorUnitario;

    /** Cantidad de bienes a adquirir. */
    private int cantidadAdquirir;

    /**
     * Constructor completo de ContratoCompraVenta.
     *
     * @param numeroContrato   Número único del contrato.
     * @param objetoContrato   Objeto del contrato.
     * @param fechaCreacion    Fecha de creación.
     * @param contratante      Contratante responsable.
     * @param contratista      Contratista ejecutor.
     * @param valorContrato    Valor total del contrato.
     * @param plazoEjecucion   Plazo de ejecución.
     * @param item             Nombre del bien.
     * @param marca            Marca del bien.
     * @param modelo           Modelo del bien.
     * @param serie            Serie del bien.
     * @param valorUnitario    Valor unitario.
     * @param cantidadAdquirir Cantidad a adquirir.
     */
    public ContratoCompraVenta(String numeroContrato, String objetoContrato, LocalDate fechaCreacion,
                               Contratante contratante, Contratista contratista, double valorContrato,
                               LocalDate plazoEjecucion, String item, String marca, String modelo, String serie,
                               double valorUnitario, int cantidadAdquirir) {
        super(numeroContrato, objetoContrato, fechaCreacion,
                contratante, contratista, valorContrato, plazoEjecucion);
        this.item             = item;
        this.marca            = marca;
        this.modelo           = modelo;
        this.serie            = serie;
        this.valorUnitario    = valorUnitario;
        this.cantidadAdquirir = cantidadAdquirir;
    }

    /** @return Nombre del ítem. */
    public String getItem() {
        return item;
    }

    /** @param item Nuevo ítem. */
    public void setItem(String item) {
        this.item = item;
    }

    /** @return Marca. */
    public String getMarca() {
        return marca;
    }

    /** @param marca Nueva marca. */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /** @return Modelo. */
    public String getModelo() {
        return modelo;
    }

    /** @param modelo Nuevo modelo. */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /** @return Serie. */
    public String getSerie() {
        return serie;
    }

    /** @param serie Nueva serie. */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /** @return Valor unitario. */
    public double getValorUnitario() {
        return valorUnitario;
    }

    /** @param valorUnitario Nuevo valor unitario. */
    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    /** @return Cantidad a adquirir. */
    public int getCantidadAdquirir() {
        return cantidadAdquirir;
    }


    /** @param cantidadAdquirir Nueva cantidad. */
    public void setCantidadAdquirir(int cantidadAdquirir) {
        this.cantidadAdquirir = cantidadAdquirir;
    }


    @Override
    public String toString() {
        return super.toString() +
                "Tipo: Compraventa" + "\n"+
                "Ítem: " + item +"\n"+
                "Marca: " + marca +"\n"+
                "Modelo: " + modelo +"\n"+
                "Serie: " + serie +"\n"+
                "Valor Unitario: $" + String.format("%,.2f", valorUnitario) +"\n"+
                "Cantidad: " + cantidadAdquirir;
    }

}
