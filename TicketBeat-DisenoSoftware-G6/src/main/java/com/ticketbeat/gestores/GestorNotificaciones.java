package com.ticketbeat.gestores;

import com.ticketbeat.interfaces.ICanal;
import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.modelo.Comprador;
import java.util.List;

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
        return null; // Aquí retornaría el ICanal asociado al comprador
    }

    private void registrarEstadoDeEntrega() {
        System.out.println("Estado de entrega registrado.");
    }

    // Métodos de apoyo llamados desde GestorEventos y GestorIncidentes
    public void notificarCompradores(List<IBoleto> afectados, String mensaje) {
        System.out.println("Enviando notificación masiva: " + mensaje);
    }

    public void notificarResolucion(Comprador comprador, String mensaje) {
        System.out.println("Notificando resolución al comprador: " + mensaje);
    }
}