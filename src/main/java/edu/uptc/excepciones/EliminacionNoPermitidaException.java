package edu.uptc.excepciones;

import edu.uptc.enums.EstadoContrato;

/**
 * Excepción lanzada cuando se intenta eliminar un contrato que no
 * está en estado PUBLICADO (único estado que permite eliminación).
 *
 * @author Alejandra Cuellar, Laura González, Elkin Pineda
 * @version 1.0
 */
public class EliminacionNoPermitidaException extends Exception {

    /**
     * Constructor con el estado actual del contrato que impide la eliminación.
     *
     * @param estadoActual Estado actual del contrato.
     */
    public EliminacionNoPermitidaException(EstadoContrato estadoActual) {
        super("No se puede eliminar el contrato porque se encuentra en estado: " + estadoActual +
                ". Solo se pueden eliminar contratos en estado PUBLICADO.");
    }
}