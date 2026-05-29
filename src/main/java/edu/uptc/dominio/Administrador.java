package edu.uptc.dominio;

import javax.swing.*;

/**
 * Clase que representa al usuario Administrador del sistema.
 * Su función exclusiva es crear, actualizar, consultar y eliminar
 * contratantes y contratistas.
 * Hereda de {@link Usuario}.
 *
 * @author Alejandra Cuellar, Laura Gonzalez, Elkin Pineda
 * @version 1.0
 */
public class Administrador extends Usuario {

    /**
     * Constructor completo del Administrador.
     *
     * @param tipoPersona     Tipo de persona.
     * @param tipoDocumento   Tipo de documento.
     * @param numeroDocumento Número de documento.
     * @param nombre          Nombre completo.
     * @param correo          Correo electrónico.
     * @param contrasenia     Contraseña de acceso.
     * @param telefono        Teléfono de contacto.
     * @param direccion       Dirección de domicilio.
     * @param ciudad          Ciudad de domicilio.
     */
    public Administrador(String tipoPersona, String tipoDocumento, String numeroDocumento,  String nombre,
                         String correo, String contrasenia, String telefono, String direccion, String ciudad) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre, correo, contrasenia, telefono, direccion, ciudad);
    }

    @Override
    public void iniciarSesion() {
        JOptionPane.showMessageDialog(null, "Administrador " + getNombre() +
                " ha iniciado sesión.");
    }

    @Override
    public String mostrarInformacion() {
        return "_____Administrador_____ \n" + super.mostrarInformacion();
    }
}
