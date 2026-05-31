package edu.uptc.servicios;

import edu.uptc.dominio.*;
import edu.uptc.excepciones.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de lógica de negocio para la gestión de usuarios del sistema.
 * Centraliza todas las validaciones y el almacenamiento en memoria.
 * Lanza excepciones específicas en lugar de retornar mensajes de error.
 * Forma parte de la capa de servicios en la arquitectura n-capas.
 *
 * @author Alejandra Cuellar, Laura González, Elkin Pineda
 * @version 1.0
 */
public class ServicioUsuario {

    /** Lista de contratantes registrados en el sistema. */
    private List<Contratante> contratantes;

    /** Lista de contratistas registrados en el sistema. */
    private List<Contratista> contratistas;

    /** Lista de administradores del sistema. */
    private List<Administrador> administradores;

    /**
     * Constructor que inicializa las listas y agrega el administrador por defecto.
     */
    public ServicioUsuario() {
        contratantes   = new ArrayList<>();
        contratistas   = new ArrayList<>();
        administradores = new ArrayList<>();
        administradores.add(new Administrador(
                "natural", "CC", "1000000000", "Admin Sistema",
                "admin@secop.gov.co", "admin123",
                "3000000000", "Calle 1 #1-1", "Bogotá"
        ));
    }

    /**
     * Autentica a un usuario verificando correo y contraseña.
     *
     * @param correo     Correo electrónico del usuario.
     * @param contrasenia Contraseña ingresada.
     * @return El usuario autenticado.
     * @throws CredencialesInvalidasException Si las credenciales no corresponden a ningún usuario.
     */
    public Usuario autenticar(String correo, String contrasenia)throws CredencialesInvalidasException {
        for (Administrador a : administradores) {
            if (a.getCorreo().equals(correo) && a.getContrasenia().equals(contrasenia))
                return a;
        }
        for (Contratante c : contratantes) {
            if (c.getCorreo().equals(correo) && c.getContrasenia().equals(contrasenia))
                return c;
        }
        for (Contratista c : contratistas) {
            if (c.getCorreo().equals(correo) && c.getContrasenia().equals(contrasenia))
                return c;
        }
        throw new CredencialesInvalidasException();
    }

    /**
     * Registra un nuevo contratante en el sistema.
     * Valida campos obligatorios y que el correo no esté duplicado.
     *
     * @param contratante Contratante a registrar.
     * @throws UsuarioYaExisteException Si el correo ya está registrado.
     * @throws IllegalArgumentException Si algún campo obligatorio está vacío.
     */
    public void registrarContratante(Contratante contratante)throws UsuarioYaExisteException {
        validarCamposUsuario(contratante);
        validarCamposContratante(contratante);
        if (correoExiste(contratante.getCorreo())) {
            throw new UsuarioYaExisteException(contratante.getCorreo());
        }
        contratantes.add(contratante);
    }

    /**
     * Retorna todos los contratantes registrados.
     *
     * @return Lista de contratantes (puede estar vacía).
     */
    public List<Contratante> obtenerContratantes() {
        return new ArrayList<>(contratantes);
    }

    /**
     * Busca un contratante por número de documento.
     *
     * @param numeroDocumento Número de documento a buscar.
     * @return El contratante encontrado.
     * @throws UsuarioNoEncontradoException Si no existe un contratante con ese documento.
     */
    public Contratante buscarContratante(String numeroDocumento)throws UsuarioNoEncontradoException {
        for (Contratante c : contratantes) {
            if (c.getNumeroDocumento().equals(numeroDocumento))
                return c;
        }
        throw new UsuarioNoEncontradoException(numeroDocumento);
    }

    /**
     * Actualiza los datos de un contratante existente.
     *
     * @param contratante Contratante con datos actualizados.
     * @throws UsuarioNoEncontradoException Si no existe el contratante a actualizar.
     */
    public void actualizarContratante(Contratante contratante) throws UsuarioNoEncontradoException {
        for (int i = 0; i < contratantes.size(); i++) {
            if (contratantes.get(i).getNumeroDocumento().equals(contratante.getNumeroDocumento())) {
                contratantes.set(i, contratante);
                return;
            }
        }
        throw new UsuarioNoEncontradoException(contratante.getNumeroDocumento());
    }

    /**
     * Elimina un contratante del sistema por número de documento.
     *
     * @param numeroDocumento Número de documento del contratante a eliminar.
     * @throws UsuarioNoEncontradoException Si no existe un contratante con ese documento.
     */
    public void eliminarContratante(String numeroDocumento)throws UsuarioNoEncontradoException {
        buscarContratante(numeroDocumento);
        contratantes.removeIf(c -> c.getNumeroDocumento().equals(numeroDocumento));
    }



    /**
     * Registra un nuevo contratista en el sistema.
     *
     * @param contratista Contratista a registrar.
     * @throws UsuarioYaExisteException Si el correo ya está registrado.
     * @throws IllegalArgumentException Si algún campo obligatorio está vacío.
     */
    public void registrarContratista(Contratista contratista)throws UsuarioYaExisteException {
        validarCamposUsuario(contratista);
        validarCamposContratista(contratista);
        if (correoExiste(contratista.getCorreo())) {
            throw new UsuarioYaExisteException(contratista.getCorreo());
        }
        contratistas.add(contratista);
    }

