/*
 * Interfaz de Producto - Patrón Factory Method
 */
package com.ticketbeat.interfaces;

import com.ticketbeat.modelo.EstadoBoleto;

/**
 * Interfaz que define el contrato para todos los tipos de boletos.
 * Parte del patrón Factory Method.
 *
 * @author Rafael Cosmo
 */
public interface IBoleto {
    void mostrarDetalles();
    EstadoBoleto getEstado();
    double getPrecio();
}
