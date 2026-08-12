package modelo;

import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    public void testAtributosHeredados_Tipico() {
        Comprador comprador = new Comprador(); // subclase concreta de Usuario
        assertNull(comprador.getNombre());
        assertNull(comprador.getId());
        assertNull(comprador.getEmail());
        assertNull(comprador.getTelefono());

        comprador.setId("USR-001");
        comprador.setEmail("juan.perez@example.com");
        comprador.setTelefono("555-1234");

        assertEquals("USR-001", comprador.getId());
        assertEquals("juan.perez@example.com", comprador.getEmail());
        assertEquals("555-1234", comprador.getTelefono());
    }

    @Test
    public void testAccesoresCompletos() {
        long publicAccessorsInUsuario = java.util.Arrays.stream(Usuario.class.getDeclaredMethods())
                .filter(m -> java.lang.reflect.Modifier.isPublic(m.getModifiers()))
                .count();
        assertEquals(8, publicAccessorsInUsuario,
                "Usuario ahora expone accesores para sus cuatro campos heredables.");
    }

    @Test
    public void testEsClaseAbstracta() {
        assertTrue(java.lang.reflect.Modifier.isAbstract(Usuario.class.getModifiers()));
    }
}
