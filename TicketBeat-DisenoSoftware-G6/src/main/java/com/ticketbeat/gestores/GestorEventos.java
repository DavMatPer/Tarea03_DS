package com.ticketbeat.gestores;

import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.modelo.EstadoEvento;
import com.ticketbeat.modelo.Evento;
import java.util.List;

public class GestorEventos {

    private final GestorNotificaciones notificador;

    public GestorEventos(GestorNotificaciones notificador) {
        this.notificador = notificador;
    }

    public void solicitarResumenEvento(Evento evento) {
        int boletos = evento.getBoletosVendidos();
        double monto = evento.getMontoRecaudado();
        System.out.println("Resumen: " + boletos + " boletos vendidos, Monto: $" + monto);
    }

    public void confirmarCancelacion(String motivo, Evento evento) {
        // 1. Cambia el estado
        evento.setEstado(EstadoEvento.CANCELADO);

        // 2. Aplica la política de devolución configurada en el propio evento
        List<IBoleto> listaBoletosAfectados = evento.getPolitica().aplicarPoliticaDevolucion(evento);

        // 3. Notifica a los compradores
        notificador.notificarCompradores(listaBoletosAfectados, "Evento cancelado: " + motivo);
    }
}
