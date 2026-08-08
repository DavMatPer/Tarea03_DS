/*
 * Producto Concreto - Patrón Factory Method
 */
package com.ticketbeat.modelo;

import com.ticketbeat.interfaces.IBoleto;

/**
 * Boleto de tipo Reservado con asiento y fila específicos.
 *
 * @author Rafael Cosmo
 */
public class BoletoReservado implements IBoleto {
    private String id;
    private EstadoBoleto estado;
    private double precio;
    private String numeroAsiento;
    private String fila;

    public BoletoReservado() {
        this.estado = EstadoBoleto.DISPONIBLE;
        this.precio = 250.0;
        this.numeroAsiento = "A15";
        this.fila = "Fila 3";
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("=== BOLETO RESERVADO ===");
        System.out.println("Asiento: " + numeroAsiento);
        System.out.println("Fila: " + fila);
        System.out.println("Precio: $" + precio);
        System.out.println("Estado: " + estado);
    }

    @Override
    public EstadoBoleto getEstado() {
        return estado;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    public void setEstado(EstadoBoleto estado) { this.estado = estado; }
    public String getNumeroAsiento() { return numeroAsiento; }
    public void setNumeroAsiento(String numeroAsiento) { this.numeroAsiento = numeroAsiento; }
    public String getFila() { return fila; }
    public void setFila(String fila) { this.fila = fila; }
}
