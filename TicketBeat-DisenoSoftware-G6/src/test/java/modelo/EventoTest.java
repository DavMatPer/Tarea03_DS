package modelo;

import com.ticketbeat.boletos.creadores.BoletoGeneral;
import com.ticketbeat.boletos.creadores.BoletoReservado;
import com.ticketbeat.boletos.creadores.BoletoVIP;
import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.modelo.EstadoBoleto;
import com.ticketbeat.modelo.EstadoEvento;
import com.ticketbeat.modelo.Evento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class EventoTest {

    @Test
    public void testAgregarBoleto_Tipico() {
        Evento evento = new Evento();
        IBoleto boleto = new BoletoGeneral();
        evento.agregarBoleto(boleto);
        assertEquals(1, evento.getBoletos().size());
        assertSame(boleto, evento.getBoletos().get(0));
    }

    @Test
    public void testAgregarBoleto_Error_BoletoNulo() {
        Evento evento = new Evento();
        assertDoesNotThrow(() -> evento.agregarBoleto(null));
        assertEquals(1, evento.getBoletos().size());
        assertNull(evento.getBoletos().get(0));
    }

    @Test
    public void testObtenerBoletosParaDevolucion_Tipico() {
        Evento evento = new Evento();
        BoletoGeneral vendido = new BoletoGeneral();
        vendido.setEstado(EstadoBoleto.VENDIDO);
        BoletoReservado reservado = new BoletoReservado();
        reservado.setEstado(EstadoBoleto.RESERVADO);
        BoletoVIP disponible = new BoletoVIP(); // queda DISPONIBLE por defecto

        evento.agregarBoleto(vendido);
        evento.agregarBoleto(reservado);
        evento.agregarBoleto(disponible);

        List<IBoleto> afectados = evento.obtenerBoletosParaDevolucion();

        assertEquals(2, afectados.size());
        assertTrue(afectados.contains(vendido));
        assertTrue(afectados.contains(reservado));
        assertFalse(afectados.contains(disponible));
    }

    @Test
    public void testObtenerBoletosParaDevolucion_Limite_SinBoletosElegibles() {
        Evento evento = new Evento(); // sin boletos
        assertTrue(evento.obtenerBoletosParaDevolucion().isEmpty());
    }

    @Test
    public void testEstadoPorDefecto_Tipico() {
        Evento evento = new Evento();
        assertEquals(EstadoEvento.ACTIVO, evento.getEstado());

        evento.setEstado(EstadoEvento.CANCELADO);
        assertEquals(EstadoEvento.CANCELADO, evento.getEstado());
    }
}

