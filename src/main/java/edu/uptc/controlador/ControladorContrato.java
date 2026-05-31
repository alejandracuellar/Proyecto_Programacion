package edu.uptc.controlador;

import edu.uptc.dominio.*;
import edu.uptc.enums.EstadoContrato;
import edu.uptc.excepciones.*;
import edu.uptc.servicios.ServicioContrato;
import edu.uptc.servicios.ServicioUsuario;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlador para la gestión de contratos del sistema.
 * Recibe entradas por JOptionPane, delega la lógica a {@link ServicioContrato}
 * y propaga todas las excepciones hacia la vista {@code Application}.
 * Forma parte de la capa de controladores en la arquitectura n-capas.
 *
 * @author Sistema Contratos Públicos - UPTC
 * @version 1.0
 */
public class ControladorContrato {

    /** Servicio de lógica de negocio para contratos. */
    private ServicioContrato servicioContrato;

    /** Servicio de usuarios para obtener contratistas. */
    private ServicioUsuario servicioUsuario;

    /** Formato de fecha para la entrada del usuario. */
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Constructor del ControladorContrato.
     *
     * @param servicioContrato Instancia del servicio de contratos.
     * @param servicioUsuario  Instancia del servicio de usuarios.
     */
    public ControladorContrato(ServicioContrato servicioContrato, ServicioUsuario servicioUsuario) {
        this.servicioContrato = servicioContrato;
        this.servicioUsuario  = servicioUsuario;
    }

    /**
     * Muestra el menú principal del Contratante.
     *
     * @param contratante El contratante autenticado.
     * @throws ContratoNoEncontradoException     Si el contrato buscado no existe.
     * @throws ContratoYaExisteException         Si el número de contrato está duplicado.
     * @throws ContratoInvalidoException         Si el contrato no supera validaciones.
     * @throws ActualizacionNoPermitidaException Si se intenta actualizar en estado no PUBLICADO.
     * @throws EliminacionNoPermitidaException   Si se intenta eliminar en estado no PUBLICADO.
     * @throws UsuarioNoEncontradoException      Si el contratista indicado no existe.
     * @throws TransicionEstadoInvalidaException Si la transición de estado no es válida.
     */
    public void mostrarMenuContratante(Contratante contratante)
            throws ContratoNoEncontradoException, ContratoYaExisteException, ContratoInvalidoException,
            ActualizacionNoPermitidaException, EliminacionNoPermitidaException, UsuarioNoEncontradoException,
            TransicionEstadoInvalidaException {
        boolean continuar = true;
        while (continuar) {
            String[] opciones = {
                    "Crear Contrato", "Consultar Contrato", "Actualizar Contrato",
                    "Eliminar Contrato", "Mis Contratos", "Cerrar Sesión"
            };
            int op = JOptionPane.showOptionDialog(null,
                    "Contratante: " + contratante.getNombre() +
                            "\nEntidad: " + contratante.getCodigoEntidad(),
                    "Menú Contratante",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, opciones, opciones[0]);
            switch (op) {
                case 0: menuCrearContrato(contratante); break;
                case 1: consultarContrato();  break;
                case 2: actualizarContrato(); break;
                case 3: eliminarContrato();   break;
                case 4: listarContratosPorContratante(contratante); break;
                default: continuar = false;
            }
        }
    }


    /**
     * Muestra el submenú para elegir el tipo de contrato a crear.
     *
     * @param contratante Contratante que crea el contrato.
     * @throws ContratoYaExisteException    Si el número ya existe.
     * @throws ContratoInvalidoException    Si el contrato no es válido.
     * @throws UsuarioNoEncontradoException Si el contratista no existe.
     */
    public void menuCrearContrato(Contratante contratante)
            throws ContratoYaExisteException, ContratoInvalidoException,
            UsuarioNoEncontradoException {
        String[] tipos = {"Prestación de Servicios", "Compraventa", "Obra Pública", "Cancelar"};
        int tipo = JOptionPane.showOptionDialog(null, "Seleccione el tipo de contrato:",
                "Tipo de Contrato", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, tipos, tipos[0]);
        switch (tipo) {
            case 0: crearContratoPrestacion(contratante);  break;
            case 1: crearContratoCompraVenta(contratante); break;
            case 2: crearContratoObraPublica(contratante); break;
        }
    }

    /**
     * Solicita datos y crea un contrato de prestación de servicios.
     *
     * @param contratante Contratante responsable.
     * @throws ContratoYaExisteException    Si el número ya existe.
     * @throws ContratoInvalidoException    Si el contrato no supera validación.
     * @throws UsuarioNoEncontradoException Si el contratista no existe.
     */
    public void crearContratoPrestacion(Contratante contratante)
            throws ContratoYaExisteException, ContratoInvalidoException,
            UsuarioNoEncontradoException {
        String[] base = solicitarDatosBase(); if (base == null) return;
        String perfil    = pedir("Perfil profesional requerido:"); if (perfil == null) return;
        String entregas  = pedir("Entregables del contrato:");     if (entregas == null) return;
        String honorario = pedir("Honorario mensual ($):");        if (honorario == null) return;

        Contratista contratista = obtenerContratistaSiExiste(base[5]);
        ContratoPrestacionServicio c = new ContratoPrestacionServicio(
                base[0], base[1],
                LocalDate.parse(base[2].trim(), FMT), contratante, contratista,
                Double.parseDouble(base[4].trim()),
                LocalDate.parse(base[3].trim(), FMT),
                perfil, entregas, Double.parseDouble(honorario.trim())
        );
        servicioContrato.crearContrato(c);
        info("Contrato de Prestación de Servicios creado exitosamente.");
    }

