package edu.uptc.servicios;

import edu.uptc.dominio.*;
import edu.uptc.enums.EstadoContrato;
import edu.uptc.excepciones.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de lógica de negocio para la gestión de contratos.
 * Centraliza todas las validaciones por tipo de contrato, las reglas de negocio
 * y el almacenamiento en memoria. Lanza excepciones específicas ante cualquier error.
 * No realiza ninguna interacción con el usuario (sin JOptionPane).
 *
 * @author Alejandra Cuellar, Laura Gonzalez, Elkin Pineda
 * @version 1.0
 */
public class ServicioContrato {

    private List<Contrato>   contratos;
    private ServicioReporte  servicioReporte;

    /**
     * Constructor que recibe el servicio de reportes para generar interventorías automáticas.
     *
     * @param servicioReporte Servicio de reportes de interventoría.
     */
    public ServicioContrato(ServicioReporte servicioReporte) {
        this.contratos       = new ArrayList<>();
        this.servicioReporte = servicioReporte;
    }


    /**
     * Registra un nuevo contrato en el sistema.
     *
     * @param contrato Contrato a registrar.
     * @throws ContratoYaExisteException Si ya existe un contrato con ese número.
     * @throws ContratoInvalidoException Si el contrato no supera las validaciones de su tipo.
     * @throws IllegalArgumentException  Si algún campo base obligatorio está vacío.
     */
    public void crearContrato(Contrato contrato)
            throws ContratoYaExisteException, ContratoInvalidoException {
        validarCamposBase(contrato);
        if (existeNumero(contrato.getNumeroContrato()))
            throw new ContratoYaExisteException(contrato.getNumeroContrato());
        validarSegunTipo(contrato);
        contratos.add(contrato);
    }


    /** @return Lista de todos los contratos registrados. */
    public List<Contrato> obtenerContratos() { return new ArrayList<>(contratos); }

    /**
     * Retorna los contratos de un contratante específico.
     *
     * @param numeroDocContratante Documento del contratante.
     * @return Lista de sus contratos (puede estar vacía).
     */
    public List<Contrato> obtenerContratosPorContratante(String numeroDocContratante) {
        List<Contrato> resultado = new ArrayList<>();
        for (Contrato c : contratos)
            if (c.getContratante() != null &&
                    c.getContratante().getNumeroDocumento().equals(numeroDocContratante))
                resultado.add(c);
        return resultado;
    }

    /**
     * Retorna los contratos disponibles para selección (PUBLICADO o LICITACION).
     *
     * @return Lista de contratos disponibles (puede estar vacía).
     */
    public List<Contrato> obtenerContratosDisponibles() {
        List<Contrato> disponibles = new ArrayList<>();
        for (Contrato c : contratos)
            if (c.getEstado() == EstadoContrato.PUBLICADO ||
                    c.getEstado() == EstadoContrato.LICITACION)
                disponibles.add(c);
        return disponibles;
    }

    /**
     * Busca un contrato por su número único.
     *
     * @param numeroContrato Número del contrato.
     * @return Contrato encontrado.
     * @throws ContratoNoEncontradoException Si no existe un contrato con ese número.
     */
    public Contrato buscarContrato(String numeroContrato)
            throws ContratoNoEncontradoException {
        for (Contrato c : contratos)
            if (c.getNumeroContrato().equals(numeroContrato)) return c;
        throw new ContratoNoEncontradoException(numeroContrato);
    }


    /**
     * Actualiza los datos de un contrato. Solo se permite si está en estado PUBLICADO.
     *
     * @param contrato Contrato con los datos actualizados.
     * @throws ContratoNoEncontradoException     Si no existe el contrato.
     * @throws ActualizacionNoPermitidaException Si el contrato no está en estado PUBLICADO.
     * @throws ContratoInvalidoException         Si no supera las validaciones.
     */
    public void actualizarContrato(Contrato contrato)
            throws ContratoNoEncontradoException,
            ActualizacionNoPermitidaException,
            ContratoInvalidoException {
        Contrato existente = buscarContrato(contrato.getNumeroContrato());
        if (existente.getEstado() != EstadoContrato.PUBLICADO)
            throw new ActualizacionNoPermitidaException(existente.getEstado());
        validarSegunTipo(contrato);
        for (int i = 0; i < contratos.size(); i++)
            if (contratos.get(i).getNumeroContrato().equals(contrato.getNumeroContrato())) {
                contratos.set(i, contrato);
                return;
            }
    }


