package estrategia_pago;

import com.ticketbeat.estrategia_pago.PagoTarjetaStrategy;
import com.ticketbeat.modelo.Pago;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class PagoTarjetaStrategyTest {

    @Test
    public void testProcesarPago_Tipico() {
        PagoTarjetaStrategy strategy = new PagoTarjetaStrategy("Visa/Mastercard");
        Map<String, String> datos = new HashMap<>();
        datos.put("numero", "4111111111111111");

        Pago pago = strategy.procesarPago(100.0, datos);

        assertNotNull(pago);
        assertEquals(com.ticketbeat.modelo.EstadoPago.COMPLETADO, pago.getEstado());
    }

    @Test
    public void testProcesarPago_Error_MontoNegativo() {
        PagoTarjetaStrategy strategy = new PagoTarjetaStrategy("Visa/Mastercard");
        Pago pago = strategy.procesarPago(-100.0, new HashMap<>());

        assertEquals(-100.0, pago.getMonto(), "No hay validación de signo del monto.");
        assertEquals(com.ticketbeat.modelo.EstadoPago.COMPLETADO, pago.getEstado(), "El pago se marca COMPLETADO igual, sin validar el monto.");
    }

    @Test
    public void testProcesarPago_Limite_DatosNulos() {
        PagoTarjetaStrategy strategy = new PagoTarjetaStrategy("Visa/Mastercard");
        assertDoesNotThrow(() -> {
            Pago pago = strategy.procesarPago(50.0, null);
            assertEquals(com.ticketbeat.modelo.EstadoPago.COMPLETADO, pago.getEstado());
        });
    }
}
