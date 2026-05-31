package edu.uptc.excepciones;

import edu.uptc.enums.EstadoContrato;

/**
 * Excepción lanzada cuando se intenta actualizar un contrato que no
 * está en estado PUBLICADO (único estado que permite modificación).
 *
 * @author Alejandra Cuellar, Laura González, Elkin Pineda
 * @version 1.0
 */
public class ActualizacionNoPermitidaException extends Exception {

    /**
     * Constructor con el estado actual del contrato que impide la actualización.
     *
     * @param estadoActual Estado actual del contrato.
     */
    public ActualizacionNoPermitidaException(EstadoContrato estadoActual) {
        super("No se puede actualizar el contrato porque se encuentra en estado: "  + estadoActual +
                ". Solo se pueden modificar contratos en estado PUBLICADO.");
    }
}