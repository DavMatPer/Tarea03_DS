package modelo;

import com.ticketbeat.modelo.Comprador;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompradorTest {

    @Test
    public void testSetEdad_Error_EdadNegativa() {
        Comprador comprador = new Comprador();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> comprador.setEdad(-5));
        assertTrue(ex.getMessage().contains("-5"));
    }

    @Test
    public void testSetEdad_Tipico() {
        Comprador comprador = new Comprador();
        comprador.setEdad(25);
        assertEquals(25, comprador.getEdad());
    }
}
