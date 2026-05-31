package edu.uptc.excepciones;

/**
 * Excepción lanzada cuando un contrato no supera las reglas de validación
 * propias de su tipo (valor incorrecto, área vacía, honorario incorrecto, etc.).
 *
 * @author Alejandra Cuellar, Laura González, Elkin Pineda
 * @version 1.0
 */
public class ContratoInvalidoException extends Exception {

    /**
     * Constructor con el mensaje de validación específico.
     *
     * @param mensaje Descripción del error de validación.
     */
    public ContratoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
