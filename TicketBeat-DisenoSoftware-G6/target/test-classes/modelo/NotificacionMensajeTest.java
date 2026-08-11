package com.ticketbeat.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase NotificacionMensaje.
 */
public class NotificacionMensajeTest {

    /**
     * Caso de prueba TC-N-001 del plan de pruebas.
     */
    @Test
    public void testConstructor_Error_SinInicializar() {
        NotificacionMensaje mensaje = new NotificacionMensaje();
        assertNull(mensaje.getContenido());

        mensaje.setContenido("Su compra fue confirmada");
        assertEquals("Su compra fue confirmada", mensaje.getContenido());

        long publicAccessors = java.util.Arrays.stream(NotificacionMensaje.class.getDeclaredMethods())
                .filter(m -> java.lang.reflect.Modifier.isPublic(m.getModifiers()))
                .count();
        assertEquals(2, publicAccessors, "Solo getContenido()/setContenido() son públicos; 'asunto' no tiene accesor.");
    }
}
