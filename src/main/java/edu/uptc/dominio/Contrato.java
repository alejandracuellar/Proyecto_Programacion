package edu.uptc.dominio;

import java.time.LocalDate;
import edu.uptc.enums.EstadoContrato;

/**
 * Clase abstracta que representa un contrato público.
 * Contiene únicamente los atributos comunes y sus accesores.
 * Toda la lógica de validación y gestión está en {@code ServicioContrato}.
 *
 * @author Alejandra Cuellar, Laura Gonzalez
 * @version 1.0
 */
public abstract class Contrato {


    /** Número único identificador del contrato. */
    private String numeroContrato;

    /** Descripción del objeto o finalidad del contrato. */
    private String objetoContrato;

    /** Fecha en que se creó el contrato en el sistema. */
    private LocalDate fechaCreacion;

    /** Contratante responsable que creó el contrato. */
    private Contratante contratante;

    /** Contratista que ejecutará el contrato (puede ser null inicialmente). */
    private Contratista contratista;

    /** Valor monetario total del contrato. */
    private double valorContrato;

    /** Fecha límite para la ejecución del contrato. */
    private LocalDate plazoEjecucion;

    /** Estado actual del contrato en su ciclo de vida. */
    private EstadoContrato estado;

    /**
     * Constructor completo de Contrato.
     * El estado inicial siempre es PUBLICADO.
     *
     * @param numeroContrato Número único del contrato.
     * @param objetoContrato Objeto o finalidad del contrato.
     * @param fechaCreacion  Fecha de creación.
     * @param contratante    Contratante responsable.
     * @param contratista    Contratista ejecutor (puede ser null).
     * @param valorContrato  Valor total del contrato.
     * @param plazoEjecucion Fecha límite de ejecución.
     */
    public Contrato(String numeroContrato, String objetoContrato, LocalDate fechaCreacion, Contratante contratante,
                    Contratista contratista, double valorContrato, LocalDate plazoEjecucion) {
        this.numeroContrato = numeroContrato;
        this.objetoContrato = objetoContrato;
        this.fechaCreacion  = fechaCreacion;
        this.contratante    = contratante;
        this.contratista    = contratista;
        this.valorContrato  = valorContrato;
        this.plazoEjecucion = plazoEjecucion;
        this.estado         = EstadoContrato.PUBLICADO;
    }

    /** @return Número del contrato. */
    public String getNumeroContrato() {
        return numeroContrato;
    }

    /** @param numeroContrato Nuevo número de contrato. */
    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    /** @return Objeto del contrato. */
    public String getObjetoContrato() {
        return objetoContrato;
    }

    /** @param objetoContrato Nuevo objeto del contrato. */
    public void setObjetoContrato(String objetoContrato) {
        this.objetoContrato = objetoContrato;
    }

    /** @return Fecha de creación. */
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    /** @param fechaCreacion Nueva fecha de creación. */
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /** @return Contratante del contrato. */
    public Contratante getContratante() {
        return contratante;
    }

    /** @param contratante Nuevo contratante. */
    public void setContratante(Contratante contratante) {
        this.contratante = contratante;
    }

    /** @return Contratista del contrato. */
    public Contratista getContratista() {
        return contratista;
    }

    /** @param contratista Nuevo contratista. */
    public void setContratista(Contratista contratista) {
        this.contratista = contratista;
    }

    /** @return Valor total del contrato. */
    public double getValorContrato() {
        return valorContrato;
    }

    /** @param valorContrato Nuevo valor del contrato. */
    public void setValorContrato(double valorContrato) {
        this.valorContrato = valorContrato;
    }

    /** @return Plazo de ejecución. */
    public LocalDate getPlazoEjecucion() {
        return plazoEjecucion;
    }

    /** @param plazoEjecucion Nuevo plazo de ejecución. */
    public void setPlazoEjecucion(LocalDate plazoEjecucion) {
        this.plazoEjecucion = plazoEjecucion;
    }

    /** @return Estado actual del contrato. */
    public EstadoContrato getEstado() {
        return estado;
    }

    /** @param estado Nuevo estado del contrato. */
    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Número: " + numeroContrato +  "\n"+
                "Objeto: " + objetoContrato + "\n"+
                "Valor: $" + String.format("%,.2f", valorContrato) + "\n"+
                "Estado: " + estado;
    }
}
