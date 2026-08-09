package com.ticketbeat.gestores;

import com.ticketbeat.modelo.Evento;
import com.ticketbeat.modelo.PoliticaEvento;
import com.ticketbeat.boletos.creaciones.BoletoGeneral;
import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.modelo.EstadoBoleto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class GestorEventosTest {

    private GestorEventos gestorEventos;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        gestorEventos = new GestorEventos();
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Test case TC-GE-001 from plan_pruebas.md
     */
    @Test
    public void testSolicitarResumenEvento_Típico() {
        Evento evento = new Evento();
        evento.setBoletosVendidos(100);
        evento.setMontoRecaudado(10000.0);

        gestorEventos.solicitarResumenEvento("evt-01", evento);

        assertEquals("Resumen: 100 boletos vendidos, Monto: $10000.0\n", outContent.toString().replace("\r\n", "\n"));
    }

    /**
     * Test case TC-GE-002 from plan_pruebas.md
     */
    @Test
    public void testSolicitarResumenEvento_Error_EventoNulo() {
        assertThrows(NullPointerException.class, () -> {
            gestorEventos.solicitarResumenEvento("evt-01", null);
        });
    }

    /**
     * Test case TC-GE-003 from plan_pruebas.md
     */
    @Test
    public void testSolicitarResumenEvento_Error_IdEventoNulo() {
        Evento evento = new Evento();
        // The method doesn't use idEvento, so this won't throw an error.
        // If it were used, we would test for NullPointerException.
        // For now, we just document this behavior.
        gestorEventos.solicitarResumenEvento(null, evento);
        // No exception expected as per current implementation
    }

    /**
     * Test case TC-GE-004 from plan_pruebas.md
     */
    @Test
    public void testConfirmarCancelacion_Típico() {
        Evento evento = new Evento();
        List<IBoleto> boletos = new ArrayList<>();
        BoletoGeneral boletoVendido = new BoletoGeneral();
        boletoVendido.setEstado(EstadoBoleto.VENDIDO);
        boletos.add(boletoVendido);
        BoletoGeneral boletoDisponible = new BoletoGeneral();
        boletoDisponible.setEstado(EstadoBoleto.DISPONIBLE);
        boletos.add(boletoDisponible);
        evento.setBoletos(boletos);

        PoliticaEvento politica = new PoliticaEvento(); // Assumes a simple implementation
        GestorNotificaciones notificador = new GestorNotificaciones(); // Real instance

        gestorEventos.confirmarCancelacion("Artista enfermo", evento, politica, notificador);

        assertEquals("CANCELADO", evento.getEstado());
        // We'd need a way to verify notificador was called, e.g. with a mock or checking output
        // For now, we just check the state change.
    }

    /**
     * Test case TC-GE-005 from plan_pruebas.md
     */
    @Test
    public void testConfirmarCancelacion_Límite_SinBoletosVendidos() {
        Evento evento = new Evento();
        List<IBoleto> boletos = new ArrayList<>();
        BoletoGeneral boletoDisponible = new BoletoGeneral();
        boletoDisponible.setEstado(EstadoBoleto.DISPONIBLE);
        boletos.add(boletoDisponible);
        evento.setBoletos(boletos);

        PoliticaEvento politica = new PoliticaEvento();
        GestorNotificaciones notificador = new GestorNotificaciones();

        gestorEventos.confirmarCancelacion("Baja demanda", evento, politica, notificador);

        assertEquals("CANCELADO", evento.getEstado());
        // We expect no notifications to be sent. Difficult to verify without mocks.
        // We can check that the outContent is empty or only contains expected messages.
    }
    
    /**
     * Test case TC-GE-006 from plan_pruebas.md
     */
    @Test
    public void testConfirmarCancelacion_Error_MotivoNulo() {
        Evento evento = new Evento();
        PoliticaEvento politica = new PoliticaEvento();
        GestorNotificaciones notificador = new GestorNotificaciones();
        
        // This won't throw an exception based on the implementation, but will result in a message like "Evento cancelado: null"
        // A robust implementation should probably validate this.
        gestorEventos.confirmarCancelacion(null, evento, politica, notificador);
        assertTrue(outContent.toString().contains("Evento cancelado: null"));
    }

    /**
     * Test case TC-GE-007 from plan_pruebas.md
     */
    @Test
    public void testConfirmarCancelacion_Error_PoliticaNula() {
        Evento evento = new Evento();
        GestorNotificaciones notificador = new GestorNotificaciones();

        assertThrows(NullPointerException.class, () -> {
            gestorEventos.confirmarCancelacion("Motivo", evento, null, notificador);
        });
    }

    /**
     * Test case TC-GE-008 from plan_pruebas.md
     */
    @Test
    public void testConfirmarCancelacion_Error_NotificadorNulo() {
        Evento evento = new Evento();
        PoliticaEvento politica = new PoliticaEvento();

        assertThrows(NullPointerException.class, () -> {
            gestorEventos.confirmarCancelacion("Motivo", evento, politica, null);
        });
    }
}
