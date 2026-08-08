/*
 * Creador Concreto - Patrón Factory Method
 */
package com.ticketbeat.modelo;

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