package edu.uptc.dominio;

/**
 * Clase que representa al usuario Contratante del sistema.
 * Contiene únicamente los datos del contratante y sus accesores.
 * La lógica de gestión de contratos está en {@code ServicioContrato}.
 * Hereda de {@link Usuario}.
 *
 * @author Alejandra Cuellar, Laura Gonzalez
 * @version 1.0
 */
public class Contratante extends Usuario {

    /** Sector al que pertenece la entidad contratante. */
    private String sector;

    /** Nivel jerárquico de la entidad (nacional, departamental, municipal). */
    private String nivelEntidad;

    /** Código único que identifica a la entidad contratante. */
    private String codigoEntidad;

    /**
     * Constructor completo del Contratante.
     *
     * @param tipoPersona     Tipo de persona.
     * @param tipoDocumento   Tipo de documento.
     * @param numeroDocumento Número de documento.
     * @param nombre          Nombre completo o razón social.
     * @param correo          Correo electrónico.
     * @param contrasenia      Contraseña de acceso.
     * @param telefono        Teléfono de contacto.
     * @param direccion       Dirección de domicilio.
     * @param ciudad          Ciudad de domicilio.
     * @param sector          Sector de la entidad.
     * @param nivelEntidad    Nivel de la entidad.
     * @param codigoEntidad   Código único de la entidad.
     */
    public Contratante(String tipoPersona, String tipoDocumento, String numeroDocumento, String nombre, String correo,
                       String contrasenia, String telefono, String direccion, String ciudad, String sector,
                       String nivelEntidad, String codigoEntidad) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre,
                correo, contrasenia, telefono, direccion, ciudad);
        this.sector        = sector;
        this.nivelEntidad  = nivelEntidad;
        this.codigoEntidad = codigoEntidad;
    }

    /** @return Sector de la entidad. */
    public String getSector() {
        return sector;
    }

    /** @param sector Nuevo sector. */
    public void setSector(String sector) {
        this.sector = sector;
    }

    /** @return Nivel de la entidad. */
    public String getNivelEntidad() {
        return nivelEntidad;
    }

    /** @param nivelEntidad Nuevo nivel de entidad. */
    public void setNivelEntidad(String nivelEntidad) {
        this.nivelEntidad = nivelEntidad;
    }

    /** @return Código de la entidad. */
    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    /** @param codigoEntidad Nuevo código de entidad. */
    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    @Override
    public String toString() {
        return "CONTRATANTE\n" + super.toString() + "\n"+
                "Sector: " + sector + "\n"+
                "Nivel Entidad: " + nivelEntidad + "\n"+
                "Código Entidad: " + codigoEntidad;
    }
}
