package edu.uptc.servicios;

import edu.uptc.dominio.*;
import edu.uptc.enums.EstadoContrato;
import edu.uptc.servicios.ServicioContrato;
import edu.uptc.servicios.ServicioReporte;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link ServicioReporte}.
 *
 * @author Alejandra Cuellar, Laura González
 * @version 1.0
 */
class ServicioReporteTest {

    private ServicioReporte servicio;
    private ServicioContrato svcContrato;
    private Contratante contratante;

    @BeforeEach
    void setUp() {
        servicio = new ServicioReporte();
        svcContrato = new ServicioContrato(servicio);

        contratante = new Contratante(
                "jurídica", "NIT", "900", "Entidad",
                "e@test.com", "p", "3", "Calle", "Bogotá",
                "Salud", "municipal", "ENT-001");
    }


    @Test
    @DisplayName("obtenerTodosLosReportes vacío al inicio")
    void reportesVaciosAlInicio() {
        assertTrue(servicio.obtenerTodosLosReportes().isEmpty());
    }

    @Test
    @DisplayName("obtenerTodosLosReportes contiene reporte tras cambio de estado")
    void reporteGeneradoTrasCambioEstado() throws Exception {
        svcContrato.crearContrato(obra("OP-001"));
        svcContrato.cambiarEstadoContrato("OP-001", EstadoContrato.LICITACION, "Inicia licitación.");
        assertEquals(1, servicio.obtenerTodosLosReportes().size());
    }

    @Test
    @DisplayName("obtenerTodosLosReportes acumula reportes de varios cambios")
    void reportesAcumulados() throws Exception {
        svcContrato.crearContrato(obra("OP-001"));
        svcContrato.cambiarEstadoContrato("OP-001", EstadoContrato.LICITACION, "L.");
        svcContrato.cambiarEstadoContrato("OP-001", EstadoContrato.ADJUDICADO, "A.");
        assertEquals(2, servicio.obtenerTodosLosReportes().size());
    }


    @Test
    @DisplayName("obtenerReportesPorContrato contrato sin cambios retorna lista vacía")
    void reportesPorContratoSinCambios() throws Exception {
        svcContrato.crearContrato(obra("OP-001"));
        assertTrue(servicio.obtenerReportesPorContrato("OP-001").isEmpty());
    }

    @Test
    @DisplayName("obtenerReportesPorContrato filtra por contrato correctamente")
    void reportesPorContratoFiltra() throws Exception {
        svcContrato.crearContrato(obra("OP-001"));
        svcContrato.crearContrato(obra("OP-002"));
        svcContrato.cambiarEstadoContrato("OP-001", EstadoContrato.LICITACION, "L1.");
        svcContrato.cambiarEstadoContrato("OP-002", EstadoContrato.LICITACION, "L2.");
        svcContrato.cambiarEstadoContrato("OP-002", EstadoContrato.ADJUDICADO, "A2.");

        assertEquals(1, servicio.obtenerReportesPorContrato("OP-001").size());
        assertEquals(2, servicio.obtenerReportesPorContrato("OP-002").size());
    }

    @Test
    @DisplayName("obtenerReportesPorContrato flujo completo genera 4 reportes")
    void reportesFlujoCompleto() throws Exception {
        svcContrato.crearContrato(obra("OP-001"));
        svcContrato.cambiarEstadoContrato("OP-001", EstadoContrato.LICITACION, "L.");
        svcContrato.cambiarEstadoContrato("OP-001", EstadoContrato.ADJUDICADO, "A.");
        svcContrato.cambiarEstadoContrato("OP-001", EstadoContrato.EJECUCION,  "E.");
        svcContrato.cambiarEstadoContrato("OP-001", EstadoContrato.FINALIZADO, "F.");

        List<ReporteInterventoria> lista = servicio.obtenerReportesPorContrato("OP-001");
        assertEquals(4, lista.size());
    }

    @Test
    @DisplayName("reporte tiene el contrato e informe correctos")
    void reporteContenidoCorrecto() throws Exception {
        svcContrato.crearContrato(obra("OP-001"));
        svcContrato.cambiarEstadoContrato("OP-001", EstadoContrato.LICITACION, "Informe de prueba.");

        ReporteInterventoria r = servicio.obtenerReportesPorContrato("OP-001").get(0);
        assertEquals("OP-001", r.getContrato().getNumeroContrato());
        assertEquals("Informe de prueba.", r.getInforme());
        assertNotNull(r.getFechaHora());
    }


    private ContratoObraPublica obra(String numero) {
        return new ContratoObraPublica(numero, "Obra de prueba", LocalDate.now(), contratante,
                null, 20_000_000.0, LocalDate.now().plusMonths(4),
                "Calle 10 Duitama", 300.0);
    }
}