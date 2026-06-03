package edu.uptc.dominio;

import java.time.LocalDate;

/**
 * Contrato de Prestación de Servicios.
 * Contiene únicamente los atributos específicos de este tipo de contrato.
 * La regla de validación (honorarioMensual × meses == valorContrato)
 * está implementada en {@code ServicioContrato}.
 * Hereda de {@link Contrato}.
 *
 * @author Alejandra Cuellar, Laura Gonzalez
 * @version 1.0
 */
public class ContratoPrestacionServicio extends Contrato {

    /** Perfil profesional o técnico requerido para ejecutar el contrato. */
    private String perfilRequerido;

    /** Descripción de los entregables que debe cumplir el contratista. */
    private String entregables;

    /** Valor del honorario mensual pactado. */
    private double valorHonorarioMensual;

    /**
     * Constructor completo de ContratoPrestacionServicio.
     *
     * @param numeroContrato        Número único del contrato.
     * @param objetoContrato        Objeto del contrato.
     * @param fechaCreacion         Fecha de creación.
     * @param contratante           Contratante responsable.
     * @param contratista           Contratista ejecutor.
     * @param valorContrato         Valor total del contrato.
     * @param plazoEjecucion        Fecha límite de ejecución.
     * @param perfilRequerido       Perfil profesional requerido.
     * @param entregables           Entregables del contrato.
     * @param valorHonorarioMensual Valor mensual de honorarios.
     */
    public ContratoPrestacionServicio(String numeroContrato, String objetoContrato, LocalDate fechaCreacion,
                                      Contratante contratante, Contratista contratista, double valorContrato,
                                      LocalDate plazoEjecucion, String perfilRequerido, String entregables,
                                      double valorHonorarioMensual) {
        super(numeroContrato, objetoContrato, fechaCreacion,
                contratante, contratista, valorContrato, plazoEjecucion);
        this.perfilRequerido       = perfilRequerido;
        this.entregables           = entregables;
        this.valorHonorarioMensual = valorHonorarioMensual;
    }

    /** @return Perfil requerido. */
    public String getPerfilRequerido() {
        return perfilRequerido;
    }

    /** @param perfilRequerido Nuevo perfil requerido. */
    public void setPerfilRequerido(String perfilRequerido) {
        this.perfilRequerido = perfilRequerido;
    }

    /** @return Entregables. */
    public String getEntregables() {
        return entregables;
    }

    /** @param entregables Nuevos entregables. */
    public void setEntregables(String entregables) {
        this.entregables = entregables;
    }

    /** @return Honorario mensual. */
    public double getValorHonorarioMensual() {
        return valorHonorarioMensual;
    }

    /** @param valorHonorarioMensual Nuevo honorario mensual. */
    public void setValorHonorarioMensual(double valorHonorarioMensual) {
        this.valorHonorarioMensual = valorHonorarioMensual;
    }

    @Override
    public String toString() {
        return super.toString() + "\n"+
                "Tipo: Prestación de Servicios" + "\n"+
                "Perfil Requerido: " + perfilRequerido + "\n"+
                "Entregables: " + entregables + "\n"+
                "Honorario Mensual: $" + String.format("%,.2f", valorHonorarioMensual);
    }
}
