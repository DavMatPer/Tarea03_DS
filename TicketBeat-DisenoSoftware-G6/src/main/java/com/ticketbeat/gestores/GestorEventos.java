package com.ticketbeat.gestores;

import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.modelo.EstadoEvento;
import com.ticketbeat.modelo.Evento;
import java.util.List;

/**
 * Ahora recibe su {@link GestorNotificaciones} colaborador por constructor
 * en vez de por parámetro en cada llamada (corrección parcial del code smell
 * "Lista de Parámetros Larga"), y confirmarCancelacion ya no exige una
 * PoliticaEvento externa: usa evento.getPolitica(), que ya existía pero se
 * ignoraba (corrección de "Intimidad Inapropiada" y, de paso, de "Envidia de
 * Características", ya que solicitarResumenEvento también dejó de recibir el
 * parámetro "idEvento" que nunca usaba — "Generalización Especulativa").
 *
 * @author Rafael Cosmo
 */
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