    /**
     * Elimina un contrato. Solo se permite si está en estado PUBLICADO.
     *
     * @param numeroContrato Número del contrato a eliminar.
     * @throws ContratoNoEncontradoException   Si no existe el contrato.
     * @throws EliminacionNoPermitidaException Si el contrato no está en estado PUBLICADO.
     */
    public void eliminarContrato(String numeroContrato)
            throws ContratoNoEncontradoException,
            EliminacionNoPermitidaException {
        Contrato existente = buscarContrato(numeroContrato);
        if (existente.getEstado() != EstadoContrato.PUBLICADO)
            throw new EliminacionNoPermitidaException(existente.getEstado());
        contratos.removeIf(c -> c.getNumeroContrato().equals(numeroContrato));
    }

    /**
     * Cambia el estado de un contrato y genera el reporte de interventoría automáticamente.
     * Los estados deben avanzar en orden estricto.
     *
     * @param numeroContrato Número del contrato.
     * @param nuevoEstado    Estado al que se avanza.
     * @param informe        Justificación del cambio (obligatorio).
     * @throws ContratoNoEncontradoException     Si no existe el contrato.
     * @throws TransicionEstadoInvalidaException Si la transición no sigue el orden establecido.
     * @throws IllegalArgumentException          Si el informe está vacío.
     */
    public void cambiarEstadoContrato(String numeroContrato,
                                      EstadoContrato nuevoEstado,
                                      String informe)
            throws ContratoNoEncontradoException,
            TransicionEstadoInvalidaException {
        if (informe == null || informe.trim().isEmpty())
            throw new IllegalArgumentException("El informe de interventoría es obligatorio.");
        Contrato contrato = buscarContrato(numeroContrato);
        if (!transicionValida(contrato.getEstado(), nuevoEstado))
            throw new TransicionEstadoInvalidaException(contrato.getEstado(), nuevoEstado);
        contrato.setEstado(nuevoEstado);
        servicioReporte.generarReporte(contrato, informe.trim(), LocalDateTime.now());
    }

    /**
     * Asigna un contratista a un contrato disponible (PUBLICADO o LICITACION).
     *
     * @param numeroContrato Número del contrato.
     * @param contratista    Contratista a asignar.
     * @throws ContratoNoEncontradoException Si no existe el contrato.
     * @throws ContratoInvalidoException     Si el contrato no está disponible para selección.
     */
    public void seleccionarContrato(String numeroContrato, Contratista contratista)
            throws ContratoNoEncontradoException, ContratoInvalidoException {
        Contrato contrato = buscarContrato(numeroContrato);
        if (contrato.getEstado() != EstadoContrato.PUBLICADO &&
                contrato.getEstado() != EstadoContrato.LICITACION)
            throw new ContratoInvalidoException(
                    "El contrato N° " + numeroContrato +
                            " no está disponible. Estado actual: " + contrato.getEstado());
        contrato.setContratista(contratista);
    }


    /** Valida campos base comunes a todo contrato. */
    private void validarCamposBase(Contrato c) {
        if (c == null)                  throw new IllegalArgumentException("El contrato no puede ser nulo.");
        if (vacio(c.getNumeroContrato()))throw new IllegalArgumentException("El número de contrato es obligatorio.");
        if (vacio(c.getObjetoContrato()))throw new IllegalArgumentException("El objeto del contrato es obligatorio.");
        if (c.getFechaCreacion() == null) throw new IllegalArgumentException("La fecha de creación es obligatoria.");
        if (c.getPlazoEjecucion() == null)throw new IllegalArgumentException("El plazo de ejecución es obligatorio.");
        if (c.getValorContrato() <= 0)   throw new IllegalArgumentException("El valor del contrato debe ser mayor a cero.");
        if (c.getContratante() == null)  throw new IllegalArgumentException("El contratante es obligatorio.");
        if (c.getPlazoEjecucion().isBefore(c.getFechaCreacion()))
            throw new IllegalArgumentException("El plazo de ejecución no puede ser anterior a la fecha de creación.");
    }

    /** Aplica las validaciones específicas según el tipo de contrato. */
    private void validarSegunTipo(Contrato c) throws ContratoInvalidoException {
        if      (c instanceof ContratoPrestacionServicio) validarPrestacion((ContratoPrestacionServicio) c);
        else if (c instanceof ContratoCompraVenta)        validarCompraVenta((ContratoCompraVenta) c);
        else if (c instanceof ContratoObraPublica)        validarObraPublica((ContratoObraPublica) c);
    }

