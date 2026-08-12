package gestores;

import com.ticketbeat.gestores.GestorNotificaciones;
import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.EstadoBoleto;
import com.ticketbeat.servicios.CanalEmail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GestorNotificacionesTest {

    private GestorNotificaciones gestor;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        gestor = new GestorNotificaciones();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    @Test
    public void testIniciarProcesoDeNotificacion_Tipico() {
        Comprador comprador = new Comprador();
        comprador.setNombre("Juan Perez");
        comprador.setCanalPreferido(new CanalEmail());

        List<Comprador> compradores = new ArrayList<>();
        compradores.add(comprador);

        gestor.iniciarProcesoDeNotificacion(compradores, "Su evento fue actualizado");

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("[EMAIL] Enviando correo: Hola, Su evento fue actualizado"));
        assertTrue(output.contains("Estado de entrega registrado."));
    }

    @Test
    public void testIniciarProcesoDeNotificacion_Limite_SinCanalPreferido() {
        Comprador comprador = new Comprador();
        comprador.setNombre("Ana Lopez");
        // canalPreferido queda null

        List<Comprador> compradores = new ArrayList<>();
        compradores.add(comprador);

        gestor.iniciarProcesoDeNotificacion(compradores, "Su evento fue actualizado");

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Canal no disponible, intentar siguiente canal"));
        assertTrue(output.contains("Encolar notificación para reintento"));
    }

    @Test
    public void testIniciarProcesoDeNotificacion_Error_ListaNula() {
        assertThrows(NullPointerException.class,
                () -> gestor.iniciarProcesoDeNotificacion(null, "mensaje"));
    }

    @Test
    public void testSeleccionarCanalDeComunicacion_Limite_CompradorNulo() throws Exception {
        Method metodo = GestorNotificaciones.class.getDeclaredMethod("seleccionarCanalDeComunicacion", Comprador.class);
        metodo.setAccessible(true);
        Object resultado = metodo.invoke(gestor, (Comprador) null);
        assertNull(resultado, "El método valida explícitamente comprador == null.");
    }

    @Test
    public void testSeleccionarCanalDeComunicacion_Tipico() throws Exception {
        Comprador comprador = new Comprador();
        CanalEmail canal = new CanalEmail();
        comprador.setCanalPreferido(canal);

        Method metodo = GestorNotificaciones.class.getDeclaredMethod("seleccionarCanalDeComunicacion", Comprador.class);
        metodo.setAccessible(true);
        Object resultado = metodo.invoke(gestor, comprador);

        assertSame(canal, resultado);
    }

    @Test
    public void testNotificarCompradores_Limite_ListaNula() {
        assertDoesNotThrow(() -> gestor.notificarCompradores(null, "Evento cancelado"));
        assertTrue(outContent.toString(StandardCharsets.UTF_8)
                .contains("No hay boletos afectados; no se envían notificaciones."));
    }

    @Test
    public void testNotificarCompradores_Tipico_IteraSobreLosBoletosSinComprador() {
        List<IBoleto> boletos = new ArrayList<>();
        boletos.add(new BoletoFalso());
        boletos.add(new BoletoFalso());
        boletos.add(new BoletoFalso());

        gestor.notificarCompradores(boletos, "Evento cancelado");

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Enviando notificación masiva a 3 boleto(s) afectado(s)"));
        long lineasPorBoleto = output.lines().filter(l -> l.contains("Boleto sin comprador identificado")).count();
        assertEquals(3, lineasPorBoleto, "Ahora sí se recorre e informa cada boleto de la lista.");
    }

    @Test
    public void testNotificarCompradores_Tipico_NotificaAlCompradorReal() {
        Comprador comprador = new Comprador();
        comprador.setNombre("Maria Gomez");
        comprador.setCanalPreferido(new CanalEmail());

        BoletoFalso boleto = new BoletoFalso();
        boleto.setComprador(comprador);

        List<IBoleto> boletos = new ArrayList<>();
        boletos.add(boleto);

        gestor.notificarCompradores(boletos, "Evento cancelado");

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Notificando a Maria Gomez"));
        assertTrue(output.contains("[EMAIL] Enviando correo: Evento cancelado"));
        assertFalse(output.contains("sin comprador identificado"));
    }

    @Test
    public void testNotificarResolucion_Limite_CompradorNulo() {
        assertDoesNotThrow(() -> gestor.notificarResolucion(null, "Su incidente fue resuelto"));
    }

    @Test
    public void testNotificarResolucion_Tipico_UsaComprador() {
        Comprador comprador = new Comprador();
        comprador.setNombre("Juan Perez");
        comprador.setCanalPreferido(new CanalEmail());

        gestor.notificarResolucion(comprador, "Su incidente fue resuelto");

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Notificando resolución a Juan Perez"));
        assertTrue(output.contains("[EMAIL] Enviando correo: Su incidente fue resuelto"));
    }

    private static class BoletoFalso implements IBoleto {
        private Comprador comprador;
        @Override public void mostrarDetalles() { }
        @Override public EstadoBoleto getEstado() { return EstadoBoleto.VENDIDO; }
        @Override public void setEstado(EstadoBoleto estado) { }
        @Override public double getPrecio() { return 0; }
        @Override public Comprador getComprador() { return comprador; }
        @Override public void setComprador(Comprador comprador) { this.comprador = comprador; }
    }
}
