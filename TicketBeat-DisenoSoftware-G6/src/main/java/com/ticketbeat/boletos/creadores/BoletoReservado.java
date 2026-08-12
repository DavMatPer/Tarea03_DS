/*
 * Producto Concreto - Patrón Factory Method
 */
package com.ticketbeat.boletos.creadores;

/**
 * Boleto de tipo Reservado con asiento y fila específicos.
 *
 * Ahora extiende {@link BoletoAbstracto} (corrección del code smell "Código
 * Duplicado").
 *
 * @author Rafael Cosmo
 */
public class BoletoReservado extends BoletoAbstracto {
    private String numeroAsiento;
    private String fila;

    public BoletoReservado() {
        super(250.0);
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

    public String getNumeroAsiento() { return numeroAsiento; }
    public void setNumeroAsiento(String numeroAsiento) { this.numeroAsiento = numeroAsiento; }
    public String getFila() { return fila; }
    public void setFila(String fila) { this.fila = fila; }
}
