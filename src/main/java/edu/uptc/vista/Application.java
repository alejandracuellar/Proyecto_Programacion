package edu.uptc.vista;

import edu.uptc.controlador.*;
import edu.uptc.dominio.*;
import edu.uptc.enums.EstadoContrato;
import edu.uptc.excepciones.*;
import edu.uptc.servicios.*;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Clase principal y única vista del sistema.
 * Contiene TODA la interacción con el usuario mediante JOptionPane
 * y TODOS los bloques try/catch del sistema.
 * Delega la lógica a los controladores.
 *
 * <p>Arquitectura n-capas:</p>
 * <ul>
 *   <li><b>Vista:</b>        Application (JOptionPane + try/catch)</li>
 *   <li><b>Controlador:</b>  ControladorUsuario, ControladorContrato, ControladorReporte</li>
 *   <li><b>Servicio:</b>     ServicioUsuario, ServicioContrato, ServicioReporte</li>
 *   <li><b>Dominio:</b>      Usuario, Contrato y subclases, ReporteInterventoria</li>
 *   <li><b>Enums:</b>        EstadoContrato</li>
 *   <li><b>Excepciones:</b>  Paquete excepciones</li>
 * </ul>
 *
 * @author Alejandra Cuellar, Laura Gonzalez
 * @version 1.0
 */
public class Application {

    /** Formato de fecha para la entrada del usuario. */
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static ControladorUsuario  ctrlUsuario;
    private static ControladorContrato ctrlContrato;
    private static ControladorReporte  ctrlReporte;

