package edu.uptc.servicios;

import edu.uptc.dominio.*;
import edu.uptc.enums.EstadoContrato;
import edu.uptc.excepciones.*;
import edu.uptc.servicios.ServicioContrato;
import edu.uptc.servicios.ServicioReporte;

import org.junit.jupiter.api.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link ServicioContrato}.
 *
 * @author Alejandra Cuellar, Laura González
 * @version 1.0
 */
class ServicioContratoTest {

    private ServicioContrato servicio;
    private ServicioReporte svcReporte;
    private Contratante contratante;
    private Contratista contratista;

    @BeforeEach
    void setUp() {
        svcReporte = new ServicioReporte();
        servicio = new ServicioContrato(svcReporte);

        contratante = new Contratante(
                "jurídica", "NIT", "900", "Entidad",
                "e@test.com", "p", "3", "Calle", "Bogotá",
                "Salud", "municipal", "ENT-001");

        contratista = new Contratista(
                "natural", "CC", "100", "Carlos",
                "c@test.com", "p", "3", "Cra", "Medellín",
                false, "Ingeniería");
    }


    @Test
    @DisplayName("crearContrato obra pública válida")
    void crearContratoObraPublicaValida() {
        assertDoesNotThrow(() -> servicio.crearContrato(obra("OP-001")));
    }

