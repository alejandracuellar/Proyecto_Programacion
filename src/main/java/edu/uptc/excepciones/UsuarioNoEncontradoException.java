package edu.uptc.excepciones;

/**
 * Excepción lanzada cuando se intenta operar sobre un usuario
 * (contratante o contratista) que no existe en el sistema.
 *
 * @author Alejandra Cuellar, Laura González, Elkin Pineda
 * @version 1.0
 */
public class UsuarioNoEncontradoException extends Exception {

    /**
     * Constructor con el número de documento no encontrado.
     *
     * @param numeroDocumento Documento del usuario que no existe.
     */
    public UsuarioNoEncontradoException(String numeroDocumento) {
        super("No se encontró ningún usuario con el documento: " + numeroDocumento);
    }
}