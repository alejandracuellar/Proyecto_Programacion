package edu.uptc.dominio;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase que representa al usuario Contratante del sistema.
 * El contratante pertenece a una entidad pública y puede crear,
 * consultar, actualizar y eliminar contratos.
 * Hereda de {@link Usuario}.
 *
 * @author Alejandra Cuellar, Laura Gonzalez, Elkin Pineda
 * @version 1.0
 */
public class Contratante extends Usuario {

    /** Sector al que pertenece la entidad. */
    private String sector;

    /** Nivel jerárquico de la entidad (nacional, departamental, municipal). */
    private String nivelEntidad;

    /** Código único que identifica a la entidad contratante. */
    private String codigoEntidad;

    /** Lista de contratos creados por este contratante. */
    private LinkedList<Contrato> contratos;

    /**
     * Constructor completo del Contratante.
     *
     * @param tipoPersona     Tipo de persona.
     * @param tipoDocumento   Tipo de documento.
     * @param numeroDocumento Número de documento.
     * @param nombre          Nombre completo o razón social.
     * @param correo          Correo electrónico.
     * @param contrasenia     Contraseña de acceso.
     * @param telefono        Teléfono de contacto.
     * @param direccion       Dirección de domicilio.
     * @param ciudad          Ciudad de domicilio.
     * @param sector          Sector de la entidad.
     * @param nivelEntidad    Nivel de la entidad.
     * @param codigoEntidad   Código único de la entidad.
     */
    public Contratante(String tipoPersona, String tipoDocumento, String numeroDocumento,  String nombre, String correo,
                       String contrasenia, String telefono, String direccion, String ciudad, String sector,
                       String nivelEntidad, String codigoEntidad) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre, correo, contrasenia, telefono, direccion, ciudad);
        this.sector = sector;
        this.nivelEntidad = nivelEntidad;
        this.codigoEntidad = codigoEntidad;
        this.contratos = new LinkedList<>();
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

    /** @return Lista de contratos del contratante. */
    public LinkedList<Contrato> getContratos() { return contratos; }

    /**
     * Agrega un contrato a la lista del contratante.
     *
     * @param contrato Contrato a agregar.
     */
    public void crearContacto(Contrato contrato){
        for (int i=0; i < contratos.size(); i++){
            if (contratos.get(i).getNumeroContrato().equals(contrato.getNumeroContrato())) {
                contratos.set(i, contrato);
                return;
            }
        }
    }

    /**
     * Elimina un contrato de la lista por número de contrato.
     *
     * @param numeroContrato Número del contrato a eliminar.
     */
    public void eliminarContrato(String numeroContrato) {
        for (int i =0; i < contratos.size(); i++) {
            if (contratos.get(i).getNumeroContrato().equals(numeroContrato)) {
                contratos.remove(i);
                break;
            }
        }
    }

    /**
     * Consulta un contrato por número.
     *
     * @param numeroContrato Número del contrato.
     * @return Contrato encontrado o null.
     */
    public Contrato consultarContrato(String numeroContrato) {
        for (Contrato aux : contratos) {
            if (aux.getNumeroContrato().equals(numeroContrato)) {
                return aux;
            }
        }
        return null;
    }

    @Override
    public void iniciarSesion() {
        JOptionPane.showMessageDialog(null, "Contratante: " + getNombre() +
                "ha iniciado sesión.");
    }

    @Override
    public String mostrarInformacion() {
        return "_____Contratante_____\n" + super.mostrarInformacion() +
                "\nSector: " + sector +
                "\nNivel Entidad: " + nivelEntidad +
                "\nCódigo Entidad: " + codigoEntidad;

    }
}
