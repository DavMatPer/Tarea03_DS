package estrategia_pago;

import com.ticketbeat.estrategia_pago.PagoTransferenciaStrategy;
import com.ticketbeat.modelo.Pago;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

public class PagoTransferenciaStrategyTest {

    @Test
    public void testProcesarPago_Tipico() {
        PagoTransferenciaStrategy strategy = new PagoTransferenciaStrategy("Banco Pichincha");
        Pago pago = strategy.procesarPago(200.0, new HashMap<>());

        assertNotNull(pago);
        assertEquals(com.ticketbeat.modelo.EstadoPago.COMPLETADO, pago.getEstado());
    }

    @Test
    public void testRevertirPago_Tipico() {
        PagoTransferenciaStrategy strategy = new PagoTransferenciaStrategy("Banco Pichincha");
        assertTrue(strategy.revertirPago("ID-QUE-NO-EXISTE"));
    }

    @Test
    public void testProcesarPago_Limite_DatosNulos() {
        PagoTransferenciaStrategy strategy = new PagoTransferenciaStrategy("Banco Pichincha");
        assertDoesNotThrow(() -> {
            Pago pago = strategy.procesarPago(20.0, null);
            assertEquals(com.ticketbeat.modelo.EstadoPago.COMPLETADO, pago.getEstado());
        });
    }
}