    /**
     * Punto de entrada de la aplicación.
     *
     * @param args Argumentos de línea de comandos (no usados).
     */
    public static void main(String[] args) {

        ServicioReporte svcReporte = new ServicioReporte();
        ServicioUsuario svcUsuario = new ServicioUsuario();
        ServicioContrato svcContrato = new ServicioContrato(svcReporte);

        ctrlUsuario = new ControladorUsuario(svcUsuario);
        ctrlContrato = new ControladorContrato(svcContrato);
        ctrlReporte = new ControladorReporte(svcReporte);


        cargarDemostracion(svcUsuario);

        boolean ejecutando = true;
        while (ejecutando) {
            String[] opciones = {"Iniciar Sesión", "Salir"};
            int op = JOptionPane.showOptionDialog(null,
                    "Sistema de Contratos Públicos — SECOP II\nUPTC", "Bienvenido",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            if (op != 0) {
                JOptionPane.showMessageDialog(null, "Cerrando el sistema...");
                break;
            }

            String correo= JOptionPane.showInputDialog(null, "Correo electrónico:");
            if (correo == null) continue;
            String contrasena = JOptionPane.showInputDialog(null, "Contraseña:");
            if (contrasena == null) continue;

            try {
                Usuario usuario = ctrlUsuario.autenticar(correo.trim(), contrasena);

                if (usuario instanceof Administrador)
                    menuAdministrador((Administrador) usuario);
                else if (usuario instanceof Contratante)
                    menuContratante((Contratante) usuario);
                else if (usuario instanceof Contratista)
                    menuContratista((Contratista) usuario);

            } catch (CredencialesInvalidasException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            }
        }
        System.exit(0);
    }

    private static void menuAdministrador(Administrador admin) {
        boolean continuar = true;
        while (continuar) {
            String[] opciones = {"Gestionar Contratantes", "Gestionar Contratistas", "Cerrar Sesión"};
            int op = JOptionPane.showOptionDialog(null, "Administrador: " +
                            admin.getNombre(), "Menú Administrador", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
            switch (op) {
                case 0:
                    menuGestionContratantes();
                    break;

                case 1:
                    menuGestionContratistas();
                    break;

                default:
                    continuar = false;
            }
        }
    }


    private static void menuGestionContratantes() {
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

    private static void crearContratante() {
        try {
            String tipoPersona = pedir("Tipo de persona (natural/jurídica):"); if (tipoPersona == null) return;
            String tipoDoc = pedir("Tipo de documento (CC/NIT/CE):"); if (tipoDoc == null) return;
            String numDoc = pedir("Número de documento:"); if (numDoc == null) return;
            String nombre = pedir("Nombre completo o razón social:"); if (nombre == null) return;
            String correo = pedir("Correo electrónico:"); if (correo == null) return;
            String contrasena= pedir("Contraseña:"); if (contrasena == null) return;
            String telefono= pedir("Teléfono:"); if (telefono == null) return;
            String direccion= pedir("Dirección:"); if (direccion == null) return;
            String ciudad = pedir("Ciudad:"); if (ciudad == null) return;
            String sector = pedir("Sector de la entidad:"); if (sector == null) return;
            String nivelEntidad  = pedir("Nivel de entidad:");if (nivelEntidad == null) return;
            String codigoEntidad = pedir("Código único de entidad:"); if (codigoEntidad == null) return;

            Contratante c = new Contratante(tipoPersona, tipoDoc, numDoc, nombre, correo, contrasena, telefono,
                    direccion, ciudad, sector, nivelEntidad, codigoEntidad);
            ctrlUsuario.registrarContratante(c);
            exito("Contratante registrado.");

        } catch (UsuarioYaExisteException | IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    private static void consultarContratante() {
        String doc = pedir("Documento del contratante:");
        if (doc == null) return;
        try {
            Contratante c = ctrlUsuario.buscarContratante(doc.trim());
            info(c.toString());
        } catch (UsuarioNoEncontradoException e) {
            error(e.getMessage());
        }
    }

    private static void actualizarContratante() {
        String doc = pedir("Documento del contratante a actualizar:");
        if (doc == null)
            return;
        try {
            Contratante c = ctrlUsuario.buscarContratante(doc.trim());
            String nombre = pedir("Nuevo nombre (actual: " + c.getNombre() + ") — vacío para conservar:");
            if (nombre != null && !nombre.trim().isEmpty()) c.setNombre(nombre.trim());
            String telefono = pedir("Nuevo teléfono (actual: " + c.getTelefono() + "):");
            if (telefono != null && !telefono.trim().isEmpty()) c.setTelefono(telefono.trim());
            String sector = pedir("Nuevo sector (actual: " + c.getSector() + "):");
            if (sector != null && !sector.trim().isEmpty()) c.setSector(sector.trim());
            ctrlUsuario.actualizarContratante(c);
            exito("Contratante actualizado.");
        } catch (UsuarioNoEncontradoException e) {
            error(e.getMessage());
        }
    }

    private static void eliminarContratante() {
        String doc = pedir("Documento del contratante a eliminar:");
        if (doc == null) return;
        try {
            if (!confirmar("¿Eliminar contratante con documento " + doc + "?")) return;
            ctrlUsuario.eliminarContratante(doc.trim());
            exito("Contratante eliminado.");
        } catch (UsuarioNoEncontradoException e) {
            error(e.getMessage());
        }
    }

    private static void listarContratantes() {
        List<Contratante> lista = ctrlUsuario.obtenerContratantes();
        if (lista.isEmpty()) { info("No hay contratantes registrados."); return; }
        StringBuilder sb = new StringBuilder("CONTRATANTES\n\n");
        for (Contratante c : lista)
            sb.append("• ").append(c.getNombre())
                    .append(" | ").append(c.getNumeroDocumento())
                    .append(" | ").append(c.getCodigoEntidad()).append("\n");
        info(sb.toString());
    }


    private static void menuGestionContratistas() {
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

    private static void crearContratista() {
        try {
            String tipoPersona = pedir("Tipo de persona (natural/jurídica):"); if (tipoPersona == null) return;
            String tipoDoc= pedir("Tipo de documento (CC/NIT/CE):");if (tipoDoc == null) return;
            String numDoc = pedir("Número de documento:"); if (numDoc == null) return;
            String nombre = pedir("Nombre completo o razón social:"); if (nombre == null) return;
            String correo = pedir("Correo electrónico:"); if (correo == null) return;
            String contrasena = pedir("Contraseña:"); if (contrasena == null) return;
            String telefono = pedir("Teléfono:"); if (telefono == null) return;
            String direccion = pedir("Dirección:"); if (direccion == null) return;
            String ciudad  = pedir("Ciudad:"); if (ciudad == null) return;
            boolean esPublica = confirmar("¿Es una entidad pública?");
            String area  = pedir("Área de desempeño:"); if (area == null) return;

            Contratista c = new Contratista(tipoPersona, tipoDoc, numDoc, nombre, correo,
                    contrasena, telefono, direccion, ciudad, esPublica, area);
            ctrlUsuario.registrarContratista(c);
            exito("Contratista registrado.");

        } catch (UsuarioYaExisteException | IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    private static void consultarContratista() {
        String doc = pedir("Documento del contratista:");
        if (doc == null) return;
        try {
            Contratista c = ctrlUsuario.buscarContratista(doc.trim());
            info(c.toString());
        } catch (UsuarioNoEncontradoException e) {
            error(e.getMessage());
        }
    }

    private static void actualizarContratista() {
        String doc = pedir("Documento del contratista a actualizar:");
        if (doc == null) return;
        try {
            Contratista c = ctrlUsuario.buscarContratista(doc.trim());
            String nombre = pedir("Nuevo nombre (actual: " + c.getNombre() + "):");
            if (nombre != null && !nombre.trim().isEmpty()) c.setNombre(nombre.trim());
            String telefono = pedir("Nuevo teléfono (actual: " + c.getTelefono() + "):");
            if (telefono != null && !telefono.trim().isEmpty()) c.setTelefono(telefono.trim());
            String area = pedir("Nueva área (actual: " + c.getAreaDesempenio() + "):");
            if (area != null && !area.trim().isEmpty()) c.setAreaDesempenio(area.trim());
            ctrlUsuario.actualizarContratista(c);
            exito("Contratista actualizado.");
        } catch (UsuarioNoEncontradoException e) {
            error(e.getMessage());
        }
    }

    private static void eliminarContratista() {
        String doc = pedir("Documento del contratista a eliminar:");
        if (doc == null) return;
        try {
            if (!confirmar("¿Eliminar contratista con documento " + doc + "?")) return;
            ctrlUsuario.eliminarContratista(doc.trim());
            exito("Contratista eliminado.");
        } catch (UsuarioNoEncontradoException e) {
            error(e.getMessage());
        }
    }

    private static void listarContratistas() {
        List<Contratista> lista = ctrlUsuario.obtenerContratistas();
        if (lista.isEmpty()) { info("No hay contratistas registrados."); return; }
        StringBuilder sb = new StringBuilder("CONTRATISTAS\n\n");
        for (Contratista c : lista)
            sb.append("• ").append(c.getNombre()).append(" | ").append(c.getNumeroDocumento())
                    .append(" | ").append(c.getAreaDesempenio()).append("\n");
        info(sb.toString());
    }

    private static void menuContratante(Contratante contratante) {
        boolean continuar = true;
        while (continuar) {
            String[] opciones = {
                    "Crear Contrato", "Consultar Contrato", "Actualizar Contrato",
                    "Eliminar Contrato", "Mis Contratos", "Ver Reportes", "Cerrar Sesión"
            };
            int op = JOptionPane.showOptionDialog(null,
                    "Contratante: " + contratante.getNombre() +
                            "\nEntidad: "   + contratante.getCodigoEntidad(),
                    "Menú Contratante",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, opciones, opciones[0]);
            switch (op) {
                case 0:
                    menuCrearContrato(contratante);
                    break;
                case 1:
                    consultarContrato();
                    break;
                case 2:
                    actualizarContrato();
                    break;
                case 3:
                    eliminarContrato();
                    break;
                case 4:
                    listarMisContratos(contratante);
                    break;
                case 5:
                    menuReportes();
                    break;
                default: continuar = false;
            }
        }
    }


    private static void menuCrearContrato(Contratante contratante) {
        String[] tipos = {"Prestación de Servicios", "Compraventa", "Obra Pública", "Cancelar"};
        int tipo = JOptionPane.showOptionDialog(null, "Tipo de contrato:", "Crear Contrato",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
        switch (tipo) {
            case 0:
                crearContratoPrestacion(contratante);
                break;
            case 1:
                crearContratoCompraVenta(contratante);
                break;
            case 2:
                crearContratoObraPublica(contratante);
                break;
        }
    }

    private static void crearContratoPrestacion(Contratante contratante) {
        try {
            String[] base= solicitarDatosBase(); if (base == null) return;
            String perfil= pedir("Perfil requerido:"); if (perfil == null) return;
            String entregas  = pedir("Entregables:"); if (entregas == null) return;
            String honorario = pedir("Honorario mensual ($):"); if (honorario == null) return;

            Contratista contratista = obtenerContratistaSiHayDoc(base[5]);
            ContratoPrestacionServicio c = new ContratoPrestacionServicio(
                    base[0], base[1],LocalDate.parse(base[2].trim(), FMT), contratante, contratista,
                    Double.parseDouble(base[4].trim()), LocalDate.parse(base[3].trim(), FMT),
                    perfil, entregas, Double.parseDouble(honorario.trim()));
            ctrlContrato.crearContrato(c);
            exito("Contrato de Prestación de Servicios creado.");

        } catch (ContratoYaExisteException | ContratoInvalidoException |
                 UsuarioNoEncontradoException | IllegalArgumentException e) {
            error(e.getMessage());
        } catch (DateTimeParseException e) {
            error("Formato de fecha inválido. Use dd/MM/yyyy.");
        }
    }

    private static void crearContratoCompraVenta(Contratante contratante) {
        try {
            String[] base  = solicitarDatosBase(); if (base == null) return;
            String item = pedir("Ítem o bien a adquirir:"); if (item == null) return;
            String marca= pedir("Marca:"); if (marca == null) return;
            String modelo = pedir("Modelo:");  if (modelo == null) return;
            String serie = pedir("Serie:"); if (serie == null) return;
            String valUnit = pedir("Valor unitario ($):"); if (valUnit == null) return;
            String cant = pedir("Cantidad a adquirir:"); if (cant == null) return;

            Contratista contratista = obtenerContratistaSiHayDoc(base[5]);
            ContratoCompraVenta c = new ContratoCompraVenta(
                    base[0], base[1],
                    LocalDate.parse(base[2].trim(), FMT), contratante, contratista,
                    Double.parseDouble(base[4].trim()),
                    LocalDate.parse(base[3].trim(), FMT),
                    item, marca, modelo, serie,
                    Double.parseDouble(valUnit.trim()), Integer.parseInt(cant.trim()));
            ctrlContrato.crearContrato(c);
            exito("Contrato de Compraventa creado.");

        } catch (ContratoYaExisteException | ContratoInvalidoException |
                 UsuarioNoEncontradoException | IllegalArgumentException e) {
            error(e.getMessage());
        } catch (DateTimeParseException e) {
            error("Formato de fecha inválido. Use dd/MM/yyyy.");
        }
    }

    private static void crearContratoObraPublica(Contratante contratante) {
        try {
            String[] base = solicitarDatosBase(); if (base == null) return;
            String ubicacion = pedir("Ubicación de la obra (dirección urbana o rural):"); if (ubicacion == null) return;
            String area = pedir("Área de intervención (m²):");if (area == null) return;

            Contratista contratista = obtenerContratistaSiHayDoc(base[5]);
            ContratoObraPublica c = new ContratoObraPublica(
                    base[0], base[1],
                    LocalDate.parse(base[2].trim(), FMT), contratante, contratista,
                    Double.parseDouble(base[4].trim()),
                    LocalDate.parse(base[3].trim(), FMT),
                    ubicacion, Double.parseDouble(area.trim()));
            ctrlContrato.crearContrato(c);
            exito("Contrato de Obra Pública creado.");

        } catch (ContratoYaExisteException | ContratoInvalidoException |
                 UsuarioNoEncontradoException | IllegalArgumentException e) {
            error(e.getMessage());
        } catch (DateTimeParseException e) {
            error("Formato de fecha inválido. Use dd/MM/yyyy.");
        }
    }


    private static void consultarContrato() {
        String num = pedir("Número del contrato:");
        if (num == null) return;
        try {
            Contrato c = ctrlContrato.buscarContrato(num.trim());
            info(c.toString());
        } catch (ContratoNoEncontradoException e) {
            error(e.getMessage());
        }
    }

    private static void actualizarContrato() {
        String num = pedir("Número del contrato a actualizar:");
        if (num == null) return;
        try {
            Contrato c = ctrlContrato.buscarContrato(num.trim());
            String nuevo = pedir("Nuevo objeto (actual: " + c.getObjetoContrato() + "):");
            if (nuevo != null && !nuevo.trim().isEmpty()) c.setObjetoContrato(nuevo.trim());
            ctrlContrato.actualizarContrato(c);
            exito("Contrato actualizado.");
        } catch (ContratoNoEncontradoException | ActualizacionNoPermitidaException |
                 ContratoInvalidoException e) {
            error(e.getMessage());
        }
    }

    private static void eliminarContrato() {
        String num = pedir("Número del contrato a eliminar:");
        if (num == null) return;
        try {
            if (!confirmar("¿Eliminar el contrato N° " + num + "?")) return;
            ctrlContrato.eliminarContrato(num.trim());
            exito("Contrato eliminado.");
        } catch (ContratoNoEncontradoException | EliminacionNoPermitidaException e) {
            error(e.getMessage());
        }
    }

    private static void listarMisContratos(Contratante contratante) {
        List<Contrato> lista = ctrlContrato.obtenerContratosPorContratante(contratante.getNumeroDocumento());
        if (lista.isEmpty()) { info("No tiene contratos registrados."); return; }
        StringBuilder sb = new StringBuilder("MIS CONTRATOS\n\n");
        for (Contrato c : lista) sb.append("• ").append(c).append("\n");
        info(sb.toString());
    }


    private static void menuContratista(Contratista contratista) {
        boolean continuar = true;
        while (continuar) {
            String[] opciones = {
                    "Ver Contratos Disponibles", "Seleccionar Contrato",
                    "Cambiar Estado de Contrato", "Ver Reportes", "Cerrar Sesión"
            };
            int op = JOptionPane.showOptionDialog(null,
                    "Contratista: " + contratista.getNombre() +
                            "\nÁrea: "      + contratista.getAreaDesempenio(),
                    "Menú Contratista",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, opciones, opciones[0]);
            switch (op) {
                case 0:
                    verContratosDisponibles();
                    break;
                case 1:
                    seleccionarContrato(contratista);
                    break;
                case 2:
                    cambiarEstadoContrato();
                    break;
                case 3:
                    menuReportes();
                    break;
                default:
                    continuar = false;
            }
        }
    }

    private static void verContratosDisponibles() {
        List<Contrato> lista = ctrlContrato.obtenerContratosDisponibles();
        if (lista.isEmpty()) { info("No hay contratos disponibles."); return; }
        StringBuilder sb = new StringBuilder("CONTRATOS DISPONIBLES\n\n");
        for (Contrato c : lista) sb.append("• ").append(c).append("\n");
        info(sb.toString());
    }

    private static void seleccionarContrato(Contratista contratista) {
        String num = pedir("Número del contrato a seleccionar:");
        if (num == null) return;
        try {
            ctrlContrato.seleccionarContrato(num.trim(), contratista);
            exito("Contrato seleccionado.");
        } catch (ContratoNoEncontradoException | ContratoInvalidoException e) {
            error(e.getMessage());
        }
    }

    private static void cambiarEstadoContrato() {
        String num = pedir("Número del contrato:");
        if (num == null) return;
        try {
            Contrato c = ctrlContrato.buscarContrato(num.trim());
            info("Estado actual: " + c.getEstado());

            EstadoContrato[] estados = EstadoContrato.values();
            String[] nombres = new String[estados.length];
            for (int i = 0; i < estados.length; i++) nombres[i] = estados[i].name();

            String sel = (String) JOptionPane.showInputDialog(null,
                    "Seleccione el nuevo estado:", "Cambiar Estado",
                    JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);
            if (sel == null) return;

            String informe = pedir("Motivo del cambio de estado:");
            if (informe == null) return;

            ctrlContrato.cambiarEstadoContrato(num.trim(), EstadoContrato.valueOf(sel), informe.trim());
            exito("Estado cambiado a " + sel + ". Reporte de interventoría generado.");

        } catch (ContratoNoEncontradoException | TransicionEstadoInvalidaException |
                 IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    private static void menuReportes() {
        String[] opciones = {"Ver todos los reportes", "Ver reportes de un contrato", "Volver"};
        int op = JOptionPane.showOptionDialog(null, "Reportes de Interventoría", "Reportes",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
        switch (op) {
            case 0:
                verTodosLosReportes();
                break;
            case 1:
                verReportesPorContrato();
                break;
        }
    }

    private static void verTodosLosReportes() {
        List<ReporteInterventoria> lista = ctrlReporte.obtenerTodosLosReportes();
        if (lista.isEmpty()) { info("No hay reportes registrados."); return; }
        StringBuilder sb = new StringBuilder("TODOS LOS REPORTES\n\n");
        for (ReporteInterventoria r : lista)
            sb.append(r).append("\n────────────────────────\n");
        info(sb.toString());
    }

    private static void verReportesPorContrato() {
        String num = pedir("Número del contrato:");
        if (num == null) return;
        List<ReporteInterventoria> lista = ctrlReporte.obtenerReportesPorContrato(num.trim());
        if (lista.isEmpty()) { info("No hay reportes para el contrato " + num + "."); return; }
        StringBuilder sb = new StringBuilder("REPORTES DEL CONTRATO" + num + "\n\n");
        for (ReporteInterventoria r : lista)
            sb.append(r).append("\n────────────────────────\n");
        info(sb.toString());
    }


    /**
     * Solicita los campos base comunes a todos los tipos de contrato.
     *
     * @return Arreglo [num, objeto, fechaCreacion, plazoEjecucion, valor, docContratista]
     *         o null si el usuario cancela.
     */
    private static String[] solicitarDatosBase() {
        String num = pedir("Número de contrato:"); if (num == null) return null;
        String objeto = pedir("Objeto del contrato:"); if (objeto == null) return null;
        String fCrea = pedir("Fecha creación (dd/MM/yyyy):"); if (fCrea == null) return null;
        String fPlazo = pedir("Plazo ejecución (dd/MM/yyyy):"); if (fPlazo == null) return null;
        String valor = pedir("Valor total del contrato ($):"); if (valor == null) return null;
        String doc  = pedir("Documento del contratista (vacío si no aplica):");
        if (doc == null) doc = "";
        return new String[]{num, objeto, fCrea, fPlazo, valor, doc};
    }

    /**
     * Busca un contratista si se proporcionó documento; retorna null si está vacío.
     *
     * @param doc Número de documento (puede ser vacío).
     * @return Contratista encontrado o null.
     * @throws UsuarioNoEncontradoException Si se ingresó documento pero no existe.
     */
    private static Contratista obtenerContratistaSiHayDoc(String doc)
            throws UsuarioNoEncontradoException {
        if (doc == null || doc.trim().isEmpty()) return null;
        return ctrlUsuario.buscarContratista(doc.trim());
    }

    /**
     * Carga datos de demostración para facilitar las pruebas.
     *
     * @param svcUsuario Servicio de usuarios.
     */
    private static void cargarDemostracion(ServicioUsuario svcUsuario) {
        try {
            svcUsuario.registrarContratante(new Contratante(
                    "jurídica", "NIT", "900123456", "Alcaldía de Duitama",
                    "contratante@duitama.gov.co", "contrato123",
                    "3101234567", "Calle 14 #10-22", "Duitama",
                    "Gobierno", "municipal", "ALC-DUI-001"
            ));
            svcUsuario.registrarContratista(new Contratista(
                    "natural", "CC", "1098765432", "Daniel Vargas" +
                    "",
                    "contratista@gmail.com", "contratista123",
                    "3009876543", "Carrera 18 #25-10", "Duitama",
                    false, "Ingeniería de Sistemas"
            ));
        } catch (UsuarioYaExisteException e) {
        }
    }


    private static String pedir(String msg){ return JOptionPane.showInputDialog(null, msg); }
    private static void info(String msg){ JOptionPane.showMessageDialog(null, msg, "Información", JOptionPane.INFORMATION_MESSAGE); }
    private static void exito(String msg) { JOptionPane.showMessageDialog(null, msg, "Éxito", JOptionPane.INFORMATION_MESSAGE); }
    private static void error(String msg) { JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE); }
    private static boolean confirmar(String m){
        return JOptionPane.showConfirmDialog(null, m, "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}