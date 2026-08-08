/*
 * Manejador Abstracto - Patrón Chain of Responsibility
 */
package com.ticketbeat.modelo;

/**
 * Clase abstracta que define la cadena de responsabilidad
 * para el manejo de incidentes.
 *
 * @author Rafael Cosmo
 */
public abstract class ManejadorIncidente {
    protected ManejadorIncidente siguienteManejador;

    public void setSiguienteManejador(ManejadorIncidente man) {
        this.siguienteManejador = man;
    }

    public abstract void manejarIncidente(Incidente incidente);
}
