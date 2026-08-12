package gestores;

import com.ticketbeat.boletos.creadores.BoletoGeneral;
import com.ticketbeat.estrategia_pago.PagoTarjetaStrategy;
import com.ticketbeat.gestores.GestorReservas;
import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.EstadoBoleto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GestorReservasTest {

    private GestorReservas gestor;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        gestor = new GestorReservas();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    @Test
    public void testElegirCantidadYTipoDeEntrada_Tipico() {
        IBoleto boleto = new BoletoGeneral();
        List<IBoleto> seleccionados = new ArrayList<>();
        seleccionados.add(boleto);
        gestor.setBoletosSeleccionados(seleccionados);

        gestor.elegirCantidadYTipoDeEntrada();

        assertEquals(EstadoBoleto.RESERVADO, boleto.getEstado());
        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Reserva "));
        assertTrue(output.contains("Temporizador de expiración de reserva iniciado"));
    }

    @Test
    public void testElegirCantidadYTipoDeEntrada_Limite_SinBoletosSeleccionados() {
        // no se llama setBoletosSeleccionados(): la lista queda vacía
        gestor.elegirCantidadYTipoDeEntrada();

        assertTrue(outContent.toString(StandardCharsets.UTF_8)
                .contains("Informar entradas insuficientes"));
    }

    @Test
    public void testConfirmarCompra_Tipico() {
        IBoleto boleto = new BoletoGeneral();
        List<IBoleto> seleccionados = new ArrayList<>();
        seleccionados.add(boleto);
        Comprador comprador = new Comprador();
        comprador.setNombre("Juan Perez");
        gestor.setComprador(comprador);
        gestor.setBoletosSeleccionados(seleccionados);
        gestor.elegirCantidadYTipoDeEntrada(); // deja el boleto en RESERVADO

        gestor.setEstrategiaPago(new PagoTarjetaStrategy("Visa/Mastercard"));
        gestor.confirmarCompra(150.0, new HashMap<>());

        assertEquals(EstadoBoleto.VENDIDO, boleto.getEstado());
        assertSame(comprador, boleto.getComprador(),
                "NUEVO (vínculo Boleto-Comprador): al vender el boleto, marcarEntradasComoVendidas() debe vincularlo al comprador.");
        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Confirmación de pago (ID:"));
        assertTrue(output.contains("1 boleto(s) digital(es) generado(s)."));
        assertTrue(output.contains("Confirmación de compra y boletos"));
    }

    @Test
    public void testConfirmarCompra_Error_EstrategiaNula() {
        gestor.confirmarCompra(100.0, new HashMap<>());

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Error: No se ha seleccionado una estrategia de pago."));
    }

    @Test
    public void testConfirmarCompra_Limite_MontoCero() {
        gestor.setEstrategiaPago(new PagoTarjetaStrategy("Visa/Mastercard"));

        gestor.confirmarCompra(0.0, new HashMap<>());

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Confirmación de compra y boletos"),
                "El pago se procesa igual como COMPLETADO, sin validar el monto.");
    }

    @Test
    public void testTiempoDeReservaExpirado_Tipico() {
        IBoleto boleto = new BoletoGeneral();
        List<IBoleto> seleccionados = new ArrayList<>();
        seleccionados.add(boleto);
        gestor.setBoletosSeleccionados(seleccionados);
        gestor.elegirCantidadYTipoDeEntrada(); // deja el boleto en RESERVADO

        gestor.tiempoDeReservaExpirado();

        assertEquals(EstadoBoleto.DISPONIBLE, boleto.getEstado());
        assertTrue(outContent.toString(StandardCharsets.UTF_8)
                .contains("Informar expiración de reserva"));
    }
}
