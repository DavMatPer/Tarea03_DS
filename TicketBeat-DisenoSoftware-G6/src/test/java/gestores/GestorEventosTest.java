package gestores;

import com.ticketbeat.boletos.creadores.BoletoGeneral;
import com.ticketbeat.gestores.GestorEventos;
import com.ticketbeat.gestores.GestorNotificaciones;
import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.EstadoBoleto;
import com.ticketbeat.modelo.EstadoEvento;
import com.ticketbeat.modelo.Evento;
import com.ticketbeat.politica.PoliticaEventoBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class GestorEventosTest {

    private GestorNotificaciones gestorNotif;
    private GestorEventos gestorEventos;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        gestorNotif = new GestorNotificaciones();
        gestorEventos = new GestorEventos(gestorNotif);
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    @Test
    public void testSolicitarResumenEvento_Tipico() {
        Evento evento = new Evento();
        evento.setBoletosVendidos(100);
        evento.setMontoRecaudado(10000.0);

        gestorEventos.solicitarResumenEvento(evento);

        assertTrue(outContent.toString(StandardCharsets.UTF_8)
                .contains("Resumen: 100 boletos vendidos, Monto: $10000.0"));
    }

    @Test
    public void testSolicitarResumenEvento_Error_EventoNulo() {
        assertThrows(NullPointerException.class,
                () -> gestorEventos.solicitarResumenEvento(null));
    }

    @Test
    public void testConfirmarCancelacion_Tipico() {
        Evento evento = new Evento();
        evento.setPolitica(new PoliticaEventoBase());
        BoletoGeneral vendido = new BoletoGeneral();
        vendido.setEstado(EstadoBoleto.VENDIDO);
        Comprador comprador = new Comprador();
        comprador.setNombre("Juan Perez");
        vendido.setComprador(comprador); // vínculo Boleto-Comprador
        BoletoGeneral disponible = new BoletoGeneral();
        disponible.setEstado(EstadoBoleto.DISPONIBLE);
        evento.agregarBoleto(vendido);
        evento.agregarBoleto(disponible);

        gestorEventos.confirmarCancelacion("Cambio de fecha", evento);

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertEquals(EstadoEvento.CANCELADO, evento.getEstado());
        assertTrue(output.contains("El estado del evento ha cambiado a: CANCELADO"));
        assertTrue(output.contains("Enviando notificación masiva a 1 boleto(s) afectado(s)"));
        assertTrue(output.contains("Notificando a Juan Perez"),
                "NUEVO (vínculo Boleto-Comprador): el comprador vinculado al boleto vendido debe ser notificado por su nombre.");
    }

    @Test
    public void testConfirmarCancelacion_Limite_SinBoletosVendidos() {
        Evento evento = new Evento();
        evento.setPolitica(new PoliticaEventoBase());
        BoletoGeneral disponible = new BoletoGeneral();
        disponible.setEstado(EstadoBoleto.DISPONIBLE);
        evento.agregarBoleto(disponible);

        gestorEventos.confirmarCancelacion("Evento cancelado por el organizador", evento);

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertEquals(EstadoEvento.CANCELADO, evento.getEstado());
        assertTrue(output.contains("No hay boletos afectados; no se envían notificaciones."));
    }

    @Test
    public void testConfirmarCancelacion_Limite_MotivoNulo() {
        Evento evento = new Evento();
        evento.setPolitica(new PoliticaEventoBase());

        assertDoesNotThrow(() -> gestorEventos.confirmarCancelacion(null, evento));
    }

    @Test
    public void testConfirmarCancelacion_Error_PoliticaNula() {
        Evento evento = new Evento(); // sin política asignada

        assertThrows(NullPointerException.class,
                () -> gestorEventos.confirmarCancelacion("motivo", evento));
    }

    @Test
    public void testConfirmarCancelacion_Error_NotificadorNulo() {
        Evento evento = new Evento();
        evento.setPolitica(new PoliticaEventoBase());
        GestorEventos gestorSinNotificador = new GestorEventos(null);

        assertThrows(NullPointerException.class,
                () -> gestorSinNotificador.confirmarCancelacion("motivo", evento));
    }
}