    /**
     * Solicita datos y crea un contrato de compraventa.
     *
     * @param contratante Contratante responsable.
     * @throws ContratoYaExisteException    Si el número ya existe.
     * @throws ContratoInvalidoException    Si el contrato no supera validación.
     * @throws UsuarioNoEncontradoException Si el contratista no existe.
     */
    public void crearContratoCompraVenta(Contratante contratante)
            throws ContratoYaExisteException, ContratoInvalidoException,
            UsuarioNoEncontradoException {
        String[] base = solicitarDatosBase(); if (base == null) return;
        String item    = pedir("Ítem o bien a adquirir:"); if (item == null) return;
        String marca   = pedir("Marca:");                  if (marca == null) return;
        String modelo  = pedir("Modelo:");                 if (modelo == null) return;
        String serie   = pedir("Serie:");                  if (serie == null) return;
        String valUnit = pedir("Valor unitario ($):");     if (valUnit == null) return;
        String cant    = pedir("Cantidad a adquirir:");    if (cant == null) return;

        Contratista contratista = obtenerContratistaSiExiste(base[5]);
        ContratoCompraVenta c = new ContratoCompraVenta(
                base[0], base[1],
                LocalDate.parse(base[2].trim(), FMT), contratante, contratista,
                Double.parseDouble(base[4].trim()),
                LocalDate.parse(base[3].trim(), FMT),
                item, marca, modelo, serie,
                Double.parseDouble(valUnit.trim()), Integer.parseInt(cant.trim())
        );
        servicioContrato.crearContrato(c);
        info("Contrato de Compraventa creado exitosamente.");
    }

    /**
     * Solicita datos y crea un contrato de obra pública.
     *
     * @param contratante Contratante responsable.
     * @throws ContratoYaExisteException    Si el número ya existe.
     * @throws ContratoInvalidoException    Si el contrato no supera validación.
     * @throws UsuarioNoEncontradoException Si el contratista no existe.
     */
    public void crearContratoObraPublica(Contratante contratante)
            throws ContratoYaExisteException, ContratoInvalidoException,
            UsuarioNoEncontradoException {
        String[] base    = solicitarDatosBase(); if (base == null) return;
        String ubicacion = pedir("Ubicación de la obra (dirección urbana o rural):"); if (ubicacion == null) return;
        String area      = pedir("Área de intervención (m²):");                       if (area == null) return;

        Contratista contratista = obtenerContratistaSiExiste(base[5]);
        ContratoObraPublica c = new ContratoObraPublica(
                base[0], base[1],
                LocalDate.parse(base[2].trim(), FMT), contratante, contratista,
                Double.parseDouble(base[4].trim()),
                LocalDate.parse(base[3].trim(), FMT),
                ubicacion, Double.parseDouble(area.trim())
        );
        servicioContrato.crearContrato(c);
        info("Contrato de Obra Pública creado exitosamente.");
    }


    /**
     * Consulta y muestra la información de un contrato.
     *
     * @throws ContratoNoEncontradoException Si el contrato no existe.
     */
    public void consultarContrato() throws ContratoNoEncontradoException {
        String num = pedir("Número del contrato a consultar:");
        if (num == null) return;
        Contrato c = servicioContrato.buscarContrato(num.trim());
        info(c.toString());
    }

    /**
     * Actualiza el objeto de un contrato en estado PUBLICADO.
     *
     * @throws ContratoNoEncontradoException     Si el contrato no existe.
     * @throws ActualizacionNoPermitidaException Si no está en estado PUBLICADO.
     * @throws ContratoInvalidoException         Si el contrato no es válido tras actualizar.
     */
    public void actualizarContrato()
            throws ContratoNoEncontradoException,
            ActualizacionNoPermitidaException,
            ContratoInvalidoException {
        String num = pedir("Número del contrato a actualizar:");
        if (num == null) return;
        Contrato c = servicioContrato.buscarContrato(num.trim());
        String nuevo = pedir("Nuevo objeto del contrato (actual: " + c.getObjetoContrato() + "):");
        if (nuevo != null && !nuevo.trim().isEmpty()) c.setObjetoContrato(nuevo.trim());
        servicioContrato.actualizarContrato(c);
        info("Contrato actualizado exitosamente.");
    }

    /**
     * Elimina un contrato en estado PUBLICADO previo confirmación.
     *
     * @throws ContratoNoEncontradoException   Si el contrato no existe.
     * @throws EliminacionNoPermitidaException Si no está en estado PUBLICADO.
     */
    public void eliminarContrato()
            throws ContratoNoEncontradoException,
            EliminacionNoPermitidaException {
        String num = pedir("Número del contrato a eliminar:");
        if (num == null) return;
        if (confirmar("¿Eliminar el contrato N° " + num + "?")) {
            servicioContrato.eliminarContrato(num.trim());
            info("Contrato eliminado exitosamente.");
        }
    }

