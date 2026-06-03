package edu.uptc.dominio;

/**
 * Clase abstracta que representa un usuario del sistema de contratos públicos.
 * Define únicamente los atributos comunes a todos los roles y sus accesores.
 * Aplica encapsulamiento mediante atributos protegidos y métodos de acceso.
 * La lógica de negocio está delegada a la capa de servicios.
 *
 * @author Alejandra Cuellar, Laura Gonzalez
 * @version 1.0
 */
public abstract class Usuario {

    /** Tipo de persona: "natural" o "jurídica". */
    protected String tipoPersona;

    /** Tipo de documento de identidad (CC, NIT, CE, etc.). */
    protected String tipoDocumento;

    /** Número del documento de identidad. */
    protected String numeroDocumento;

    /** Nombre completo del usuario o razón social. */
    protected String nombre;

    /** Correo electrónico del usuario. */
    protected String correo;

    /** Contraseña de acceso al sistema. */
    protected String contrasenia;

    /** Número de teléfono de contacto. */
    protected String telefono;

    /** Dirección de residencia o domicilio. */
    protected String direccion;

    /** Ciudad de residencia o domicilio. */
    protected String ciudad;

    /**
     * Constructor completo de Usuario.
     *
     * @param tipoPersona     Tipo de persona (natural o jurídica).
     * @param tipoDocumento   Tipo de documento de identidad.
     * @param numeroDocumento Número del documento.
     * @param nombre          Nombre completo o razón social.
     * @param correo          Correo electrónico.
     * @param contrasenia      Contraseña de acceso.
     * @param telefono        Número de teléfono.
     * @param direccion       Dirección de domicilio.
     * @param ciudad          Ciudad de domicilio.
     */
    public Usuario(String tipoPersona, String tipoDocumento, String numeroDocumento, String nombre, String correo,
                   String contrasenia, String telefono, String direccion, String ciudad) {
        this.tipoPersona      = tipoPersona;
        this.tipoDocumento    = tipoDocumento;
        this.numeroDocumento  = numeroDocumento;
        this.nombre           = nombre;
        this.correo           = correo;
        this.contrasenia       = contrasenia;
        this.telefono         = telefono;
        this.direccion        = direccion;
        this.ciudad           = ciudad;
    }


    /** @return Tipo de persona. */
    public String getTipoPersona(){
        return tipoPersona;
    }

    /** @param tipoPersona Nuevo tipo de persona. */
    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    /** @return Tipo de documento. */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /** @param tipoDocumento Nuevo tipo de documento. */
    public void setTipoDocumento(String tipoDocumento){
        this.tipoDocumento = tipoDocumento;
    }

    /** @return Número de documento. */
    public String getNumeroDocumento(){
        return numeroDocumento;
    }

    /** @param numeroDocumento Nuevo número de documento. */
    public void setNumeroDocumento(String numeroDocumento){
        this.numeroDocumento = numeroDocumento;
    }

    /** @return Nombre del usuario. */
    public String getNombre(){
        return nombre;
    }

    /** @param nombre Nuevo nombre. */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    /** @return Correo electrónico. */
    public String getCorreo(){
        return correo;
    }

    /** @param correo Nuevo correo. */
    public void setCorreo(String correo){
        this.correo = correo;
    }

    /** @return Contraseña. */
    public String getContrasenia(){
        return contrasenia;
    }

    /** @param contrasenia Nueva contraseña. */
    public void setContrasenia(String contrasenia){
        this.contrasenia = contrasenia; }


    /** @return Teléfono. */
    public String getTelefono(){
        return telefono;
    }

    /** @param telefono Nuevo teléfono. */
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    /** @return Dirección. */
    public String getDireccion(){
        return direccion;
    }

    /** @param direccion Nueva dirección. */
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

    /** @return Ciudad. */
    public String getCiudad() {
        return ciudad;
    }

    /** @param ciudad Nueva ciudad. */
    public void setCiudad(String ciudad){
        this.ciudad = ciudad;
    }

    /**
     * Retorna la información del usuario formateada como texto.
     *
     * @return String con los datos del usuario.
     */

    @Override
    public String toString() {
        return "Documento: " + tipoDocumento + " " + numeroDocumento + "\n"+
                "Nombre: " + nombre + "\n"+
                "Correo: " + correo + "\n"+
                "Teléfono: " + telefono + "\n"+
                "Dirección: " + direccion + ", " + ciudad + "\n"+
                "Tipo de persona: " + tipoPersona;
    }
}