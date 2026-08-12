package com.ticketbeat.modelo;

import com.ticketbeat.boletos.creaciones.BoletoGeneral;
import com.ticketbeat.interfaces.IBoleto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservaTest {

    @Test
    public void testConstructor_Tipico() {
        Comprador comprador = new Comprador();
        comprador.setNombre("Juan Perez");

        List<IBoleto> boletos = new ArrayList<>();
        boletos.add(new BoletoGeneral());

        Date expiracion = new Date();
        Reserva reserva = new Reserva("RES-001", comprador, boletos, expiracion);

        assertEquals("RES-001", reserva.getId());
        assertSame(comprador, reserva.getComprador());
        assertEquals(1, reserva.getBoletosReservados().size());
        assertEquals(expiracion, reserva.getFechaExpiracion());
    }
}
