package com.ticketbeat.modelo;

import com.ticketbeat.boletos.creaciones.BoletoGeneral;
import com.ticketbeat.boletos.creaciones.BoletoReservado;
import com.ticketbeat.boletos.creaciones.BoletoVIP;
import com.ticketbeat.interfaces.IBoleto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Pruebas para la clase PoliticaEvento.
 */
public class PoliticaEventoTest {

    /**
     * Caso de prueba TC-PE-001 del plan de pruebas.
     */
    @Test
    public void testAplicarPoliticaDevolucion_Tipico() {
        Evento evento = new Evento();
        BoletoGeneral vendido = new BoletoGeneral();
        vendido.setEstado(EstadoBoleto.VENDIDO);
        BoletoReservado reservado = new BoletoReservado();
        reservado.setEstado(EstadoBoleto.RESERVADO);
        BoletoVIP disponible = new BoletoVIP(); // queda DISPONIBLE por defecto

        evento.agregarBoleto(vendido);
        evento.agregarBoleto(reservado);
        evento.agregarBoleto(disponible);

        PoliticaEvento politica = new PoliticaEvento();
        List<IBoleto> afectados = politica.aplicarPoliticaDevolucion(evento);

        assertEquals(2, afectados.size());
        assertTrue(afectados.contains(vendido));
        assertTrue(afectados.contains(reservado));
        assertFalse(afectados.contains(disponible));
    }

    /**
     * Caso de prueba TC-PE-002 del plan de pruebas.
     */
    @Test
    public void testAplicarPoliticaDevolucion_Limite_SinBoletosElegibles() {
        Evento evento = new Evento(); // sin boletos
        PoliticaEvento politica = new PoliticaEvento();
        List<IBoleto> afectados = politica.aplicarPoliticaDevolucion(evento);
        assertTrue(afectados.isEmpty());
    }
}
