package com.ticketbeat.boletos.creadores;

import com.ticketbeat.interfaces.IBoleto;

public abstract class CreadorBoleto {

    public abstract IBoleto crearBoleto();

    public void procesarEmision() {
        IBoleto b = crearBoleto();
        b.mostrarDetalles();
    }
}
