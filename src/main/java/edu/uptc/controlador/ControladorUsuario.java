package edu.uptc.controlador;

import edu.uptc.dominio.*;
import edu.uptc.excepciones.*;
import edu.uptc.servicios.ServicioUsuario;

import javax.swing.JOptionPane;
import java.util.List;

/**
 * Controlador para la gestión de usuarios del sistema.
 * Recibe las entradas del administrador por JOptionPane,
 * delega la lógica a {@link ServicioUsuario} y propaga
 * las excepciones hacia la vista {@code Application}.
 * Forma parte de la capa de controladores en la arquitectura n-capas.
 *
 * @author Alejandra Cuellar, Laura Gonzalez, Elkin Pineda
 * @version 1.0
 */
public class ControladorUsuario {

    /** Servicio de lógica de negocio para usuarios. */
    private ServicioUsuario servicioUsuario;

    /**
     * Constructor del ControladorUsuario.
     *
     * @param servicioUsuario Instancia del servicio de usuarios.
     */
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    /**
     * Muestra el menú principal del Administrador.
     *
     * @param administrador El administrador autenticado.
     * @throws UsuarioNoEncontradoException Si se intenta operar con un usuario inexistente.
     * @throws UsuarioYaExisteException     Si se intenta registrar un correo duplicado.
     */
    public void mostrarMenuAdministrador(Administrador administrador)
            throws UsuarioNoEncontradoException, UsuarioYaExisteException {
        boolean continuar = true;
        while (continuar) {
            String[] opciones = {"Gestionar Contratantes", "Gestionar Contratistas", "Cerrar Sesión"};
            int op = JOptionPane.showOptionDialog(null,
                    "Bienvenido Administrador: " + administrador.getNombre(),
                    "Menú Administrador",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, opciones, opciones[0]);
            switch (op) {
                case 0: menuContratantes(); break;
                case 1: menuContratistas(); break;
                default: continuar = false;
            }
        }
    }

