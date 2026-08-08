/*
 * Creador Abstracto - Patrón Factory Method
 */
package com.ticketbeat.modelo;

import com.ticketbeat.interfaces.IBoleto;

/**
 * Clase abstracta que define el Factory Method para la creación de boletos.
 * Los subclases concretas deciden qué tipo de boleto instanciar.
 *
 * @author Rafael Cosmo
 */
public abstract class CreadorBoleto {

    /**
     * Factory Method: método abstracto que las subclases implementan
     * para crear el tipo específico de boleto.
     */
    public abstract IBoleto crearBoleto();

    /**
     * Método concreto que utiliza el Factory Method para procesar
     * la emisión de un boleto sin conocer su tipo concreto.
     */
    public void procesarEmision() {
        IBoleto b = crearBoleto();
        b.mostrarDetalles();
    }
}

