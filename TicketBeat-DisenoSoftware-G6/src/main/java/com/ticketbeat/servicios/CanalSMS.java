package com.ticketbeat.servicios;

import com.ticketbeat.interfaces.ICanal;

public class CanalSMS implements ICanal {
    @Override
    public boolean enviar(String mensaje) {
        System.out.println("[SMS] Enviando mensaje de texto: " + mensaje);
        return true;
    }

    @Override
    public boolean verificarDisponibilidad() {
        return true;
    }

}
