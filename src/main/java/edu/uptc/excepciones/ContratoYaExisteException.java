package edu.uptc.excepciones;

/**
 * Excepción lanzada cuando se intenta crear un contrato con un número
 * que ya está registrado en el sistema.
 *
 * @author Alejandra Cuellar, Laura González, Elkin Pineda
 * @version 1.0
 */
public class ContratoYaExisteException extends Exception {

    /**
     * Constructor con el número de contrato duplicado.
     *
     * @param numeroContrato Número del contrato que ya existe.
     */
    public ContratoYaExisteException(String numeroContrato) {
        super("Ya existe un contrato registrado con el número: " + numeroContrato);
    }
}