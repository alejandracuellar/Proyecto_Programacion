package edu.uptc.excepciones;

/**
 * Excepción lanzada cuando las credenciales de inicio de sesión
 * (correo o contraseña) no coinciden con ningún usuario registrado.
 *
 * @author Alejandra Cuellar, Laura González, Elkin Pineda
 * @version 1.0
 */
public class CredencialesInvalidasException extends Exception {

    /**
     * Constructor por defecto con mensaje estándar.
     */
    public CredencialesInvalidasException() {
        super("Correo o contraseña incorrectos. Intente nuevamente.");
    }
}
