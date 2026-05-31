package edu.uptc.dominio;
import java.time.LocalDate;

/**
 * Contrato de Obra Pública.
 * Contiene únicamente los atributos específicos de este tipo de contrato.
 * La regla de validación está implementada en {@code ServicioContrato}.
 * Hereda de {@link Contrato}.
 *
 * @author Alejandra Cuellar, Laura Gonzalez, Elkin Pineda
 * @version 1.0
 */
public class ContratoObraPublica extends Contrato{

    /** Dirección urbana o rural donde se realizará la obra. */
    private String ubicacionObra;

    /** Área de intervención de la obra en metros cuadrados. */
    private double areaIntervencion;

    /**
     * Constructor completo de ContratoObraPublica.
     *
     * @param numeroContrato   Número único del contrato.
     * @param objetoContrato   Objeto del contrato.
     * @param fechaCreacion    Fecha de creación.
     * @param contratante      Contratante responsable.
     * @param contratista      Contratista ejecutor.
     * @param valorContrato    Valor total del contrato.
     * @param plazoEjecucion   Plazo de ejecución.
     * @param ubicacionObra    Dirección de la obra.
     * @param areaIntervencion Área de intervención en m².
     */
    public ContratoObraPublica(String numeroContrato, String objetoContrato, LocalDate fechaCreacion,
                               Contratante contratante, Contratista contratista, double valorContrato,
                               LocalDate plazoEjecucion, String ubicacionObra, double areaIntervencion) {
        super(numeroContrato, objetoContrato, fechaCreacion,
                contratante, contratista, valorContrato, plazoEjecucion);
        this.ubicacionObra    = ubicacionObra;
        this.areaIntervencion = areaIntervencion;
    }

    /** @return Ubicación de la obra. */
    public String getUbicacionObra(){
        return ubicacionObra;
    }

    /** @param ubicacionObra Nueva ubicación. */
    public void setUbicacionObra(String ubicacionObra){
        this.ubicacionObra = ubicacionObra;
    }

    /** @return Área de intervención. */
    public double getAreaIntervencion(){
        return areaIntervencion;
    }
    /** @param areaIntervencion Nueva área de intervención. */
    public void setAreaIntervencion(double areaIntervencion){
        this.areaIntervencion = areaIntervencion;
    }

    @Override
    public String toString() {
        return super.toString() + "\n"+
                "Tipo: Obra Pública" +"\n"+
                "Ubicación: " + ubicacionObra +"\n"+
                "Área Intervención: " + areaIntervencion + " m²";
    }


}
