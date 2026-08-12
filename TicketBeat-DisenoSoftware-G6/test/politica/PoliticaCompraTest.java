package com.ticketbeat.politica;

import com.ticketbeat.boletos.creaciones.BoletoGeneral;
import com.ticketbeat.boletos.creaciones.BoletoVIP;
import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.interfaces.IPoliticaCompra;
import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.EstadoBoleto;
import com.ticketbeat.modelo.Evento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class PoliticaCompraTest {

    private Comprador compradorValido;

    @BeforeEach
    public void setUp() {
        compradorValido = new Comprador();
        compradorValido.setNombre("Juan Perez");
        compradorValido.setEdad(25);
        compradorValido.setEsSocio(true);
    }

    @Test
    public void testPoliticaEventoBase_ValidarCompra_Tipico() {
        PoliticaEventoBase politica = new PoliticaEventoBase();
        assertTrue(politica.validarCompra(compradorValido, 2));
    }

    @Test
    public void testPoliticaEventoBase_ValidarCompra_Limite_CompradorNuloOCantidadInvalida() {
        PoliticaEventoBase politica = new PoliticaEventoBase();
        assertFalse(politica.validarCompra(null, 2));
        assertFalse(politica.validarCompra(compradorValido, 0));
    }

    @Test
    public void testPoliticaEventoBase_CalcularReembolso_Tipico() {
        PoliticaEventoBase politica = new PoliticaEventoBase(); // permiteDevoluciones=true, 80% por defecto
        assertEquals(80.0, politica.calcularReembolso(100.0));
    }

    @Test
    public void testPoliticaEventoBase_CalcularReembolso_Limite_SinDevoluciones() {
        PoliticaEventoBase politica = new PoliticaEventoBase();
        politica.setPermiteDevoluciones(false);
        assertEquals(0, politica.calcularReembolso(100.0));
    }

    @Test
    public void testPoliticaEventoBase_CalcularReembolso_Error_MontoNegativo() {
        PoliticaEventoBase politica = new PoliticaEventoBase();
        assertEquals(-80.0, politica.calcularReembolso(-100.0));
    }

    @Test
    public void testLimiteBoletosDecorator_ValidarCompra_Tipico_DentroDelLimite() {
        IPoliticaCompra base = new PoliticaEventoBase();
        LimiteBoletosDecorator decorator = new LimiteBoletosDecorator(base, 5);
        assertTrue(decorator.validarCompra(compradorValido, 3));
    }

    @Test
    public void testLimiteBoletosDecorator_ValidarCompra_Limite_CantidadIgualAlLimite() {
        IPoliticaCompra base = new PoliticaEventoBase();
        LimiteBoletosDecorator decorator = new LimiteBoletosDecorator(base, 5);
        assertTrue(decorator.validarCompra(compradorValido, 5));
    }

    @Test
    public void testLimiteBoletosDecorator_ValidarCompra_Tipico_ExcedeElLimite() {
        IPoliticaCompra base = new PoliticaEventoBase();
        LimiteBoletosDecorator decorator = new LimiteBoletosDecorator(base, 5);
        assertFalse(decorator.validarCompra(compradorValido, 6));
    }

    @Test
    public void testRestriccionSocioDecorator_ValidarCompra_Tipico_RechazaNoSocio() {
        IPoliticaCompra base = new PoliticaEventoBase();
        RestriccionSocioDecorator decorator = new RestriccionSocioDecorator(base, true);

        Comprador noSocio = new Comprador();
        noSocio.setNombre("Pedro Ruiz");
        noSocio.setEdad(30);
        noSocio.setEsSocio(false);

        assertFalse(decorator.validarCompra(noSocio, 1));
    }

    @Test
    public void testRestriccionSocioDecorator_ValidarCompra_Limite_SinRequerirMembresia() {
        IPoliticaCompra base = new PoliticaEventoBase();
        RestriccionSocioDecorator decorator = new RestriccionSocioDecorator(base, false);

        assertTrue(decorator.validarCompra(compradorValido, 1),
                "Debe delegar al componente envuelto sin evaluar la membresía.");
    }

    @Test
    public void testVerificacionEdadDecorator_ValidarCompra_Tipico_MenorDeEdad() {
        IPoliticaCompra base = new PoliticaEventoBase();
        VerificacionEdadDecorator decorator = new VerificacionEdadDecorator(base, 18);

        Comprador menor = new Comprador();
        menor.setNombre("Sofia Diaz");
        menor.setEdad(15);
        menor.setEsSocio(false);

        assertFalse(decorator.validarCompra(menor, 1));
    }

    @Test
    public void testVerificacionEdadDecorator_ValidarCompra_Limite_EdadMinimaExacta() {
        IPoliticaCompra base = new PoliticaEventoBase();
        VerificacionEdadDecorator decorator = new VerificacionEdadDecorator(base, 18);

        Comprador edadExacta = new Comprador();
        edadExacta.setNombre("Carlos Vera");
        edadExacta.setEdad(18);
        edadExacta.setEsSocio(false);

        assertTrue(decorator.validarCompra(edadExacta, 1));
    }

    @Test
    public void testPoliticaEventoBase_AplicarPoliticaDevolucion_Tipico() {
        Evento evento = new Evento();
        BoletoGeneral vendido = new BoletoGeneral();
        vendido.setEstado(EstadoBoleto.VENDIDO);
        BoletoVIP disponible = new BoletoVIP();
        evento.agregarBoleto(vendido);
        evento.agregarBoleto(disponible);

        IPoliticaCompra politica = new PoliticaEventoBase();
        List<IBoleto> afectados = politica.aplicarPoliticaDevolucion(evento);

        assertEquals(1, afectados.size());
        assertTrue(afectados.contains(vendido));
    }

    @Test
    public void testDecorator_AplicarPoliticaDevolucion_Delega() {
        Evento evento = new Evento();
        BoletoGeneral vendido = new BoletoGeneral();
        vendido.setEstado(EstadoBoleto.VENDIDO);
        evento.agregarBoleto(vendido);

        IPoliticaCompra decorada = new LimiteBoletosDecorator(new PoliticaEventoBase(), 4);
        List<IBoleto> afectados = decorada.aplicarPoliticaDevolucion(evento);

        assertEquals(1, afectados.size());
        assertTrue(afectados.contains(vendido));
    }
}
