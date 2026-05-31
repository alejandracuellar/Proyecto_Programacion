package edu.uptc.excepciones;

import edu.uptc.enums.EstadoContrato;

/**
 * Excepción lanzada cuando se intenta hacer una transición de estado inválida.
 * El orden requerido es: PUBLICADO → LICITACION → ADJUDICADO → EJECUCION → FINALIZADO.
 *
 * @author Alejandra Cuellar, Laura González, Elkin Pineda
 * @version 1.0
 */
public class TransicionEstadoInvalidaException extends Exception {

    /**
     * Constructor con los estados actual y pretendido.
     *
     * @param estadoActual  Estado actual del contrato.
     * @param estadoIntento Estado al que se intentó pasar.
     */
    public TransicionEstadoInvalidaException(EstadoContrato estadoActual, EstadoContrato estadoIntento) {
        super("Transición inválida: no se puede pasar de " + estadoActual + " a " + estadoIntento +
                ". Orden requerido: PUBLICADO → LICITACION → ADJUDICADO → EJECUCION → FINALIZADO.");
    }
}