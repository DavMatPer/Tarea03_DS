package estrategia_pago;

import com.ticketbeat.estrategia_pago.PagoMovilStrategy;
import com.ticketbeat.modelo.Pago;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

public class PagoMovilStrategyTest {

    @Test
    public void testProcesarPago_Tipico() {
        PagoMovilStrategy strategy = new PagoMovilStrategy("PayPhone");
        Pago pago = strategy.procesarPago(75.0, new HashMap<>());

        assertNotNull(pago);
        assertEquals(com.ticketbeat.modelo.EstadoPago.COMPLETADO, pago.getEstado());
    }

    @Test
    public void testProcesarPago_Limite_DatosNulos() {
        PagoMovilStrategy strategy = new PagoMovilStrategy("PayPhone");
        assertDoesNotThrow(() -> {
            Pago pago = strategy.procesarPago(30.0, null);
            assertEquals(com.ticketbeat.modelo.EstadoPago.COMPLETADO, pago.getEstado());
        });
    }
}
