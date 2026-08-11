package com.ticketbeat.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase abstracta Usuario, ejercitada a través de una subclase concreta.
 */
public class UsuarioTest {

    /**
     * Caso de prueba TC-U-001 del plan de pruebas.
     */
    @Test
    public void testAtributosHeredados_Error_SinInicializar() {
        Comprador comprador = new Comprador(); // subclase concreta de Usuario
        assertNull(comprador.getNombre(), "nombre inicia en null.");

        // id, email y telefono son protected en Usuario y no tienen getter/setter público:
        long publicAccessorsInUsuario = java.util.Arrays.stream(Usuario.class.getDeclaredMethods())
                .filter(m -> java.lang.reflect.Modifier.isPublic(m.getModifiers()))
                .count();
        assertEquals(2, publicAccessorsInUsuario, "Solo getNombre()/setNombre() son públicos en Usuario.");
    }

    /**
     * Confirma que Usuario es abstracta y por lo tanto no puede instanciarse
     * directamente. La restricción real se verifica en tiempo de compilación
     * (new Usuario() no compilaría); aquí se documenta usando reflection.
     */
    @Test
    public void testEsClaseAbstracta() {
        assertTrue(java.lang.reflect.Modifier.isAbstract(Usuario.class.getModifiers()));
    }
}
