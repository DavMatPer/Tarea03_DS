/*
 * Creador Concreto - Patrón Factory Method
 */
package com.ticketbeat.boletos.creadores;

import com.ticketbeat.boletos.creaciones.BoletoReservado;
import com.ticketbeat.boletos.creadores.CreadorBoleto;
import com.ticketbeat.interfaces.IBoleto;

/**
 * Creador concreto que instancia boletos de tipo Reservado.
 *
 * @author Rafael Cosmo
 */
public class CreadorBoletoReservado extends CreadorBoleto {

    @Override
    public IBoleto crearBoleto() {
        return new BoletoReservado();
    }
}