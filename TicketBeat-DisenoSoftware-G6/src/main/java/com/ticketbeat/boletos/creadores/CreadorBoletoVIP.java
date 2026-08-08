/*
 * Creador Concreto - Patrón Factory Method
 */
package com.ticketbeat.boletos.creadores;

import com.ticketbeat.boletos.creaciones.BoletoVIP;
import com.ticketbeat.boletos.creadores.CreadorBoleto;
import com.ticketbeat.interfaces.IBoleto;

/**
 * Creador concreto que instancia boletos de tipo VIP.
 *
 * @author Rafael Cosmo
 */
public class CreadorBoletoVIP extends CreadorBoleto {

    @Override
    public IBoleto crearBoleto() {
        return new BoletoVIP();
    }
}

