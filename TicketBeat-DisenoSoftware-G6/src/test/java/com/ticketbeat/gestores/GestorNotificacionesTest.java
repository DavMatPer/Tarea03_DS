package com.ticketbeat.gestores;

import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.EstadoBoleto;
import com.ticketbeat.servicios.CanalEmail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Pruebas para GestorNotificaciones.
 */
public class GestorNotificacionesTest {

    private GestorNotificaciones gestor;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        gestor = new GestorNotificaciones();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    /**
     * Caso de prueba TC-GN-001 del plan de pruebas.
     */
    @Test
    public void testIniciarProcesoDeNotificacion_Tipico() {
        Comprador comprador = new Comprador();
        comprador.setNombre("Juan Perez");
        comprador.setCanalPreferido(new CanalEmail());

        List<Comprador> compradores = new ArrayList<>();
        compradores.add(comprador);

        gestor.iniciarProcesoDeNotificacion(compradores, "Su evento fue actualizado");

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("[EMAIL] Enviando correo: Hola, Su evento fue actualizado"));
        assertTrue(output.contains("Estado de entrega registrado."));
    }

    /**
     * Caso de prueba TC-GN-002 del plan de pruebas.
     *
     * CORREGIDO: no existe una lógica real de canal secundario; solo se
     * imprimen mensajes informativos.
     */
    @Test
    public void testIniciarProcesoDeNotificacion_Limite_SinCanalPreferido() {
        Comprador comprador = new Comprador();
        comprador.setNombre("Ana Lopez");
        // canalPreferido queda null

        List<Comprador> compradores = new ArrayList<>();
        compradores.add(comprador);

        gestor.iniciarProcesoDeNotificacion(compradores, "Su evento fue actualizado");

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Canal no disponible, intentar siguiente canal"));
        assertTrue(output.contains("Encolar notificación para reintento"));
    }

    /**
     * Caso de prueba TC-GN-003 del plan de pruebas.
     */
    @Test
    public void testIniciarProcesoDeNotificacion_Error_ListaNula() {
        assertThrows(NullPointerException.class,
                () -> gestor.iniciarProcesoDeNotificacion(null, "mensaje"));
    }

    /**
     * Caso de prueba TC-GN-004 del plan de pruebas.
     *
     * seleccionarCanalDeComunicacion es privado; se invoca vía reflection.
     */
    @Test
    public void testSeleccionarCanalDeComunicacion_Limite_CompradorNulo() throws Exception {
        Method metodo = GestorNotificaciones.class.getDeclaredMethod("seleccionarCanalDeComunicacion", Comprador.class);
        metodo.setAccessible(true);
        Object resultado = metodo.invoke(gestor, (Comprador) null);
        assertNull(resultado, "El método valida explícitamente comprador == null.");
    }

    /**
     * Caso de prueba TC-GN-005 del plan de pruebas.
     *
     * CORREGIDO: el método está correctamente implementado (delega al getter);
     * se reclasifica como caso Típico.
     */
    @Test
    public void testSeleccionarCanalDeComunicacion_Tipico() throws Exception {
        Comprador comprador = new Comprador();
        CanalEmail canal = new CanalEmail();
        comprador.setCanalPreferido(canal);

        Method metodo = GestorNotificaciones.class.getDeclaredMethod("seleccionarCanalDeComunicacion", Comprador.class);
        metodo.setAccessible(true);
        Object resultado = metodo.invoke(gestor, comprador);

        assertSame(canal, resultado);
    }

    /**
     * Caso de prueba TC-GN-006 del plan de pruebas.
     */
    @Test
    public void testNotificarCompradores_Limite_ListaNula() {
        assertDoesNotThrow(() -> gestor.notificarCompradores(null, "Evento cancelado"));
    }

    /**
     * Caso de prueba TC-GN-007 del plan de pruebas.
     *
     * CORREGIDO: el método (stub) ignora por completo la lista recibida; no hay
     * iteración sobre los datos.
     */
    @Test
    public void testNotificarCompradores_Limite_SinIteracion() {
        List<IBoleto> boletos = new ArrayList<>();
        boletos.add(new BoletoFalso());
        boletos.add(new BoletoFalso());
        boletos.add(new BoletoFalso());

        gestor.notificarCompradores(boletos, "Evento cancelado");

        String output = outContent.toString(StandardCharsets.UTF_8);
        long lineasDeNotificacion = output.lines().filter(l -> l.contains("Enviando notificaci")).count();
        assertEquals(1, lineasDeNotificacion, "Solo se imprime un mensaje genérico, sin recorrer la lista.");
    }

    /**
     * Caso de prueba TC-GN-008 del plan de pruebas.
     */
    @Test
    public void testNotificarResolucion_Limite_CompradorNulo() {
        assertDoesNotThrow(() -> gestor.notificarResolucion(null, "Su incidente fue resuelto"));
    }

    /** Implementación mínima de IBoleto usada solo para poblar listas en las pruebas. */
    private static class BoletoFalso implements IBoleto {
        @Override public void mostrarDetalles() { }
        @Override public EstadoBoleto getEstado() { return EstadoBoleto.VENDIDO; }
        @Override public double getPrecio() { return 0; }
    }
}
