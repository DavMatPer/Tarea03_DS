/*
 * Creador Concreto - Patrón Factory Method
 */
package com.ticketbeat.boletos.creadores;

import com.ticketbeat.boletos.creaciones.BoletoGeneral;
import com.ticketbeat.boletos.creadores.CreadorBoleto;
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