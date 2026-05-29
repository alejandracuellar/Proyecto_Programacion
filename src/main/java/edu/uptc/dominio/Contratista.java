package edu.uptc.dominio;


import javax.swing.*;

/**
 * Clase que representa al usuario Contratista del sistema.
 * El contratista ejecuta los contratos, puede seleccionarlos
 * y cambiar su estado según avance la ejecución.
 * Hereda de {@link Usuario}.
 *
 * @author Alejandra Cuellar, Laura Gonzalez, Elkin Pineda
 * @version 1.0
 */
public class Contratista extends Usuario {


    /** Indica si el contratista es una entidad pública. */
    private boolean entidadPublica;

    /** Área de desempeño o función principal del contratista. */
    private String areaDesempenio;

    /**
     * Constructor completo del Contratista.
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
     * @param entidadPublica  true si es entidad pública.
     * @param areaDesempenio  Área de desempeño.
     */
    public Contratista(String tipoPersona, String tipoDocumento, String numeroDocumento, String nombre, String correo,
                       String contrasenia, String telefono, String direccion, String ciudad, boolean entidadPublica,
                       String areaDesempenio) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre, correo, contrasenia, telefono, direccion, ciudad);
        this.entidadPublica = entidadPublica;
        this.areaDesempenio = areaDesempenio;
    }

    /** @return true si es entidad pública. */
    public boolean isEntidadPublica() {
        return entidadPublica;
    }

    /** @param entidadPublica Nuevo valor para entidad pública. */
    public void setEntidadPublica(boolean entidadPublica) {
        this.entidadPublica = entidadPublica;
    }

    /** @return Área de desempeño. */
    public String getAreaDesempenio() {
        return areaDesempenio;
    }

    /** @param areaDesempenio Nueva área de desempeño. */
    public void setAreaDesempenio(String areaDesempenio) {
        this.areaDesempenio = areaDesempenio;
    }

    /**
     * Selecciona un contrato para ejecutar.
     *
     * @param contrato Contrato a seleccionar.
     */
    public void seleccionarContrato(Contrato contrato) {
       JOptionPane.showMessageDialog(null, "Contratista " + getNombre() + " seleccionó: "
               + contrato.getNumeroContrato());
    }

    /**
     * Cambia el estado de un contrato.
     *
     * @param contrato    Contrato a modificar.
     * @param nuevoEstado Nuevo estado del contrato.
     */
    public void cambiarEstadoContrato(Contrato contrato, edu.uptc.enums.EstadoContrato nuevoEstado) {
        contrato.setEstado(nuevoEstado);
    }

    @Override
    public void iniciarSesion() {
        JOptionPane.showMessageDialog(null, "Contratista " + getNombre()
                + " ha iniciado sesión.");
    }

    @Override
    public String mostrarInformacion() {
        return "_____Contratista_____\n" + super.mostrarInformacion() +
                "\nEntidad Pública: " + (entidadPublica ? "Sí" : "No") +
                "\nÁrea de Desempeño: " + areaDesempenio;
    }
}
