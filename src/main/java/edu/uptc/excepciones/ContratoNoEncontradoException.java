package edu.uptc.excepciones;

/**
 * Excepción lanzada cuando se intenta operar sobre un contrato
 * que no existe en el sistema (buscar, actualizar, eliminar, cambiar estado).
 *
 * @author Alejandra Cuellar, Laura González, Elkin Pineda
 * @version 1.0
 */
public class ContratoNoEncontradoException extends Exception {

    /**
     * Constructor con el número de contrato que no fue encontrado.
     *
     * @param numeroContrato Número del contrato que no existe.
     */
    public ContratoNoEncontradoException(String numeroContrato) {
        super("No se encontró el contrato con número: " + numeroContrato);
    }
}