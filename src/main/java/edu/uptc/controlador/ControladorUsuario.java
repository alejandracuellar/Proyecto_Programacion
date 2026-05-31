package edu.uptc.controlador;

import edu.uptc.dominio.*;
import edu.uptc.excepciones.*;
import edu.uptc.servicios.ServicioUsuario;
import java.util.List;

/**
 * Controlador para la gestión de usuarios.
 * Actúa como intermediario entre la vista (Application) y el ServicioUsuario.
 * Recibe los datos ya capturados, llama al servicio y propaga las excepciones.
 * No contiene JOptionPane ni lógica de negocio.
 *
 * @author Alejandra Cuellar, Laura González, Elkin Pineda
 * @version 1.0
 */
public class ControladorUsuario {

    private ServicioUsuario servicioUsuario;

    /**
     * Constructor del ControladorUsuario.
     *
     * @param servicioUsuario Servicio de lógica de usuarios.
     */
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    /**
     * Autentica un usuario por correo y contraseña.
     *
     * @param correo     Correo electrónico.
     * @param contrasena Contraseña.
     * @return Usuario autenticado.
     * @throws CredencialesInvalidasException Si las credenciales no son válidas.
     */
    public Usuario autenticar(String correo, String contrasena) throws CredencialesInvalidasException {
        return servicioUsuario.autenticar(correo, contrasena);
    }


    /**
     * Registra un nuevo contratante en el sistema.
     *
     * @param contratante Contratante a registrar.
     * @throws UsuarioYaExisteException Si el correo ya está registrado.
     */
    public void registrarContratante(Contratante contratante) throws UsuarioYaExisteException {
        servicioUsuario.registrarContratante(contratante);
    }

    /**
     * Retorna todos los contratantes registrados.
     *
     * @return Lista de contratantes.
     */
    public List<Contratante> obtenerContratantes() {
        return servicioUsuario.obtenerContratantes();
    }

    /**
     * Busca un contratante por número de documento.
     *
     * @param numeroDocumento Documento a buscar.
     * @return Contratante encontrado.
     * @throws UsuarioNoEncontradoException Si no existe.
     */
    public Contratante buscarContratante(String numeroDocumento) throws UsuarioNoEncontradoException {
        return servicioUsuario.buscarContratante(numeroDocumento);
    }

    /**
     * Actualiza los datos de un contratante.
     *
     * @param contratante Contratante con datos nuevos.
     * @throws UsuarioNoEncontradoException Si no existe.
     */
    public void actualizarContratante(Contratante contratante) throws UsuarioNoEncontradoException {
        servicioUsuario.actualizarContratante(contratante);
    }

    /**
     * Elimina un contratante por número de documento.
     *
     * @param numeroDocumento Documento del contratante.
     * @throws UsuarioNoEncontradoException Si no existe.
     */
    public void eliminarContratante(String numeroDocumento) throws UsuarioNoEncontradoException {
        servicioUsuario.eliminarContratante(numeroDocumento);
    }


    /**
     * Registra un nuevo contratista en el sistema.
     *
     * @param contratista Contratista a registrar.
     * @throws UsuarioYaExisteException Si el correo ya está registrado.
     */
    public void registrarContratista(Contratista contratista) throws UsuarioYaExisteException {
        servicioUsuario.registrarContratista(contratista);
    }

    /**
     * Retorna todos los contratistas registrados.
     *
     * @return Lista de contratistas.
     */
    public List<Contratista> obtenerContratistas() {
        return servicioUsuario.obtenerContratistas();
    }

    /**
     * Busca un contratista por número de documento.
     *
     * @param numeroDocumento Documento a buscar.
     * @return Contratista encontrado.
     * @throws UsuarioNoEncontradoException Si no existe.
     */
    public Contratista buscarContratista(String numeroDocumento) throws UsuarioNoEncontradoException {
        return servicioUsuario.buscarContratista(numeroDocumento);
    }

    /**
     * Actualiza los datos de un contratista.
     *
     * @param contratista Contratista con datos nuevos.
     * @throws UsuarioNoEncontradoException Si no existe.
     */
    public void actualizarContratista(Contratista contratista) throws UsuarioNoEncontradoException {
        servicioUsuario.actualizarContratista(contratista);
    }

    /**
     * Elimina un contratista por número de documento.
     *
     * @param numeroDocumento Documento del contratista.
     * @throws UsuarioNoEncontradoException Si no existe.
     */
    public void eliminarContratista(String numeroDocumento) throws UsuarioNoEncontradoException {
        servicioUsuario.eliminarContratista(numeroDocumento);
    }
}
