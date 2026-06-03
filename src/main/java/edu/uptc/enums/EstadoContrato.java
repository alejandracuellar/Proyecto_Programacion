package edu.uptc.enums;

/**
 * Enumeración que representa los posibles estados de un contrato público.
 * Los estados siguen el ciclo de vida del contrato en orden estricto.
 *
 * @author Alejandra Cuellar, Laura Gonzalez
 * @version 1.0
 */
public enum EstadoContrato {

    /** El contrato ha sido publicado y está disponible para licitación. */
    PUBLICADO,

    /** El contrato está en proceso de licitación. */
    LICITACION,

    /** El contrato ha sido adjudicado a un contratista. */
    ADJUDICADO,

    /** El contrato está en fase de ejecución activa. */
    EJECUCION,

    /** El contrato ha sido finalizado completamente. */
    FINALIZADO
}
