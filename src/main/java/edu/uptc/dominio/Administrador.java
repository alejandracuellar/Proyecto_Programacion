package edu.uptc.dominio;

 /**
 * Clase que representa al usuario Administrador del sistema.
 * Solo contiene los datos propios del administrador.
 * La lógica de gestión de usuarios está en {@code ServicioUsuario}.
 * Hereda de {@link Usuario}.
 *
 * @author Alejandra Cuellar, Laura Gonzalez
 * @version 1.0
 */
public class Administrador extends Usuario {

        /**
         * Constructor completo del Administrador.
         *
         * @param tipoPersona Tipo de persona.
         * @param tipoDocumento Tipo de documento.
         * @param numeroDocumento Número de documento.
         * @param nombre Nombre completo.
         * @param correo Correo electrónico.
         * @param contrasenia Contraseña de acceso.
         * @param telefono Teléfono de contacto.
         * @param direccion       Dirección de domicilio.
         * @param ciudad          Ciudad de domicilio.
         */
        public Administrador(String tipoPersona, String tipoDocumento, String numeroDocumento, String nombre,
                             String correo, String contrasenia, String telefono, String direccion, String ciudad) {
            super(tipoPersona, tipoDocumento, numeroDocumento, nombre,
                    correo, contrasenia, telefono, direccion, ciudad);
        }

     @Override
     public String toString() {
         return "ADMINISTRADOR\n" + super.toString();
     }
}