    /**
     * Retorna todos los contratistas registrados.
     *
     * @return Lista de contratistas (puede estar vacía).
     */
    public List<Contratista> obtenerContratistas() {
        return new ArrayList<>(contratistas);
    }

    /**
     * Busca un contratista por número de documento.
     *
     * @param numeroDocumento Número de documento a buscar.
     * @return El contratista encontrado.
     * @throws UsuarioNoEncontradoException Si no existe un contratista con ese documento.
     */
    public Contratista buscarContratista(String numeroDocumento)throws UsuarioNoEncontradoException {
        for (Contratista c : contratistas) {
            if (c.getNumeroDocumento().equals(numeroDocumento))
                return c;
        }
        throw new UsuarioNoEncontradoException(numeroDocumento);
    }

    /**
     * Actualiza los datos de un contratista existente.
     *
     * @param contratista Contratista con datos actualizados.
     * @throws UsuarioNoEncontradoException Si no existe el contratista a actualizar.
     */
    public void actualizarContratista(Contratista contratista)throws UsuarioNoEncontradoException {
        for (int i = 0; i < contratistas.size(); i++) {
            if (contratistas.get(i).getNumeroDocumento().equals(contratista.getNumeroDocumento())) {
                contratistas.set(i, contratista);
                return;
            }
        }
        throw new UsuarioNoEncontradoException(contratista.getNumeroDocumento());
    }

    /**
     * Elimina un contratista del sistema por número de documento.
     *
     * @param numeroDocumento Número de documento del contratista a eliminar.
     * @throws UsuarioNoEncontradoException Si no existe un contratista con ese documento.
     */
    public void eliminarContratista(String numeroDocumento) throws UsuarioNoEncontradoException {
        buscarContratista(numeroDocumento);
        contratistas.removeIf(c -> c.getNumeroDocumento().equals(numeroDocumento));
    }


    /**
     * Verifica si un correo ya está registrado en cualquier rol.
     *
     * @param correo Correo a verificar.
     * @return true si ya existe.
     */
    private boolean correoExiste(String correo) {
        for (Administrador a : administradores) {
            if (a.getCorreo().equals(correo))
                return true;
        }
        for (Contratante c : contratantes) {
            if (c.getCorreo().equals(correo))
                return true;
        }
        for (Contratista c : contratistas) {
            if (c.getCorreo().equals(correo))
                return true;
        }
        return false;
    }

    /**
     * Valida que todos los campos obligatorios del usuario estén completos.
     *
     * @param u Usuario a validar.
     * @throws IllegalArgumentException Si algún campo obligatorio está vacío.
     */
    private void validarCamposUsuario(Usuario u) {
        if (vacio(u.getTipoPersona()))throw new IllegalArgumentException("El tipo de persona es obligatorio.");
        if (vacio(u.getTipoDocumento()))throw new IllegalArgumentException("El tipo de documento es obligatorio.");
        if (vacio(u.getNumeroDocumento()))throw new IllegalArgumentException("El número de documento es obligatorio.");
        if (vacio(u.getNombre()))throw new IllegalArgumentException("El nombre es obligatorio.");
        if (vacio(u.getCorreo()))throw new IllegalArgumentException("El correo es obligatorio.");
        if (vacio(u.getContrasenia()))throw new IllegalArgumentException("La contraseña es obligatoria.");
        if (vacio(u.getTelefono()))throw new IllegalArgumentException("El teléfono es obligatorio.");
        if (vacio(u.getDireccion()))throw new IllegalArgumentException("La dirección es obligatoria.");
        if (vacio(u.getCiudad()))throw new IllegalArgumentException("La ciudad es obligatoria.");
    }

    /**
     * Valida los campos adicionales del contratante.
     *
     * @param c Contratante a validar.
     * @throws IllegalArgumentException Si algún campo propio del contratante está vacío.
     */
    private void validarCamposContratante(Contratante c) {
        if (vacio(c.getSector()))throw new IllegalArgumentException("El sector es obligatorio.");
        if (vacio(c.getNivelEntidad()))throw new IllegalArgumentException("El nivel de entidad es obligatorio.");
        if (vacio(c.getCodigoEntidad()))throw new IllegalArgumentException("El código de entidad es obligatorio.");
    }

    /**
     * Valida los campos adicionales del contratista.
     *
     * @param c Contratista a validar.
     * @throws IllegalArgumentException Si el área de desempeño está vacía.
     */
    private void validarCamposContratista(Contratista c) {
        if (vacio(c.getAreaDesempenio()))throw new IllegalArgumentException("El área de desempeño es obligatoria.");
    }

    /**
     * Verifica si un String es nulo o vacío.
     *
     * @param s String a verificar.
     * @return true si es nulo o vacío.
     */
    private boolean vacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}