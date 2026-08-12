package com.ticketbeat.servicios;

import com.ticketbeat.interfaces.ICanal;

public class CanalEmail implements ICanal{
    @Override
    public boolean enviar(String mensaje) {
        System.out.println("[EMAIL] Enviando correo: " + mensaje);
        return true;
    }

    @Override
    public boolean verificarDisponibilidad() {
        return true;
    }

}
