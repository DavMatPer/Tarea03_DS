package com.ticketbeat.boletos.creadores;

import com.ticketbeat.boletos.creadores.BoletoGeneral;
import com.ticketbeat.boletos.creadores.CreadorBoleto;
import com.ticketbeat.interfaces.IBoleto;

public class CreadorBoletoGeneral extends CreadorBoleto {

    @Override
    public IBoleto crearBoleto() {
        return new BoletoGeneral();
    }
}
