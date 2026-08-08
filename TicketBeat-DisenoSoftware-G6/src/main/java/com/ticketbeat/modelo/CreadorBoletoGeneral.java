/*
 * Creador Concreto - Patrón Factory Method
 */
package com.ticketbeat.modelo;

import com.ticketbeat.interfaces.IBoleto;

/**
 * Creador concreto que instancia boletos de tipo General.
 *
 * @author Rafael Cosmo
 */
public class CreadorBoletoGeneral extends CreadorBoleto {

    @Override
    public IBoleto crearBoleto() {
        return new BoletoGeneral();
    }
}