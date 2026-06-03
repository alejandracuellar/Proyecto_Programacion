package edu.uptc.controlador;

import edu.uptc.dominio.*;
import edu.uptc.enums.EstadoContrato;
import edu.uptc.excepciones.*;
import edu.uptc.servicios.ServicioContrato;
import java.util.List;

/**
 * Controlador para la gestión de contratos.
 * Actúa como intermediario entre la vista (Application) y el ServicioContrato.
 * Recibe los datos ya capturados, llama al servicio y propaga las excepciones.
 * No contiene JOptionPane ni lógica de negocio.
 *
 * @author Alejandra Cuellar, Laura González
 * @version 1.0
 */
public class ControladorContrato {

    private ServicioContrato servicioContrato;

    /**
     * Constructor del ControladorContrato.
     *
     * @param servicioContrato Servicio de lógica de contratos.
     */
    public ControladorContrato(ServicioContrato servicioContrato) {
        this.servicioContrato = servicioContrato;
    }

    /**
     * Registra un nuevo contrato en el sistema.
     *
     * @param contrato Contrato a registrar.
     * @throws ContratoYaExisteException Si el número ya está registrado.
     * @throws ContratoInvalidoException Si no supera las validaciones de su tipo.
     */
    public void crearContrato(Contrato contrato)
            throws ContratoYaExisteException, ContratoInvalidoException {
        servicioContrato.crearContrato(contrato);
    }

    /**
     * Retorna todos los contratos registrados.
     *
     * @return Lista de contratos.
     */
    public List<Contrato> obtenerContratos() {
        return servicioContrato.obtenerContratos();
    }

    /**
     * Retorna los contratos de un contratante específico.
     *
     * @param numeroDocContratante Documento del contratante.
     * @return Lista de contratos del contratante.
     */
    public List<Contrato> obtenerContratosPorContratante(String numeroDocContratante) {
        return servicioContrato.obtenerContratosPorContratante(numeroDocContratante);
    }

    /**
     * Retorna los contratos disponibles para selección.
     *
     * @return Lista de contratos en estado PUBLICADO o LICITACION.
     */
    public List<Contrato> obtenerContratosDisponibles() {
        return servicioContrato.obtenerContratosDisponibles();
    }

    /**
     * Busca un contrato por su número único.
     *
     * @param numeroContrato Número del contrato.
     * @return Contrato encontrado.
     * @throws ContratoNoEncontradoException Si no existe.
     */
    public Contrato buscarContrato(String numeroContrato) throws ContratoNoEncontradoException {
        return servicioContrato.buscarContrato(numeroContrato);
    }

    /**
     * Actualiza los datos de un contrato en estado PUBLICADO.
     *
     * @param contrato Contrato con datos actualizados.
     * @throws ContratoNoEncontradoException     Si no existe.
     * @throws ActualizacionNoPermitidaException Si no está en estado PUBLICADO.
     * @throws ContratoInvalidoException         Si no supera las validaciones.
     */
    public void actualizarContrato(Contrato contrato) throws ContratoNoEncontradoException,
            ActualizacionNoPermitidaException, ContratoInvalidoException {
        servicioContrato.actualizarContrato(contrato);
    }

    /**
     * Elimina un contrato en estado PUBLICADO.
     *
     * @param numeroContrato Número del contrato a eliminar.
     * @throws ContratoNoEncontradoException   Si no existe.
     * @throws EliminacionNoPermitidaException Si no está en estado PUBLICADO.
     */
    public void eliminarContrato(String numeroContrato) throws ContratoNoEncontradoException,
            EliminacionNoPermitidaException {
        servicioContrato.eliminarContrato(numeroContrato);
    }

    /**
     * Cambia el estado de un contrato y genera el reporte de interventoría.
     *
     * @param numeroContrato Número del contrato.
     * @param nuevoEstado    Estado al que se avanza.
     * @param informe        Justificación del cambio.
     * @throws ContratoNoEncontradoException     Si no existe el contrato.
     * @throws TransicionEstadoInvalidaException Si la transición no es válida.
     */
    public void cambiarEstadoContrato(String numeroContrato, EstadoContrato nuevoEstado, String informe)
            throws ContratoNoEncontradoException,TransicionEstadoInvalidaException {
        servicioContrato.cambiarEstadoContrato(numeroContrato, nuevoEstado, informe);
    }

    /**
     * Asigna un contratista a un contrato disponible.
     *
     * @param numeroContrato Número del contrato.
     * @param contratista    Contratista a asignar.
     * @throws ContratoNoEncontradoException Si no existe el contrato.
     * @throws ContratoInvalidoException     Si el contrato no está disponible.
     */
    public void seleccionarContrato(String numeroContrato, Contratista contratista)
            throws ContratoNoEncontradoException, ContratoInvalidoException {
        servicioContrato.seleccionarContrato(numeroContrato, contratista);
    }
}