    /**
     * Valida: honorarioMensual × meses == valorContrato.
     *
     * @throws ContratoInvalidoException Si la suma no coincide.
     */
    private void validarPrestacion(ContratoPrestacionServicio c) throws ContratoInvalidoException {
        if (vacio(c.getPerfilRequerido())) throw new ContratoInvalidoException("El perfil requerido es obligatorio.");
        if (vacio(c.getEntregables()))     throw new ContratoInvalidoException("Los entregables son obligatorios.");
        if (c.getValorHonorarioMensual() <= 0) throw new ContratoInvalidoException("El honorario mensual debe ser mayor a cero.");
        long meses = ChronoUnit.MONTHS.between(c.getFechaCreacion(), c.getPlazoEjecucion());
        if (meses <= 0) throw new ContratoInvalidoException("El plazo debe ser al menos un mes posterior a la fecha de creación.");
        double total = c.getValorHonorarioMensual() * meses;
        if (Math.abs(total - c.getValorContrato()) >= 0.01)
            throw new ContratoInvalidoException(
                    "Honorario mensual × meses (" + meses + " × $" +
                            String.format("%,.2f", c.getValorHonorarioMensual()) +
                            " = $" + String.format("%,.2f", total) +
                            ") no coincide con el valor del contrato ($" +
                            String.format("%,.2f", c.getValorContrato()) + ").");
    }

    /**
     * Valida: cantidad × valorUnitario == valorContrato.
     *
     * @throws ContratoInvalidoException Si el producto no coincide.
     */
    private void validarCompraVenta(ContratoCompraVenta c) throws ContratoInvalidoException {
        if (vacio(c.getItem()))   throw new ContratoInvalidoException("El ítem es obligatorio.");
        if (vacio(c.getMarca()))  throw new ContratoInvalidoException("La marca es obligatoria.");
        if (vacio(c.getModelo())) throw new ContratoInvalidoException("El modelo es obligatorio.");
        if (vacio(c.getSerie()))  throw new ContratoInvalidoException("La serie es obligatoria.");
        if (c.getValorUnitario() <= 0)    throw new ContratoInvalidoException("El valor unitario debe ser mayor a cero.");
        if (c.getCantidadAdquirir() <= 0) throw new ContratoInvalidoException("La cantidad debe ser mayor a cero.");
        double total = (double) c.getCantidadAdquirir() * c.getValorUnitario();
        if (Math.abs(total - c.getValorContrato()) >= 0.01)
            throw new ContratoInvalidoException(
                    "Cantidad × valor unitario (" + c.getCantidadAdquirir() + " × $" +
                            String.format("%,.2f", c.getValorUnitario()) +
                            " = $" + String.format("%,.2f", total) +
                            ") no coincide con el valor del contrato ($" +
                            String.format("%,.2f", c.getValorContrato()) + ").");
    }

    /**
     * Valida: ubicación no vacía y área mayor a cero.
     *
     * @throws ContratoInvalidoException Si la ubicación está vacía o el área es inválida.
     */
    private void validarObraPublica(ContratoObraPublica c) throws ContratoInvalidoException {
        if (vacio(c.getUbicacionObra()))    throw new ContratoInvalidoException("La ubicación de la obra es obligatoria.");
        if (c.getAreaIntervencion() <= 0)   throw new ContratoInvalidoException("El área de intervención debe ser mayor a cero.");
    }

    /** Verifica si ya existe un contrato con el número dado. */
    private boolean existeNumero(String numeroContrato) {
        for (Contrato c : contratos)
            if (c.getNumeroContrato().equals(numeroContrato)) return true;
        return false;
    }

    /** Valida que la transición de estados sea en orden estricto. */
    private boolean transicionValida(EstadoContrato actual, EstadoContrato siguiente) {
        switch (actual) {
            case PUBLICADO:  return siguiente == EstadoContrato.LICITACION;
            case LICITACION: return siguiente == EstadoContrato.ADJUDICADO;
            case ADJUDICADO: return siguiente == EstadoContrato.EJECUCION;
            case EJECUCION:  return siguiente == EstadoContrato.FINALIZADO;
            default:         return false;
        }
    }

    private boolean vacio(String s) { return s == null || s.trim().isEmpty(); }
}