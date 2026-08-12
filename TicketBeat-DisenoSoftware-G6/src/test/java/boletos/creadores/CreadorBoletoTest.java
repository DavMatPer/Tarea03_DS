    package boletos.creadores;

    import com.ticketbeat.boletos.creadores.BoletoGeneral;
    import com.ticketbeat.boletos.creadores.BoletoReservado;
    import com.ticketbeat.boletos.creadores.BoletoVIP;
import com.ticketbeat.boletos.creadores.CreadorBoleto;
import com.ticketbeat.boletos.creadores.CreadorBoletoGeneral;
import com.ticketbeat.boletos.creadores.CreadorBoletoReservado;
import com.ticketbeat.boletos.creadores.CreadorBoletoVIP;
    import com.ticketbeat.interfaces.IBoleto;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import static org.junit.jupiter.api.Assertions.*;

    import java.io.ByteArrayOutputStream;
    import java.io.PrintStream;

    public class CreadorBoletoTest {
        
        private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        @BeforeEach
        public void setUp() {
            System.setOut(new PrintStream(outContent));
        }

        @Test
        public void testCreadorBoletoGeneral_CreaBoletoCorrecto() {
            CreadorBoleto creador = new CreadorBoletoGeneral();
            IBoleto boleto = creador.crearBoleto();
            assertTrue(boleto instanceof BoletoGeneral, "El creador debe instanciar un BoletoGeneral.");
        }

        @Test
        public void testCreadorBoletoReservado_CreaBoletoCorrecto() {
            CreadorBoleto creador = new CreadorBoletoReservado();
            IBoleto boleto = creador.crearBoleto();
            assertTrue(boleto instanceof BoletoReservado, "El creador debe instanciar un BoletoReservado.");
        }

        @Test
        public void testCreadorBoletoVIP_CreaBoletoCorrecto() {
            CreadorBoleto creador = new CreadorBoletoVIP();
            IBoleto boleto = creador.crearBoleto();
            assertTrue(boleto instanceof BoletoVIP, "El creador debe instanciar un BoletoVIP.");
        }

        @Test
        public void testProcesarEmision_TemplateMethod() {
            // Use CreadorBoletoGeneral as the concrete implementation for the test
            CreadorBoleto creador = new CreadorBoletoGeneral();
            creador.procesarEmision();

            String output = outContent.toString();
            assertTrue(output.contains("=== BOLETO GENERAL ==="));
            assertTrue(output.contains("Precio: $100.0"));
        }
    }
