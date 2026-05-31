package edu.uptc.servicios;

import edu.uptc.dominio.*;
import edu.uptc.excepciones.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de lógica de negocio para la gestión de usuarios.
 * Solo contiene validaciones, almacenamiento en memoria y lanzamiento de excepciones.
 * No realiza ninguna interacción con el usuario (sin JOptionPane).
 *
 * @author Alejandra Cuellar, Laura González, Elkin Pineda
 * @version 1.0
 */
public class ServicioUsuario {

    private List<Contratante>   contratantes;
    private List<Contratista>   contratistas;
    private List<Administrador> administradores;

    /**
     * Constructor: inicializa listas y crea el administrador por defecto del sistema.
     */
    public ServicioUsuario() {
        contratantes    = new ArrayList<>();
        contratistas    = new ArrayList<>();
        administradores = new ArrayList<>();
        administradores.add(new Administrador("natural", "CC", "1000000000",
                "Admin Sistema","admin@secop.gov.co", "admin123", "3000000000",
                "Calle 1 #1-1", "Bogotá"
        ));
    }


    /**
     * Autentica un usuario verificando correo y contraseña en todos los roles.
     *
     * @param correo Correo electrónico.
     * @param contrasena Contraseña.
     * @return Usuario autenticado.
     * @throws CredencialesInvalidasException Si las credenciales no coinciden con ningún usuario.
     */
    public Usuario autenticar(String correo, String contrasena)
            throws CredencialesInvalidasException {
        for (Administrador a : administradores)
            if (a.getCorreo().equals(correo) && a.getContrasenia().equals(contrasena)) return a;
        for (Contratante c : contratantes)
            if (c.getCorreo().equals(correo) && c.getContrasenia().equals(contrasena)) return c;
        for (Contratista c : contratistas)
            if (c.getCorreo().equals(correo) && c.getContrasenia().equals(contrasena)) return c;
        throw new CredencialesInvalidasException();
    }


    /**
     * Registra un nuevo contratante validando campos y unicidad de correo.
     *
     * @param contratante Contratante a registrar.
     * @throws UsuarioYaExisteException Si el correo ya está registrado.
     * @throws IllegalArgumentException Si algún campo obligatorio está vacío.
     */
    public void registrarContratante(Contratante contratante) throws UsuarioYaExisteException {
        validarCamposUsuario(contratante);
        if (vacio(contratante.getSector())) throw new IllegalArgumentException("El sector es obligatorio.");
        if (vacio(contratante.getNivelEntidad())) throw new IllegalArgumentException("El nivel de entidad es obligatorio.");
        if (vacio(contratante.getCodigoEntidad())) throw new IllegalArgumentException("El código de entidad es obligatorio.");
        if (correoExiste(contratante.getCorreo())) throw new UsuarioYaExisteException(contratante.getCorreo());
        contratantes.add(contratante);
    }

    /** @return Lista de todos los contratantes registrados. */
    public List<Contratante> obtenerContratantes() {
        return new ArrayList<>(contratantes);
    }

    /**
     * Busca un contratante por número de documento.
     *
     * @param numeroDocumento Documento a buscar.
     * @return Contratante encontrado.
     * @throws UsuarioNoEncontradoException Si no existe ningún contratante con ese documento.
     */
    public Contratante buscarContratante(String numeroDocumento)
            throws UsuarioNoEncontradoException {
        for (Contratante c : contratantes)
            if (c.getNumeroDocumento().equals(numeroDocumento)) return c;
        throw new UsuarioNoEncontradoException(numeroDocumento);
    }

    /**
     * Actualiza los datos de un contratante existente.
     *
     * @param contratante Contratante con datos actualizados.
     * @throws UsuarioNoEncontradoException Si no existe el contratante.
     */
    public void actualizarContratante(Contratante contratante)
            throws UsuarioNoEncontradoException {
        for (int i = 0; i < contratantes.size(); i++) {
            if (contratantes.get(i).getNumeroDocumento().equals(contratante.getNumeroDocumento())) {
                contratantes.set(i, contratante);
                return;
            }
        }
        throw new UsuarioNoEncontradoException(contratante.getNumeroDocumento());
    }

