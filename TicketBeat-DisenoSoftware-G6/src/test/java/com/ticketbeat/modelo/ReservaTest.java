package com.ticketbeat.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase Reserva (POJO).
 */
public class ReservaTest {

    /**
     * Caso de prueba TC-R-001 del plan de pruebas.
     */
    @Test
    public void testConstructorPorDefecto_Error_AtributosSinInicializar() {
        Reserva reserva = new Reserva();
        assertNull(reserva.getComprador(), "El único atributo accesible (comprador) inicia en null.");
    }

    /**
     * Caso de prueba TC-R-002 del plan de pruebas.
     *
     * Documenta, vía reflection, que la clase Reserva solo expone getComprador():
     * id, boletosReservados y fechaExpiracion no tienen getters públicos.
     */
    @Test
    public void testGetters_Error_AtributosInaccesibles() {
        long publicGetters = java.util.Arrays.stream(Reserva.class.getDeclaredMethods())
                .filter(m -> java.lang.reflect.Modifier.isPublic(m.getModifiers()))
                .filter(m -> m.getName().startsWith("get"))
                .count();
        assertEquals(1, publicGetters,
                "Solo getComprador() es público; id, boletosReservados y fechaExpiracion no tienen accesor.");
    }
}
