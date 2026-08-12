package com.ticketbeat.politica;

import com.ticketbeat.interfaces.IPoliticaCompra;
import com.ticketbeat.modelo.Comprador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para el patrón Decorator (Políticas de Compra): PoliticaEventoBase
 * y los decoradores LimiteBoletosDecorator, RestriccionSocioDecorator y
 * VerificacionEdadDecorator.
 *
 * NOTA: esta clase existía en el código original como un stub vacío; se
 * completó para cubrir la sección 5.5 del plan de pruebas.
 */
public class PoliticaCompraTest {

    private Comprador compradorValido;

    @BeforeEach
    public void setUp() {
        compradorValido = new Comprador();
        compradorValido.setNombre("Juan Perez");
        compradorValido.setEdad(25);
        compradorValido.setEsSocio(true);
    }

    // ---------- PoliticaEventoBase ----------

    /**
     * Caso de prueba TC-PEB-001 del plan de pruebas.
     */
    @Test
    public void testPoliticaEventoBase_ValidarCompra_Tipico() {
        PoliticaEventoBase politica = new PoliticaEventoBase();
        assertTrue(politica.validarCompra(compradorValido, 2));
    }

    /**
     * Caso de prueba TC-PEB-002 del plan de pruebas.
     */
    @Test
    public void testPoliticaEventoBase_ValidarCompra_Limite_CompradorNuloOCantidadInvalida() {
        PoliticaEventoBase politica = new PoliticaEventoBase();
        assertFalse(politica.validarCompra(null, 2));
        assertFalse(politica.validarCompra(compradorValido, 0));
    }

    /**
     * Caso de prueba TC-PEB-003 del plan de pruebas.
     */
    @Test
    public void testPoliticaEventoBase_CalcularReembolso_Tipico() {
        PoliticaEventoBase politica = new PoliticaEventoBase(); // permiteDevoluciones=true, 80% por defecto
        assertEquals(80.0, politica.calcularReembolso(100.0));
    }

    /**
     * Caso de prueba TC-PEB-004 del plan de pruebas.
     */
    @Test
    public void testPoliticaEventoBase_CalcularReembolso_Limite_SinDevoluciones() {
        PoliticaEventoBase politica = new PoliticaEventoBase();
        politica.setPermiteDevoluciones(false);
        assertEquals(0, politica.calcularReembolso(100.0));
    }

    /**
     * Caso de prueba TC-PEB-005 del plan de pruebas.
     */
    @Test
    public void testPoliticaEventoBase_CalcularReembolso_Error_MontoNegativo() {
        PoliticaEventoBase politica = new PoliticaEventoBase();
        assertEquals(-80.0, politica.calcularReembolso(-100.0));
    }

    // ---------- LimiteBoletosDecorator ----------

    /**
     * Caso de prueba TC-LBD-001 del plan de pruebas.
     */
    @Test
    public void testLimiteBoletosDecorator_ValidarCompra_Tipico_DentroDelLimite() {
        IPoliticaCompra base = new PoliticaEventoBase();
        LimiteBoletosDecorator decorator = new LimiteBoletosDecorator(base, 5);
        assertTrue(decorator.validarCompra(compradorValido, 3));
    }

    /**
     * Caso de prueba TC-LBD-002 del plan de pruebas.
     */
    @Test
    public void testLimiteBoletosDecorator_ValidarCompra_Limite_CantidadIgualAlLimite() {
        IPoliticaCompra base = new PoliticaEventoBase();
        LimiteBoletosDecorator decorator = new LimiteBoletosDecorator(base, 5);
        assertTrue(decorator.validarCompra(compradorValido, 5));
    }

    /**
     * Caso de prueba TC-LBD-003 del plan de pruebas.
     */
    @Test
    public void testLimiteBoletosDecorator_ValidarCompra_Tipico_ExcedeElLimite() {
        IPoliticaCompra base = new PoliticaEventoBase();
        LimiteBoletosDecorator decorator = new LimiteBoletosDecorator(base, 5);
        assertFalse(decorator.validarCompra(compradorValido, 6));
    }

    // ---------- RestriccionSocioDecorator ----------

    /**
     * Caso de prueba TC-RSD-001 del plan de pruebas.
     */
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

    /**
     * Caso de prueba TC-RSD-002 del plan de pruebas.
     */
    @Test
    public void testRestriccionSocioDecorator_ValidarCompra_Limite_SinRequerirMembresia() {
        IPoliticaCompra base = new PoliticaEventoBase();
        RestriccionSocioDecorator decorator = new RestriccionSocioDecorator(base, false);

        assertTrue(decorator.validarCompra(compradorValido, 1),
                "Debe delegar al componente envuelto sin evaluar la membresía.");
    }

    // ---------- VerificacionEdadDecorator ----------

    /**
     * Caso de prueba TC-VED-001 del plan de pruebas.
     */
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

    /**
     * Caso de prueba TC-VED-002 del plan de pruebas.
     */
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
}
