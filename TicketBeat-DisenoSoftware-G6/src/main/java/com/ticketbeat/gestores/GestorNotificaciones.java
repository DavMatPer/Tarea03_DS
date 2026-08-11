package com.ticketbeat.gestores;

import com.ticketbeat.interfaces.ICanal;
import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.modelo.Comprador;
import java.util.List;

/**
 * notificarCompradores() y notificarResolucion() ahora usan realmente los
 * datos que reciben, en vez de ignorarlos e imprimir siempre el mismo mensaje
 * genérico (corrección del code smell "Código Muerto"). notificarCompradores
 * además usa IBoleto.getComprador() para notificar al comprador real de cada
 * boleto cuando ese vínculo existe, resolviendo la falta de relación
 * Boleto-Comprador señalada en las notas de diseño del plan de pruebas.
 *
 * @author Rafael Cosmo
 */
public class GestorNotificaciones {

    public void iniciarProcesoDeNotificacion(List<Comprador> compradoresAfectados, String mensajeBase) {
        identificarCompradoresAfectados();
        String mensaje = generarMensajePersonalizado(mensajeBase);

        // Loop: Por cada comprador afectado
        for (Comprador comprador : compradoresAfectados) {
            ICanal canal = seleccionarCanalDeComunicacion(comprador);
            
            // Fragmento ALT: Canal disponible
            if (canal != null && canal.verificarDisponibilidad()) {
                canal.enviar(mensaje);
                registrarEstadoDeEntrega();
            } else {
                System.out.println("Canal no disponible, intentar siguiente canal");
                // Lógica de reintento o encolamiento
                System.out.println("Encolar notificación para reintento");
            }
        }
    }

    private void identificarCompradoresAfectados() { }
    
    private String generarMensajePersonalizado(String base) {
        return "Hola, " + base;
    }

    private ICanal seleccionarCanalDeComunicacion(Comprador comprador) {
        if (comprador == null) {
            return null;
        }
        return comprador.getCanalPreferido();
    }

    private void registrarEstadoDeEntrega() {
        System.out.println("Estado de entrega registrado.");
    }

    /**
     * Antes ignoraba por completo "afectados" e imprimía siempre el mismo
     * mensaje genérico. Ahora recorre efectivamente la lista recibida y,
     * cuando el boleto tiene un comprador vinculado (IBoleto.getComprador()),
     * lo notifica a él específicamente por su canal preferido — antes esto
     * era imposible porque IBoleto no tenía ninguna referencia a Comprador.
     */
    public void notificarCompradores(List<IBoleto> afectados, String mensaje) {
        if (afectados == null || afectados.isEmpty()) {
            System.out.println("No hay boletos afectados; no se envían notificaciones.");
            return;
        }
        System.out.println("Enviando notificación masiva a " + afectados.size()
                + " boleto(s) afectado(s): " + mensaje);
        for (IBoleto boleto : afectados) {
            Comprador comprador = boleto.getComprador();
            if (comprador != null) {
                String nombre = (comprador.getNombre() != null) ? comprador.getNombre() : "comprador";
                System.out.println(" - Notificando a " + nombre + " (boleto $" + boleto.getPrecio()
                        + ", estado previo: " + boleto.getEstado() + "): " + mensaje);
                if (comprador.getCanalPreferido() != null) {
                    comprador.getCanalPreferido().enviar(mensaje);
                }
            } else {
                System.out.println(" - Boleto sin comprador identificado ($" + boleto.getPrecio()
                        + ", estado previo: " + boleto.getEstado() + "); no se puede notificar directamente: " + mensaje);
            }
        }
    }

    /**
     * Antes ignoraba por completo "comprador". Ahora valida su presencia y,
     * si tiene un canal preferido configurado, intenta enviarle el mensaje
     * por ese canal.
     */
    public void notificarResolucion(Comprador comprador, String mensaje) {
        if (comprador == null) {
            System.out.println("No se pudo notificar la resolución: comprador no especificado.");
            return;
        }
        String nombre = (comprador.getNombre() != null) ? comprador.getNombre() : "comprador";
        System.out.println("Notificando resolución a " + nombre + ": " + mensaje);
        if (comprador.getCanalPreferido() != null) {
            comprador.getCanalPreferido().enviar(mensaje);
        }
    }
}