    /**
     * Elimina un contratante por número de documento.
     *
     * @param numeroDocumento Documento del contratante a eliminar.
     * @throws UsuarioNoEncontradoException Si no existe el contratante.
     */
    public void eliminarContratante(String numeroDocumento)
            throws UsuarioNoEncontradoException {
        buscarContratante(numeroDocumento);
        contratantes.removeIf(c -> c.getNumeroDocumento().equals(numeroDocumento));
    }


    /**
     * Registra un nuevo contratista validando campos y unicidad de correo.
     *
     * @param contratista Contratista a registrar.
     * @throws UsuarioYaExisteException Si el correo ya está registrado.
     * @throws IllegalArgumentException Si algún campo obligatorio está vacío.
     */
    public void registrarContratista(Contratista contratista) throws UsuarioYaExisteException {
        validarCamposUsuario(contratista);
        if (vacio(contratista.getAreaDesempenio()))
            throw new IllegalArgumentException("El área de desempeño es obligatoria.");
        if (correoExiste(contratista.getCorreo())) throw new UsuarioYaExisteException(contratista.getCorreo());
        contratistas.add(contratista);
    }

    /** @return Lista de todos los contratistas registrados. */
    public List<Contratista> obtenerContratistas() {
        return new ArrayList<>(contratistas);
    }

    /**
     * Busca un contratista por número de documento.
     *
     * @param numeroDocumento Documento a buscar.
     * @return Contratista encontrado.
     * @throws UsuarioNoEncontradoException Si no existe ningún contratista con ese documento.
     */
    public Contratista buscarContratista(String numeroDocumento)
            throws UsuarioNoEncontradoException {
        for (Contratista c : contratistas)
            if (c.getNumeroDocumento().equals(numeroDocumento))
                return c;
        throw new UsuarioNoEncontradoException(numeroDocumento);
    }

    /**
     * Actualiza los datos de un contratista existente.
     *
     * @param contratista Contratista con datos actualizados.
     * @throws UsuarioNoEncontradoException Si no existe el contratista.
     */
    public void actualizarContratista(Contratista contratista) throws UsuarioNoEncontradoException {
        for (int i = 0; i < contratistas.size(); i++) {
            if (contratistas.get(i).getNumeroDocumento().equals(contratista.getNumeroDocumento())) {
                contratistas.set(i, contratista);
                return;
            }
        }
        throw new UsuarioNoEncontradoException(contratista.getNumeroDocumento());
    }

    /**
     * Elimina un contratista por número de documento.
     *
     * @param numeroDocumento Documento del contratista a eliminar.
     * @throws UsuarioNoEncontradoException Si no existe el contratista.
     */
    public void eliminarContratista(String numeroDocumento) throws UsuarioNoEncontradoException {
        buscarContratista(numeroDocumento);
        contratistas.removeIf(c -> c.getNumeroDocumento().equals(numeroDocumento));
    }


    private boolean correoExiste(String correo) {
        for (Administrador a : administradores) if (a.getCorreo().equals(correo)) return true;
        for (Contratante c: contratantes) if (c.getCorreo().equals(correo)) return true;
        for (Contratista c: contratistas)if (c.getCorreo().equals(correo)) return true;
        return false;
    }

    private void validarCamposUsuario(Usuario u) {
        if (vacio(u.getTipoPersona())) throw new IllegalArgumentException("Tipo de persona obligatorio.");
        if (vacio(u.getTipoDocumento())) throw new IllegalArgumentException("Tipo de documento obligatorio.");
        if (vacio(u.getNumeroDocumento())) throw new IllegalArgumentException("Número de documento obligatorio.");
        if (vacio(u.getNombre())) throw new IllegalArgumentException("Nombre obligatorio.");
        if (vacio(u.getCorreo())) throw new IllegalArgumentException("Correo obligatorio.");
        if (vacio(u.getContrasenia())) throw new IllegalArgumentException("Contraseña obligatoria.");
        if (vacio(u.getTelefono())) throw new IllegalArgumentException("Teléfono obligatorio.");
        if (vacio(u.getDireccion())) throw new IllegalArgumentException("Dirección obligatoria.");
        if (vacio(u.getCiudad())) throw new IllegalArgumentException("Ciudad obligatoria.");
    }

    private boolean vacio(String s) { return s == null || s.trim().isEmpty(); }
}