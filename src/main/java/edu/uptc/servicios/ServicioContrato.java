package edu.uptc.servicios;

import edu.uptc.dominio.*;
import edu.uptc.enums.EstadoContrato;
import edu.uptc.excepciones.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de lógica de negocio para la gestión de contratos.
 * Implementa todas las validaciones, reglas de negocio y el almacenamiento
 * en memoria. Lanza excepciones específicas ante cualquier violación de reglas.
 * Forma parte de la capa de servicios en la arquitectura n-capas.
 *
 * @author Alejandra Cuellar, Laura Gonzalez, Elkin Pineda
 * @version 1.0
 */
public class ServicioContrato {

    /** Lista de contratos registrados en el sistema. */
    private List<Contrato> contratos;

    /** Servicio de reportes para generar interventorías automáticas. */
    private ServicioReporte servicioReporte;

    /**
     * Constructor que inicializa la lista y recibe el servicio de reportes.
     *
     * @param servicioReporte Instancia del servicio de reportes.
     */
    public ServicioContrato(ServicioReporte servicioReporte) {
        this.contratos= new ArrayList<>();
        this.servicioReporte = servicioReporte;
    }


    /**
     * Registra un nuevo contrato en el sistema.
     * Valida que el número no esté duplicado y aplica las reglas del tipo de contrato.
     *
     * @param contrato Contrato a registrar.
     * @throws ContratoYaExisteException  Si ya existe un contrato con ese número.
     * @throws ContratoInvalidoException  Si el contrato no cumple las reglas de su tipo.
     * @throws IllegalArgumentException   Si los campos básicos están vacíos.
     */
    public void crearContrato(Contrato contrato) throws ContratoYaExisteException, ContratoInvalidoException {
        validarCamposBase(contrato);
        if (existeNumero(contrato.getNumeroContrato())) {
            throw new ContratoYaExisteException(contrato.getNumeroContrato());
        }
        validarSegunTipo(contrato);
        contratos.add(contrato);
    }

    /**
     * Retorna todos los contratos registrados.
     *
     * @return Lista de contratos (puede estar vacía).
     */
    public List<Contrato> obtenerContratos() {
        return new ArrayList<>(contratos);
    }

