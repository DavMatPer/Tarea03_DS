package com.ticketbeat.manejador_incidente;

import com.ticketbeat.modelo.Incidente;

public abstract class ManejadorIncidente {
    protected ManejadorIncidente siguienteManejador;

    public void setSiguienteManejador(ManejadorIncidente man) {
        this.siguienteManejador = man;
    }

    public abstract void manejarIncidente(Incidente incidente);
}
