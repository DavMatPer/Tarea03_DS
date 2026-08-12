package com.ticketbeat.boletos.creadores;

import com.ticketbeat.boletos.creadores.BoletoReservado;
import com.ticketbeat.boletos.creadores.CreadorBoleto;
import com.ticketbeat.interfaces.IBoleto;

public class CreadorBoletoReservado extends CreadorBoleto {

    @Override
    public IBoleto crearBoleto() {
        return new BoletoReservado();
    }
}