    /**
     * Lista los contratos del contratante actual.
     *
     * @param contratante El contratante autenticado.
     */
    public void listarContratosPorContratante(Contratante contratante) {
        List<Contrato> lista = servicioContrato.obtenerContratosPorContratante(
                contratante.getNumeroDocumento());
        if (lista.isEmpty()) { info("No tiene contratos registrados."); return; }
        StringBuilder sb = new StringBuilder("=== Mis Contratos ===\n\n");
        for (Contrato c : lista) sb.append("• ").append(c).append("\n");
        info(sb.toString());
    }


    /**
     * Muestra los contratos disponibles para selección.
     */
    public void verContratosDisponibles() {
        List<Contrato> lista = servicioContrato.obtenerContratosDisponibles();
        if (lista.isEmpty()) { info("No hay contratos disponibles."); return; }
        StringBuilder sb = new StringBuilder("=== Contratos Disponibles ===\n\n");
        for (Contrato c : lista) sb.append("• ").append(c).append("\n");
        info(sb.toString());
    }

    /**
     * Permite al contratista seleccionar un contrato disponible.
     *
     * @param contratista El contratista que selecciona.
     * @throws ContratoNoEncontradoException Si el contrato no existe.
     * @throws ContratoInvalidoException     Si el contrato no está disponible.
     */
    public void seleccionarContrato(Contratista contratista)
            throws ContratoNoEncontradoException, ContratoInvalidoException {
        String num = pedir("Número del contrato a seleccionar:");
        if (num == null) return;
        servicioContrato.seleccionarContrato(num.trim(), contratista);
        info("Contrato seleccionado exitosamente.");
    }

    /**
     * Permite al contratista cambiar el estado de un contrato.
     * Genera automáticamente el reporte de interventoría.
     *
     * @throws ContratoNoEncontradoException     Si el contrato no existe.
     * @throws TransicionEstadoInvalidaException Si la transición no es válida.
     */
    public void cambiarEstadoContrato()
            throws ContratoNoEncontradoException,
            TransicionEstadoInvalidaException {
        String num = pedir("Número del contrato:");
        if (num == null) return;
        Contrato c = servicioContrato.buscarContrato(num.trim());
        info("Estado actual: " + c.getEstado());

        EstadoContrato[] estados = EstadoContrato.values();
        String[] nombres = new String[estados.length];
        for (int i = 0; i < estados.length; i++) nombres[i] = estados[i].name();

        String sel = (String) JOptionPane.showInputDialog(null,
                "Seleccione el nuevo estado:", "Cambiar Estado",
                JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);
        if (sel == null) return;

        String informe = pedir("Informe que justifica el cambio de estado:");
        if (informe == null || informe.trim().isEmpty()) {
            throw new IllegalArgumentException("El informe es obligatorio para cambiar el estado.");
        }
        servicioContrato.cambiarEstadoContrato(num.trim(), EstadoContrato.valueOf(sel), informe.trim());
        info("Estado actualizado a " + sel + ". Reporte de interventoría generado.");
    }


    /**
     * Solicita los datos base comunes a todos los tipos de contrato.
     *
     * @return Arreglo [numeroContrato, objetoContrato, fechaCreacion, plazoEjecucion,
     *         valorContrato, docContratista], o null si se cancela.
     */
    private String[] solicitarDatosBase() {
        String num    = pedir("Número de contrato:");             if (num == null) return null;
        String objeto = pedir("Objeto del contrato:");           if (objeto == null) return null;
        String fCrea  = pedir("Fecha creación (dd/MM/yyyy):");   if (fCrea == null) return null;
        String fPlazo = pedir("Plazo ejecución (dd/MM/yyyy):"); if (fPlazo == null) return null;
        String valor  = pedir("Valor total del contrato ($):"); if (valor == null) return null;
        String docCon = pedir("Documento del contratista (vacío si no aplica):");
        if (docCon == null) docCon = "";
        return new String[]{num, objeto, fCrea, fPlazo, valor, docCon};
    }

    /**
     * Obtiene un contratista si el documento fue proporcionado; retorna null si vacío.
     *
     * @param doc Número de documento del contratista.
     * @return El contratista encontrado o null.
     * @throws UsuarioNoEncontradoException Si se proporcionó documento pero no existe.
     */
    private Contratista obtenerContratistaSiExiste(String doc)
            throws UsuarioNoEncontradoException {
        if (doc == null || doc.trim().isEmpty()) return null;
        return servicioUsuario.buscarContratista(doc.trim());
    }

    private String pedir(String msg)  { return JOptionPane.showInputDialog(null, msg); }
    private void info(String msg)     { JOptionPane.showMessageDialog(null, msg, "Información", JOptionPane.INFORMATION_MESSAGE); }
    private boolean confirmar(String m) {
        return JOptionPane.showConfirmDialog(null, m, "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}