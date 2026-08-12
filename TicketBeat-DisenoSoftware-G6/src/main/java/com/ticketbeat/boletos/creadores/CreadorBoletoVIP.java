package com.ticketbeat.boletos.creadores;

import com.ticketbeat.boletos.creadores.BoletoVIP;
import com.ticketbeat.boletos.creadores.CreadorBoleto;
import com.ticketbeat.interfaces.IBoleto;

public class CreadorBoletoVIP extends CreadorBoleto {

    @Override
    public IBoleto crearBoleto() {
        return new BoletoVIP();
    }
}
