package edu.uptc.excepciones;

/**
 * Excepción lanzada cuando se intenta registrar un usuario con un correo
 * electrónico que ya está registrado en el sistema.
 *
 * @author Alejandra Cuellar, Laura González, Elkin Pineda
 * @version 1.0
 */
public class UsuarioYaExisteException extends Exception {

    /**
     * Constructor con el correo duplicado.
     *
     * @param correo Correo electrónico que ya está registrado.
     */
    public UsuarioYaExisteException(String correo) {
        super("Ya existe un usuario registrado con el correo: " + correo);
    }
}