    /**
     * Muestra el submenú de gestión de contratantes.
     *
     * @throws UsuarioNoEncontradoException Si no se encuentra el contratante buscado/actualizado/eliminado.
     * @throws UsuarioYaExisteException     Si el correo ya está registrado al crear.
     */
    public void menuContratantes()
            throws UsuarioNoEncontradoException, UsuarioYaExisteException {
        String[] opciones = {"Crear", "Consultar", "Actualizar", "Eliminar", "Listar todos", "Volver"};
        int op = JOptionPane.showOptionDialog(null, "Gestión de Contratantes", "Contratantes",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
        switch (op) {
            case 0:
                crearContratante();
                break;
            case 1:
                consultarContratante();
                break;
            case 2:
                actualizarContratante();
                break;
            case 3:
                eliminarContratante();
                break;
            case 4:
                listarContratantes();
                break;
        }
    }

    /**
     * Solicita los datos y crea un nuevo contratante.
     *
     * @throws UsuarioYaExisteException Si el correo ya está registrado.
     */
    public void crearContratante() throws UsuarioYaExisteException {
        String tipoPersona   = pedir("Tipo de persona (natural/jurídica):"); if (tipoPersona == null) return;
        String tipoDoc       = pedir("Tipo de documento (CC/NIT/CE):");      if (tipoDoc == null) return;
        String numDoc        = pedir("Número de documento:");                 if (numDoc == null) return;
        String nombre        = pedir("Nombre completo o razón social:");      if (nombre == null) return;
        String correo        = pedir("Correo electrónico:");                  if (correo == null) return;
        String contrasena    = pedir("Contraseña:");                          if (contrasena == null) return;
        String telefono      = pedir("Teléfono:");                            if (telefono == null) return;
        String direccion     = pedir("Dirección:");                           if (direccion == null) return;
        String ciudad        = pedir("Ciudad:");                              if (ciudad == null) return;
        String sector        = pedir("Sector de la entidad:");                if (sector == null) return;
        String nivelEntidad  = pedir("Nivel de entidad:");                    if (nivelEntidad == null) return;
        String codigoEntidad = pedir("Código único de entidad:");             if (codigoEntidad == null) return;

        Contratante c = new Contratante(tipoPersona, tipoDoc, numDoc, nombre, correo,
                contrasena, telefono, direccion, ciudad, sector, nivelEntidad, codigoEntidad);
        servicioUsuario.registrarContratante(c);
        info("Contratante registrado exitosamente.");
    }

    /**
     * Consulta y muestra la información de un contratante.
     *
     * @throws UsuarioNoEncontradoException Si el contratante no existe.
     */
    public void consultarContratante() throws UsuarioNoEncontradoException {
        String doc = pedir("Número de documento del contratante:");
        if (doc == null) return;
        Contratante c = servicioUsuario.buscarContratante(doc.trim());
        info(c.toString());
    }

    /**
     * Actualiza los datos de un contratante existente.
     *
     * @throws UsuarioNoEncontradoException Si el contratante no existe.
     */
    public void actualizarContratante() throws UsuarioNoEncontradoException {
        String doc = pedir("Documento del contratante a actualizar:");
        if (doc == null) return;
        Contratante c = servicioUsuario.buscarContratante(doc.trim());

        String nombre = pedir("Nuevo nombre (actual: " + c.getNombre() + ") — vacío para conservar:");
        if (nombre != null && !nombre.trim().isEmpty()) c.setNombre(nombre.trim());
        String telefono = pedir("Nuevo teléfono (actual: " + c.getTelefono() + "):");
        if (telefono != null && !telefono.trim().isEmpty()) c.setTelefono(telefono.trim());
        String correo = pedir("Nuevo correo (actual: " + c.getCorreo() + "):");
        if (correo != null && !correo.trim().isEmpty()) c.setCorreo(correo.trim());
        String sector = pedir("Nuevo sector (actual: " + c.getSector() + "):");
        if (sector != null && !sector.trim().isEmpty()) c.setSector(sector.trim());

        servicioUsuario.actualizarContratante(c);
        info("Contratante actualizado exitosamente.");
    }

    /**
     * Elimina un contratante del sistema previo confirmación.
     *
     * @throws UsuarioNoEncontradoException Si el contratante no existe.
     */
    public void eliminarContratante() throws UsuarioNoEncontradoException {
        String doc = pedir("Documento del contratante a eliminar:");
        if (doc == null) return;
        if (confirmar("¿Eliminar contratante con documento " + doc + "?")) {
            servicioUsuario.eliminarContratante(doc.trim());
            info("Contratante eliminado exitosamente.");
        }
    }

    /**
     * Lista todos los contratantes registrados.
     */
    public void listarContratantes() {
        List<Contratante> lista = servicioUsuario.obtenerContratantes();
        if (lista.isEmpty()) { info("No hay contratantes registrados."); return; }
        StringBuilder sb = new StringBuilder("=== Contratantes ===\n\n");
        for (Contratante c : lista) {
            sb.append("• ").append(c.getNombre())
                    .append(" | Doc: ").append(c.getNumeroDocumento())
                    .append(" | Entidad: ").append(c.getCodigoEntidad()).append("\n");
        }
        info(sb.toString());
    }


    /**
     * Muestra el submenú de gestión de contratistas.
     *
     * @throws UsuarioNoEncontradoException Si no se encuentra el contratista.
     * @throws UsuarioYaExisteException     Si el correo ya está registrado al crear.
     */
    public void menuContratistas()
            throws UsuarioNoEncontradoException, UsuarioYaExisteException {
        String[] opciones = {"Crear", "Consultar", "Actualizar", "Eliminar", "Listar todos", "Volver"};
        int op = JOptionPane.showOptionDialog(null, "Gestión de Contratistas", "Contratistas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
        switch (op) {
            case 0:
                crearContratista();
                break;
            case 1:
                consultarContratista();
                break;
            case 2:
                actualizarContratista();
                break;
            case 3:
                eliminarContratista();
                break;
            case 4:
                listarContratistas();
                break;
        }
    }

    /**
     * Solicita los datos y crea un nuevo contratista.
     *
     * @throws UsuarioYaExisteException Si el correo ya está registrado.
     */
    public void crearContratista() throws UsuarioYaExisteException {
        String tipoPersona = pedir("Tipo de persona (natural/jurídica):"); if (tipoPersona == null) return;
        String tipoDoc     = pedir("Tipo de documento (CC/NIT/CE):");      if (tipoDoc == null) return;
        String numDoc      = pedir("Número de documento:");                 if (numDoc == null) return;
        String nombre      = pedir("Nombre completo o razón social:");      if (nombre == null) return;
        String correo      = pedir("Correo electrónico:");                  if (correo == null) return;
        String contrasena  = pedir("Contraseña:");                          if (contrasena == null) return;
        String telefono    = pedir("Teléfono:");                            if (telefono == null) return;
        String direccion   = pedir("Dirección:");                           if (direccion == null) return;
        String ciudad      = pedir("Ciudad:");                              if (ciudad == null) return;

        boolean esPublica = confirmar("¿Es una entidad pública?");
        String area = pedir("Área de desempeño:"); if (area == null) return;

        Contratista c = new Contratista(tipoPersona, tipoDoc, numDoc, nombre, correo,
                contrasena, telefono, direccion, ciudad, esPublica, area);
        servicioUsuario.registrarContratista(c);
        info("Contratista registrado exitosamente.");
    }

    /**
     * Consulta y muestra la información de un contratista.
     *
     * @throws UsuarioNoEncontradoException Si el contratista no existe.
     */
    public void consultarContratista() throws UsuarioNoEncontradoException {
        String doc = pedir("Número de documento del contratista:");
        if (doc == null) return;
        Contratista c = servicioUsuario.buscarContratista(doc.trim());
        info(c.toString());
    }

    /**
     * Actualiza los datos de un contratista existente.
     *
     * @throws UsuarioNoEncontradoException Si el contratista no existe.
     */
    public void actualizarContratista() throws UsuarioNoEncontradoException {
        String doc = pedir("Documento del contratista a actualizar:");
        if (doc == null) return;
        Contratista c = servicioUsuario.buscarContratista(doc.trim());

        String nombre = pedir("Nuevo nombre (actual: " + c.getNombre() + "):");
        if (nombre != null && !nombre.trim().isEmpty()) c.setNombre(nombre.trim());
        String telefono = pedir("Nuevo teléfono (actual: " + c.getTelefono() + "):");
        if (telefono != null && !telefono.trim().isEmpty()) c.setTelefono(telefono.trim());
        String area = pedir("Nueva área de desempeño (actual: " + c.getAreaDesempenio() + "):");
        if (area != null && !area.trim().isEmpty()) c.setAreaDesempenio(area.trim());

        servicioUsuario.actualizarContratista(c);
        info("Contratista actualizado exitosamente.");
    }

    /**
     * Elimina un contratista del sistema previo confirmación.
     *
     * @throws UsuarioNoEncontradoException Si el contratista no existe.
     */
    public void eliminarContratista() throws UsuarioNoEncontradoException {
        String doc = pedir("Documento del contratista a eliminar:");
        if (doc == null) return;
        if (confirmar("¿Eliminar contratista con documento " + doc + "?")) {
            servicioUsuario.eliminarContratista(doc.trim());
            info("Contratista eliminado exitosamente.");
        }
    }

    /**
     * Lista todos los contratistas registrados.
     */
    public void listarContratistas() {
        List<Contratista> lista = servicioUsuario.obtenerContratistas();
        if (lista.isEmpty()) { info("No hay contratistas registrados."); return; }
        StringBuilder sb = new StringBuilder("=== Contratistas ===\n\n");
        for (Contratista c : lista) {
            sb.append("• ").append(c.getNombre())
                    .append(" | Doc: ").append(c.getNumeroDocumento())
                    .append(" | Área: ").append(c.getAreaDesempenio()).append("\n");
        }
        info(sb.toString());
    }


    private String pedir(String msg)  {
        return JOptionPane.showInputDialog(null, msg);
    }
    private void info(String msg)     {
        JOptionPane.showMessageDialog(null, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    private boolean confirmar(String m) {
        return JOptionPane.showConfirmDialog(null, m, "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}