    @Test
    @DisplayName("crearContrato número duplicado lanza ContratoYaExisteException")
    void crearContratoNumeroDuplicado() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        assertThrows(ContratoYaExisteException.class,
                () -> servicio.crearContrato(obra("OP-001")));
    }

    @Test
    @DisplayName("crearContrato obra con ubicación vacía lanza ContratoInvalidoException")
    void crearContratoObraUbicacionVacia() {
        ContratoObraPublica c = new ContratoObraPublica(
                "OP-002", "Obra", LocalDate.now(), contratante, null,
                10_000_000.0, LocalDate.now().plusMonths(3), "", 500.0);
        assertThrows(ContratoInvalidoException.class, () -> servicio.crearContrato(c));
    }

    @Test
    @DisplayName("crearContrato compraventa válida")
    void crearContratoCompraVentaValida() {
        assertDoesNotThrow(() -> servicio.crearContrato(compraventa("CC-001", 2_000_000.0, 5, 10_000_000.0)));
    }

    @Test
    @DisplayName("crearContrato compraventa total incorrecto lanza ContratoInvalidoException")
    void crearContratoCompraVentaTotalIncorrecto() {
        assertThrows(ContratoInvalidoException.class,
                () -> servicio.crearContrato(compraventa("CC-002", 100_000.0, 10, 5_000_000.0)));
    }

    @Test
    @DisplayName("crearContrato prestación de servicios válida")
    void crearContratoPrestacionValida() {
        assertDoesNotThrow(() -> servicio.crearContrato(
                prestacion("PS-001", LocalDate.of(2025,1,1), LocalDate.of(2025,7,1), 5_000_000.0, 30_000_000.0)));
    }

    @Test
    @DisplayName("crearContrato prestación honorario incorrecto lanza ContratoInvalidoException")
    void crearContratoPrestacionHonorarioIncorrecto() {
        assertThrows(ContratoInvalidoException.class,
                () -> servicio.crearContrato(
                        prestacion("PS-002", LocalDate.of(2025,1,1), LocalDate.of(2025,7,1), 3_000_000.0, 30_000_000.0)));
    }

    @Test
    @DisplayName("crearContrato valor cero lanza IllegalArgumentException")
    void crearContratoValorCero() {
        ContratoObraPublica c = new ContratoObraPublica(
                "OP-003", "Obra", LocalDate.now(), contratante, null,
                0.0, LocalDate.now().plusMonths(3), "Calle 1", 200.0);
        assertThrows(IllegalArgumentException.class, () -> servicio.crearContrato(c));
    }


    @Test
    @DisplayName("buscarContrato existente")
    void buscarContratoExistente() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        assertEquals("OP-001", servicio.buscarContrato("OP-001").getNumeroContrato());
    }

    @Test
    @DisplayName("buscarContrato no existente lanza ContratoNoEncontradoException")
    void buscarContratoNoExiste() {
        assertThrows(ContratoNoEncontradoException.class,
                () -> servicio.buscarContrato("NOEXISTE"));
    }


    @Test
    @DisplayName("obtenerContratos vacío al inicio")
    void obtenerContratosVacio() {
        assertTrue(servicio.obtenerContratos().isEmpty());
    }

    @Test
    @DisplayName("obtenerContratosPorContratante filtra correctamente")
    void obtenerContratosPorContratante() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        assertEquals(1, servicio.obtenerContratosPorContratante("900").size());
    }

    @Test
    @DisplayName("obtenerContratosDisponibles solo PUBLICADO y LICITACION")
    void obtenerContratosDisponibles() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        servicio.crearContrato(obra("OP-002"));
        // Avanzar OP-002 hasta ADJUDICADO (ya no disponible)
        servicio.cambiarEstadoContrato("OP-002", EstadoContrato.LICITACION, "Licitación.");
        servicio.cambiarEstadoContrato("OP-002", EstadoContrato.ADJUDICADO, "Adjudicado.");
        assertEquals(1, servicio.obtenerContratosDisponibles().size());
    }


    @Test
    @DisplayName("actualizarContrato en PUBLICADO exitoso")
    void actualizarContratoExitoso() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        ContratoObraPublica actualizado = obra("OP-001");
        actualizado.setObjetoContrato("Nuevo objeto");
        servicio.actualizarContrato(actualizado);
        assertEquals("Nuevo objeto", servicio.buscarContrato("OP-001").getObjetoContrato());
    }

    @Test
    @DisplayName("actualizarContrato no existente lanza ContratoNoEncontradoException")
    void actualizarContratoNoExiste() {
        assertThrows(ContratoNoEncontradoException.class,
                () -> servicio.actualizarContrato(obra("NOEXISTE")));
    }

    @Test
    @DisplayName("actualizarContrato en LICITACION lanza ActualizacionNoPermitidaException")
    void actualizarContratoNoEnPublicado() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        servicio.cambiarEstadoContrato("OP-001", EstadoContrato.LICITACION, "Inicia licitación.");
        assertThrows(ActualizacionNoPermitidaException.class,
                () -> servicio.actualizarContrato(obra("OP-001")));
    }



    @Test
    @DisplayName("eliminarContrato en PUBLICADO exitoso")
    void eliminarContratoExitoso() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        servicio.eliminarContrato("OP-001");
        assertThrows(ContratoNoEncontradoException.class,
                () -> servicio.buscarContrato("OP-001"));
    }

    @Test
    @DisplayName("eliminarContrato no existente lanza ContratoNoEncontradoException")
    void eliminarContratoNoExiste() {
        assertThrows(ContratoNoEncontradoException.class,
                () -> servicio.eliminarContrato("NOEXISTE"));
    }

    @Test
    @DisplayName("eliminarContrato en ADJUDICADO lanza EliminacionNoPermitidaException")
    void eliminarContratoEnAdjudicado() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        servicio.cambiarEstadoContrato("OP-001", EstadoContrato.LICITACION, "L.");
        servicio.cambiarEstadoContrato("OP-001", EstadoContrato.ADJUDICADO, "A.");
        assertThrows(EliminacionNoPermitidaException.class,
                () -> servicio.eliminarContrato("OP-001"));
    }


    @Test
    @DisplayName("cambiarEstadoContrato PUBLICADO → LICITACION válido")
    void cambiarEstadoValido() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        servicio.cambiarEstadoContrato("OP-001", EstadoContrato.LICITACION, "Inicia.");
        assertEquals(EstadoContrato.LICITACION, servicio.buscarContrato("OP-001").getEstado());
    }

    @Test
    @DisplayName("cambiarEstadoContrato saltar estado lanza TransicionEstadoInvalidaException")
    void cambiarEstadoSaltado() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        assertThrows(TransicionEstadoInvalidaException.class,
                () -> servicio.cambiarEstadoContrato("OP-001", EstadoContrato.ADJUDICADO, "Salto."));
    }

    @Test
    @DisplayName("cambiarEstadoContrato no existente lanza ContratoNoEncontradoException")
    void cambiarEstadoNoExiste() {
        assertThrows(ContratoNoEncontradoException.class,
                () -> servicio.cambiarEstadoContrato("NOEXISTE", EstadoContrato.LICITACION, "X."));
    }

    @Test
    @DisplayName("cambiarEstadoContrato informe vacío lanza IllegalArgumentException")
    void cambiarEstadoInformeVacio() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        assertThrows(IllegalArgumentException.class,
                () -> servicio.cambiarEstadoContrato("OP-001", EstadoContrato.LICITACION, ""));
    }

    @Test
    @DisplayName("cambiarEstadoContrato flujo completo PUBLICADO → FINALIZADO")
    void cambiarEstadoFlujoCompleto() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        servicio.cambiarEstadoContrato("OP-001", EstadoContrato.LICITACION, "L.");
        servicio.cambiarEstadoContrato("OP-001", EstadoContrato.ADJUDICADO, "A.");
        servicio.cambiarEstadoContrato("OP-001", EstadoContrato.EJECUCION,  "E.");
        servicio.cambiarEstadoContrato("OP-001", EstadoContrato.FINALIZADO, "F.");
        assertEquals(EstadoContrato.FINALIZADO, servicio.buscarContrato("OP-001").getEstado());
    }


    @Test
    @DisplayName("seleccionarContrato exitoso")
    void seleccionarContratoExitoso() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        servicio.seleccionarContrato("OP-001", contratista);
        assertEquals(contratista, servicio.buscarContrato("OP-001").getContratista());
    }

    @Test
    @DisplayName("seleccionarContrato no existente lanza ContratoNoEncontradoException")
    void seleccionarContratoNoExiste() {
        assertThrows(ContratoNoEncontradoException.class,
                () -> servicio.seleccionarContrato("NOEXISTE", contratista));
    }

    @Test
    @DisplayName("seleccionarContrato en FINALIZADO lanza ContratoInvalidoException")
    void seleccionarContratoFinalizado() throws Exception {
        servicio.crearContrato(obra("OP-001"));
        servicio.cambiarEstadoContrato("OP-001", EstadoContrato.LICITACION, "L.");
        servicio.cambiarEstadoContrato("OP-001", EstadoContrato.ADJUDICADO, "A.");
        servicio.cambiarEstadoContrato("OP-001", EstadoContrato.EJECUCION,  "E.");
        servicio.cambiarEstadoContrato("OP-001", EstadoContrato.FINALIZADO, "F.");
        assertThrows(ContratoInvalidoException.class,
                () -> servicio.seleccionarContrato("OP-001", contratista));
    }


    private ContratoObraPublica obra(String numero) {
        return new ContratoObraPublica(
                numero, "Construcción parque",
                LocalDate.now(), contratante, null, 50_000_000.0,
                LocalDate.now().plusMonths(6), "Carrera 15 Duitama", 800.0);
    }

    private ContratoCompraVenta compraventa(String numero, double valorUnit, int cantidad, double total) {
        return new ContratoCompraVenta(
                numero, "Compra equipos",LocalDate.now(), contratante, null, total,
                LocalDate.now().plusMonths(3), "Laptop", "Dell", "Inspiron", "XYZ",
                valorUnit, cantidad);
    }

    private ContratoPrestacionServicio prestacion(String numero, LocalDate inicio, LocalDate fin, double honorario,
                                                  double total) {
        return new ContratoPrestacionServicio(
                numero, "Consultoría", inicio, contratante, null, total, fin,
                "Ingeniero", "Informes mensuales", honorario);
    }
}