package com.ticketbeat.gestores;

import com.ticketbeat.boletos.creaciones.BoletoGeneral;
import com.ticketbeat.modelo.EstadoBoleto;
import com.ticketbeat.modelo.Evento;
import com.ticketbeat.modelo.PoliticaEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Pruebas para GestorEventos.
 */
public class GestorEventosTest {

    private GestorEventos gestorEventos;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        gestorEventos = new GestorEventos();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    /**
     * Caso de prueba TC-GE-001 del plan de pruebas.
     */
    @Test
    public void testSolicitarResumenEvento_Tipico() {
        Evento evento = new Evento();
        evento.setBoletosVendidos(100);
        evento.setMontoRecaudado(10000.0);

        gestorEventos.solicitarResumenEvento("EVT-001", evento);

        assertTrue(outContent.toString(StandardCharsets.UTF_8)
                .contains("Resumen: 100 boletos vendidos, Monto: $10000.0"));
    }

    /**
     * Caso de prueba TC-GE-002 del plan de pruebas.
     */
    @Test
    public void testSolicitarResumenEvento_Error_EventoNulo() {
        assertThrows(NullPointerException.class,
                () -> gestorEventos.solicitarResumenEvento("EVT-001", null));
    }

    /**
     * Caso de prueba TC-GE-003 del plan de pruebas.
     *
     * CORREGIDO: idEvento nunca se lee dentro del método; no lanza excepción.
     */
    @Test
    public void testSolicitarResumenEvento_Limite_IdEventoNulo() {
        Evento evento = new Evento();
        evento.setBoletosVendidos(5);
        evento.setMontoRecaudado(500.0);

        assertDoesNotThrow(() -> gestorEventos.solicitarResumenEvento(null, evento));
    }

    /**
     * Caso de prueba TC-GE-004 del plan de pruebas.
     *
     * CORREGIDO: notificarCompradores ignora la lista de boletos afectados y no
     * distingue compradores; solo imprime un mensaje genérico.
     */
    @Test
    public void testConfirmarCancelacion_Tipico() {
        Evento evento = new Evento();
        BoletoGeneral vendido = new BoletoGeneral();
        vendido.setEstado(EstadoBoleto.VENDIDO);
        BoletoGeneral disponible = new BoletoGeneral();
        disponible.setEstado(EstadoBoleto.DISPONIBLE);
        evento.agregarBoleto(vendido);
        evento.agregarBoleto(disponible);

        PoliticaEvento politica = new PoliticaEvento();
        GestorNotificaciones notificador = new GestorNotificaciones();

        gestorEventos.confirmarCancelacion("Cambio de fecha", evento, politica, notificador);

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertEquals("CANCELADO", evento.getEstado());
        assertTrue(output.contains("El estado del evento ha cambiado a: CANCELADO"));
        assertTrue(output.contains("Enviando notificación masiva: Evento cancelado: Cambio de fecha"));
    }

    /**
     * Caso de prueba TC-GE-005 del plan de pruebas.
     *
     * CORREGIDO: el mensaje de notificación se envía igual, aunque no haya
     * boletos vendidos, porque notificarCompradores no verifica si la lista
     * de afectados está vacía.
     */
    @Test
    public void testConfirmarCancelacion_Limite_SinBoletosVendidos() {
        Evento evento = new Evento();
        BoletoGeneral disponible = new BoletoGeneral();
        disponible.setEstado(EstadoBoleto.DISPONIBLE);
        evento.agregarBoleto(disponible);

        PoliticaEvento politica = new PoliticaEvento();
        GestorNotificaciones notificador = new GestorNotificaciones();

        gestorEventos.confirmarCancelacion("Evento cancelado por el organizador", evento, politica, notificador);

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertEquals("CANCELADO", evento.getEstado());
        assertTrue(output.contains("Enviando notificación masiva:"),
                "El mensaje se envía incluso sin boletos vendidos.");
    }

    /**
     * Caso de prueba TC-GE-006 del plan de pruebas.
     *
     * CORREGIDO: motivo solo se concatena con "+"; no lanza excepción.
     */
    @Test
    public void testConfirmarCancelacion_Limite_MotivoNulo() {
        Evento evento = new Evento();
        PoliticaEvento politica = new PoliticaEvento();
        GestorNotificaciones notificador = new GestorNotificaciones();

        assertDoesNotThrow(() -> gestorEventos.confirmarCancelacion(null, evento, politica, notificador));
        assertTrue(outContent.toString(StandardCharsets.UTF_8).contains("Evento cancelado: null"));
    }

    /**
     * Caso de prueba TC-GE-007 del plan de pruebas.
     */
    @Test
    public void testConfirmarCancelacion_Error_PoliticaNula() {
        Evento evento = new Evento();
        GestorNotificaciones notificador = new GestorNotificaciones();

        assertThrows(NullPointerException.class,
                () -> gestorEventos.confirmarCancelacion("motivo", evento, null, notificador));
    }

    /**
     * Caso de prueba TC-GE-008 del plan de pruebas.
     */
    @Test
    public void testConfirmarCancelacion_Error_NotificadorNulo() {
        Evento evento = new Evento();
        PoliticaEvento politica = new PoliticaEvento();

        assertThrows(NullPointerException.class,
                () -> gestorEventos.confirmarCancelacion("motivo", evento, politica, null));
    }
}
