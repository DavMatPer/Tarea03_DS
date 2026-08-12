package modelo;

import com.ticketbeat.modelo.NotificacionMensaje;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NotificacionMensajeTest {

    @Test
    public void testConstructor_Tipico() {
        NotificacionMensaje mensaje = new NotificacionMensaje();
        assertNull(mensaje.getContenido());
        assertNull(mensaje.getAsunto());

        mensaje.setAsunto("Confirmación de compra");
        mensaje.setContenido("Su compra fue confirmada");

        assertEquals("Confirmación de compra", mensaje.getAsunto());
        assertEquals("Su compra fue confirmada", mensaje.getContenido());
    }

    @Test
    public void testAccesoresCompletos() {
        long publicAccessors = java.util.Arrays.stream(NotificacionMensaje.class.getDeclaredMethods())
                .filter(m -> java.lang.reflect.Modifier.isPublic(m.getModifiers()))
                .count();
        assertEquals(4, publicAccessors, "Ahora asunto y contenido tienen accesores públicos.");
    }
}
