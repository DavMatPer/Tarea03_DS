package manejador_incidente;

import com.ticketbeat.manejador_incidente.AgenteSoporte;
import com.ticketbeat.manejador_incidente.ManejadorIncidente;
import com.ticketbeat.modelo.EstadoIncidente;
import com.ticketbeat.modelo.Incidente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class AgenteSoporteTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    @Test
    public void testPuedeResolver_Tipico_ProblemaSimple() {
        AgenteSoporte agente = new AgenteSoporte();
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Tengo un problema simple con mi boleto");

        assertTrue(agente.puedeResolver(incidente));
    }

    @Test
    public void testManejarIncidente_Tipico_MarcaResuelto() {
        AgenteSoporte agente = new AgenteSoporte();
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Problema simple de acceso");

        agente.manejarIncidente(incidente);

        assertEquals(EstadoIncidente.RESUELTO, incidente.getEstado());
    }

    @Test
    public void testPuedeResolver_Tipico_ProblemaComplejo() {
        AgenteSoporte agente = new AgenteSoporte();
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Tengo un problema COMPLEJO de fraude");

        assertFalse(agente.puedeResolver(incidente));
    }

    @Test
    public void testManejarIncidente_Tipico_PasaAlSiguiente() {
        AgenteSoporte agente = new AgenteSoporte();
        final boolean[] invocado = {false};
        ManejadorIncidente siguiente = new ManejadorIncidente() {
            @Override
            public void manejarIncidente(Incidente incidente) {
                invocado[0] = true;
            }
        };
        agente.setSiguienteManejador(siguiente);

        Incidente incidente = new Incidente();
        incidente.setDescripcion("Caso complejo de fraude");

        agente.manejarIncidente(incidente);

        assertTrue(invocado[0], "Debe delegar al siguiente manejador de la cadena.");
    }

    @Test
    public void testManejarIncidente_Limite_SinSiguienteManejador() {
        AgenteSoporte agente = new AgenteSoporte(); // sin setSiguienteManejador
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Caso complejo de fraude");

        agente.manejarIncidente(incidente);

        assertTrue(outContent.toString(StandardCharsets.UTF_8)
                .contains("[AgenteSoporte] No hay más manejadores en la cadena."));
    }
}
