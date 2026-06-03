package edu.uptc.servicios;

import edu.uptc.dominio.*;
import edu.uptc.excepciones.*;
import edu.uptc.servicios.ServicioUsuario;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link ServicioUsuario}.
 *
 * @author Alejandra Cuellar, Laura González
 * @version 1.0
 */
class ServicioUsuarioTest {

    private ServicioUsuario servicio;
    private Contratante contratante;
    private Contratista contratista;

    @BeforeEach
    void setUp() {
        servicio = new ServicioUsuario();

        contratante = new Contratante(
                "natural", "CC", "111", "Ana Torres",
                "ana@test.com", "pass1", "300", "Calle 1", "Bogotá",
                "Salud", "municipal", "ENT-001");

        contratista = new Contratista(
                "natural", "CC", "222", "Luis García",
                "luis@test.com", "pass2", "301", "Calle 2", "Cali",
                false, "Ingeniería");
    }


    @Test
    @DisplayName("autenticar admin por defecto")
    void autenticarAdminExitoso() throws Exception {
        Usuario u = servicio.autenticar("admin@secop.gov.co", "admin123");
        assertInstanceOf(Administrador.class, u);
    }

    @Test
    @DisplayName("autenticar credenciales incorrectas lanza excepción")
    void autenticarCredencialesInvalidas() {
        assertThrows(CredencialesInvalidasException.class, () -> servicio.autenticar("mal@x.com", "mal"));
    }

    @Test
    @DisplayName("autenticar contratante registrado")
    void autenticarContratante() throws Exception {
        servicio.registrarContratante(contratante);
        assertInstanceOf(Contratante.class, servicio.autenticar("ana@test.com", "pass1"));
    }

    @Test
    @DisplayName("autenticar contratista registrado")
    void autenticarContratista() throws Exception {
        servicio.registrarContratista(contratista);
        assertInstanceOf(Contratista.class, servicio.autenticar("luis@test.com", "pass2"));
    }


    @Test
    @DisplayName("registrarContratante exitoso")
    void registrarContratanteExitoso() {
        assertDoesNotThrow(() -> servicio.registrarContratante(contratante));
    }

    @Test
    @DisplayName("registrarContratante correo duplicado lanza excepción")
    void registrarContratanteCorreoDuplicado() throws Exception {
        servicio.registrarContratante(contratante);
        Contratante dup = new Contratante(
                "natural", "CC", "999", "Otro",
                "ana@test.com", "x", "3", "Dir", "Ciudad",
                "Salud", "dep", "ENT-999");
        assertThrows(UsuarioYaExisteException.class, () -> servicio.registrarContratante(dup));
    }

    @Test
    @DisplayName("registrarContratante campo vacío lanza excepción")
    void registrarContratanteCampoVacio() {
        Contratante malo = new Contratante(
                "natural", "CC", "111", "",
                "otro@test.com", "x", "3", "Dir", "Ciudad",
                "Salud", "dep", "ENT-002");
        assertThrows(IllegalArgumentException.class,
                () -> servicio.registrarContratante(malo));
    }



    @Test
    @DisplayName("buscarContratante existente")
    void buscarContratanteExistente() throws Exception {
        servicio.registrarContratante(contratante);
        assertEquals("Ana Torres", servicio.buscarContratante("111").getNombre());
    }

    @Test
    @DisplayName("buscarContratante no existente lanza excepción")
    void buscarContratanteNoExiste() {
        assertThrows(UsuarioNoEncontradoException.class, () -> servicio.buscarContratante("000"));
    }


    @Test
    @DisplayName("actualizarContratante exitoso")
    void actualizarContratanteExitoso() throws Exception {
        servicio.registrarContratante(contratante);
        contratante.setNombre("Nuevo Nombre");
        servicio.actualizarContratante(contratante);
        assertEquals("Nuevo Nombre", servicio.buscarContratante("111").getNombre());
    }

    @Test
    @DisplayName("actualizarContratante no existente lanza excepción")
    void actualizarContratanteNoExiste() {
        assertThrows(UsuarioNoEncontradoException.class,
                () -> servicio.actualizarContratante(contratante));
    }



    @Test
    @DisplayName("eliminarContratante exitoso")
    void eliminarContratanteExitoso() throws Exception {
        servicio.registrarContratante(contratante);
        servicio.eliminarContratante("111");
        assertThrows(UsuarioNoEncontradoException.class,
                () -> servicio.buscarContratante("111"));
    }

    @Test
    @DisplayName("eliminarContratante no existente lanza excepción")
    void eliminarContratanteNoExiste() {
        assertThrows(UsuarioNoEncontradoException.class,
                () -> servicio.eliminarContratante("000"));
    }


    @Test
    @DisplayName("registrarContratista exitoso")
    void registrarContratistaExitoso() {
        assertDoesNotThrow(() -> servicio.registrarContratista(contratista));
    }

    @Test
    @DisplayName("registrarContratista correo duplicado lanza excepción")
    void registrarContratistaCorreoDuplicado() throws Exception {
        servicio.registrarContratista(contratista);
        Contratista dup = new Contratista(
                "natural", "CC", "888", "Otro",
                "luis@test.com", "x", "3", "Dir", "Ciudad",
                false, "Área");
        assertThrows(UsuarioYaExisteException.class,
                () -> servicio.registrarContratista(dup));
    }


    @Test
    @DisplayName("buscarContratista existente")
    void buscarContratistaExistente() throws Exception {
        servicio.registrarContratista(contratista);
        assertEquals("Luis García", servicio.buscarContratista("222").getNombre());
    }

    @Test
    @DisplayName("buscarContratista no existente lanza excepción")
    void buscarContratistaNoExiste() {
        assertThrows(UsuarioNoEncontradoException.class,
                () -> servicio.buscarContratista("000"));
    }



    @Test
    @DisplayName("actualizarContratista exitoso")
    void actualizarContratistaExitoso() throws Exception {
        servicio.registrarContratista(contratista);
        contratista.setAreaDesempenio("Nuevo Área");
        servicio.actualizarContratista(contratista);
        assertEquals("Nuevo Área", servicio.buscarContratista("222").getAreaDesempenio());
    }

    @Test
    @DisplayName("actualizarContratista no existente lanza excepción")
    void actualizarContratistaNoExiste() {
        assertThrows(UsuarioNoEncontradoException.class, () -> servicio.actualizarContratista(contratista));
    }



    @Test
    @DisplayName("eliminarContratista exitoso")
    void eliminarContratistaExitoso() throws Exception {
        servicio.registrarContratista(contratista);
        servicio.eliminarContratista("222");
        assertThrows(UsuarioNoEncontradoException.class, () -> servicio.buscarContratista("222"));
    }

    @Test
    @DisplayName("eliminarContratista no existente lanza excepción")
    void eliminarContratistaNoExiste() {
        assertThrows(UsuarioNoEncontradoException.class,
                () -> servicio.eliminarContratista("000"));
    }


    @Test
    @DisplayName("obtenerContratantes vacío al inicio")
    void obtenerContratantesVacio() {
        assertTrue(servicio.obtenerContratantes().isEmpty());
    }

    @Test
    @DisplayName("obtenerContratistas vacío al inicio")
    void obtenerContratistasVacio() {
        assertTrue(servicio.obtenerContratistas().isEmpty());
    }
}