    /**
     * Retorna los contratos creados por un contratante específico.
     *
     * @param numeroDocContratante Número de documento del contratante.
     * @return Lista de contratos del contratante (puede estar vacía).
     */
    public List<Contrato> obtenerContratosPorContratante(String numeroDocContratante) {
        List<Contrato> resultado = new ArrayList<>();
        for (Contrato c : contratos) {
            if (c.getContratante() != null && c.getContratante().getNumeroDocumento().equals(numeroDocContratante)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    /**
     * Retorna los contratos disponibles para selección (estado PUBLICADO o LICITACION).
     *
     * @return Lista de contratos disponibles (puede estar vacía).
     */
    public List<Contrato> obtenerContratosDisponibles() {
        List<Contrato> disponibles = new ArrayList<>();
        for (Contrato c : contratos) {
            if (c.getEstado() == EstadoContrato.PUBLICADO || c.getEstado() == EstadoContrato.LICITACION) {
                disponibles.add(c);
            }
        }
        return disponibles;
    }

    /**
     * Busca un contrato por su número único.
     *
     * @param numeroContrato Número del contrato a buscar.
     * @return El contrato encontrado.
     * @throws ContratoNoEncontradoException Si no existe un contrato con ese número.
     */
    public Contrato buscarContrato(String numeroContrato)throws ContratoNoEncontradoException {
        for (Contrato c : contratos) {
            if (c.getNumeroContrato().equals(numeroContrato)) return c;
        }
        throw new ContratoNoEncontradoException(numeroContrato);
    }

    /**
     * Actualiza los datos de un contrato existente.
     * Solo se permite actualizar contratos en estado PUBLICADO.
     *
     * @param contrato Contrato con los datos actualizados.
     * @throws ContratoNoEncontradoException     Si no existe el contrato a actualizar.
     * @throws ActualizacionNoPermitidaException Si el contrato no está en estado PUBLICADO.
     * @throws ContratoInvalidoException         Si el contrato no supera la validación.
     */
    public void actualizarContrato(Contrato contrato)throws ContratoNoEncontradoException,
            ActualizacionNoPermitidaException, ContratoInvalidoException {
        Contrato existente = buscarContrato(contrato.getNumeroContrato());
        if (existente.getEstado() != EstadoContrato.PUBLICADO) {
            throw new ActualizacionNoPermitidaException(existente.getEstado());
        }
        validarSegunTipo(contrato);
        for (int i = 0; i < contratos.size(); i++) {
            if (contratos.get(i).getNumeroContrato().equals(contrato.getNumeroContrato())) {
                contratos.set(i, contrato);
                return;
            }
        }
    }

    /**
     * Elimina un contrato del sistema.
     * Solo se permite eliminar contratos en estado PUBLICADO.
     *
     * @param numeroContrato Número del contrato a eliminar.
     * @throws ContratoNoEncontradoException   Si no existe un contrato con ese número.
     * @throws EliminacionNoPermitidaException Si el contrato no está en estado PUBLICADO.
     */
    public void eliminarContrato(String numeroContrato) throws ContratoNoEncontradoException,
            EliminacionNoPermitidaException {
        Contrato existente = buscarContrato(numeroContrato);
        if (existente.getEstado() != EstadoContrato.PUBLICADO) {
            throw new EliminacionNoPermitidaException(existente.getEstado());
        }
        contratos.removeIf(c -> c.getNumeroContrato().equals(numeroContrato));
    }

    /**
     * Cambia el estado de un contrato y genera automáticamente un reporte de interventoría.
     * Los estados deben avanzar en orden estricto.
     *
     * @param numeroContrato Número del contrato a modificar.
     * @param nuevoEstado    Estado al que se desea avanzar.
     * @param informe        Justificación del cambio (obligatorio).
     * @throws ContratoNoEncontradoException         Si no existe el contrato.
     * @throws TransicionEstadoInvalidaException     Si la transición de estado no es válida.
     * @throws IllegalArgumentException              Si el informe está vacío.
     */
    public void cambiarEstadoContrato(String numeroContrato, EstadoContrato nuevoEstado, String informe)
            throws ContratoNoEncontradoException, TransicionEstadoInvalidaException {
        if (informe == null || informe.trim().isEmpty()) {
            throw new IllegalArgumentException("El informe de interventoría es obligatorio para cambiar el estado.");
        }
        Contrato contrato = buscarContrato(numeroContrato);
        if (!transicionValida(contrato.getEstado(), nuevoEstado)) {
            throw new TransicionEstadoInvalidaException(contrato.getEstado(), nuevoEstado);
        }
        contrato.setEstado(nuevoEstado);
        servicioReporte.generarReporte(contrato, informe.trim(), LocalDateTime.now());
    }

    /**
     * Asigna un contratista a un contrato disponible.
     *
     * @param numeroContrato Número del contrato.
     * @param contratista    Contratista a asignar.
     * @throws ContratoNoEncontradoException Si no existe el contrato.
     * @throws ContratoInvalidoException     Si el contrato no está disponible para selección.
     */
    public void seleccionarContrato(String numeroContrato, Contratista contratista)
            throws ContratoNoEncontradoException, ContratoInvalidoException {
        if (contratista == null) throw new IllegalArgumentException("El contratista no puede ser nulo.");
        Contrato contrato = buscarContrato(numeroContrato);
        if (contrato.getEstado() != EstadoContrato.PUBLICADO &&
                contrato.getEstado() != EstadoContrato.LICITACION) {
            throw new ContratoInvalidoException(
                    "El contrato N° " + numeroContrato + " no está disponible para selección. Estado actual: " + contrato.getEstado());
        }
        contrato.setContratista(contratista);
    }


    /**
     * Valida los campos base obligatorios de cualquier contrato.
     *
     * @param c Contrato a validar.
     * @throws IllegalArgumentException Si algún campo obligatorio está vacío o es nulo.
     */
    private void validarCamposBase(Contrato c) {
        if (c == null)                                    throw new IllegalArgumentException("El contrato no puede ser nulo.");
        if (vacio(c.getNumeroContrato()))                 throw new IllegalArgumentException("El número de contrato es obligatorio.");
        if (vacio(c.getObjetoContrato()))                 throw new IllegalArgumentException("El objeto del contrato es obligatorio.");
        if (c.getFechaCreacion() == null)                 throw new IllegalArgumentException("La fecha de creación es obligatoria.");
        if (c.getPlazoEjecucion() == null)                throw new IllegalArgumentException("El plazo de ejecución es obligatorio.");
        if (c.getValorContrato() <= 0)                    throw new IllegalArgumentException("El valor del contrato debe ser mayor a cero.");
        if (c.getContratante() == null)                   throw new IllegalArgumentException("El contratante es obligatorio.");
        if (c.getPlazoEjecucion().isBefore(c.getFechaCreacion()))
            throw new IllegalArgumentException("El plazo de ejecución no puede ser anterior a la fecha de creación.");
    }

    /**
     * Aplica las reglas de validación específicas según el tipo de contrato.
     *
     * @param c Contrato a validar.
     * @throws ContratoInvalidoException Si el contrato no cumple las reglas de su tipo.
     */
    private void validarSegunTipo(Contrato c) throws ContratoInvalidoException {
        if (c instanceof ContratoPrestacionServicio) {
            validarPrestacionServicio((ContratoPrestacionServicio) c);
        } else if (c instanceof ContratoCompraVenta) {
            validarCompraVenta((ContratoCompraVenta) c);
        } else if (c instanceof ContratoObraPublica) {
            validarObraPublica((ContratoObraPublica) c);
        }
    }

    /**
     * Valida que honorarioMensual × meses == valorContrato.
     *
     * @param c Contrato de prestación de servicios.
     * @throws ContratoInvalidoException Si la suma no coincide con el valor total.
     */
    private void validarPrestacionServicio(ContratoPrestacionServicio c) throws ContratoInvalidoException {
        if (vacio(c.getPerfilRequerido()))
            throw new ContratoInvalidoException("El perfil requerido es obligatorio en un contrato de prestación de servicios.");
        if (vacio(c.getEntregables()))
            throw new ContratoInvalidoException("Los entregables son obligatorios en un contrato de prestación de servicios.");
        if (c.getValorHonorarioMensual() <= 0)
            throw new ContratoInvalidoException("El honorario mensual debe ser mayor a cero.");

        long meses = ChronoUnit.MONTHS.between(c.getFechaCreacion(), c.getPlazoEjecucion());
        if (meses <= 0) throw new ContratoInvalidoException("El plazo de ejecución debe ser al menos un mes posterior a la fecha de creación.");

        double totalHonorarios = c.getValorHonorarioMensual() * meses;
        if (Math.abs(totalHonorarios - c.getValorContrato()) >= 0.01) {
            throw new ContratoInvalidoException(
                    "La suma de honorarios mensuales (" + meses + " meses × $" +
                            String.format("%,.2f", c.getValorHonorarioMensual()) + " = $" +
                            String.format("%,.2f", totalHonorarios) + ") no coincide con el valor del contrato ($"
                            + String.format("%,.2f", c.getValorContrato()) + ")."
            );
        }
    }

    /**
     * Valida que cantidad × valorUnitario == valorContrato.
     *
     * @param c Contrato de compraventa.
     * @throws ContratoInvalidoException Si el producto no coincide con el valor total.
     */
    private void validarCompraVenta(ContratoCompraVenta c)
            throws ContratoInvalidoException {
        if (vacio(c.getItem()))   throw new ContratoInvalidoException("El ítem del bien es obligatorio.");
        if (vacio(c.getMarca()))  throw new ContratoInvalidoException("La marca del bien es obligatoria.");
        if (vacio(c.getModelo())) throw new ContratoInvalidoException("El modelo del bien es obligatorio.");
        if (vacio(c.getSerie()))  throw new ContratoInvalidoException("La serie del bien es obligatoria.");
        if (c.getValorUnitario() <= 0)   throw new ContratoInvalidoException("El valor unitario debe ser mayor a cero.");
        if (c.getCantidadAdquirir() <= 0) throw new ContratoInvalidoException("La cantidad a adquirir debe ser mayor a cero.");

        double totalCalculado = (double) c.getCantidadAdquirir() * c.getValorUnitario();
        if (Math.abs(totalCalculado - c.getValorContrato()) >= 0.01) {
            throw new ContratoInvalidoException(
                    "El producto cantidad × valor unitario (" + c.getCantidadAdquirir() + " × $"
                            + String.format("%,.2f", c.getValorUnitario()) + " = $" +
                            String.format("%,.2f", totalCalculado) + ") no coincide con el valor del contrato ($"
                            + String.format("%,.2f", c.getValorContrato()) + ")."
            );
        }
    }

    /**
     * Valida que la ubicación y área de intervención de una obra pública sean correctas.
     *
     * @param c Contrato de obra pública.
     * @throws ContratoInvalidoException Si la ubicación está vacía o el área es inválida.
     */
    private void validarObraPublica(ContratoObraPublica c) throws ContratoInvalidoException {
        if (vacio(c.getUbicacionObra()))
            throw new ContratoInvalidoException("La ubicación de la obra es obligatoria.");
        if (c.getAreaIntervencion() <= 0)
            throw new ContratoInvalidoException("El área de intervención debe ser mayor a cero.");
    }

    /**
     * Verifica si ya existe un contrato con el número dado.
     *
     * @param numeroContrato Número a verificar.
     * @return true si ya existe.
     */
    private boolean existeNumero(String numeroContrato) {
        for (Contrato c : contratos) {
            if (c.getNumeroContrato().equals(numeroContrato)) return true;
        }
        return false;
    }

    /**
     * Verifica si la transición entre estados es válida y en orden.
     * Orden requerido: PUBLICADO → LICITACION → ADJUDICADO → EJECUCION → FINALIZADO.
     *
     * @param actual    Estado actual del contrato.
     * @param siguiente Estado al que se quiere pasar.
     * @return true si la transición es válida.
     */
    private boolean transicionValida(EstadoContrato actual, EstadoContrato siguiente) {
        switch (actual) {
            case PUBLICADO:
                return siguiente == EstadoContrato.LICITACION;
            case LICITACION:
                return siguiente == EstadoContrato.ADJUDICADO;
            case ADJUDICADO:
                return siguiente == EstadoContrato.EJECUCION;
            case EJECUCION:
                return siguiente == EstadoContrato.FINALIZADO;
            default:
                return false;
        }
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