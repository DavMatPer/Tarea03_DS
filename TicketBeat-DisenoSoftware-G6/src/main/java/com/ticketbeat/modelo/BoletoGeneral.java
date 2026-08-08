/*
 * Producto Concreto - Patrón Factory Method
 */
package com.ticketbeat.modelo;

import com.ticketbeat.interfaces.IBoleto;

/**
 * Boleto de tipo General con una sección asignada.
 *
 * @author Rafael Cosmo
 */
public class BoletoGeneral implements IBoleto {
    private String id;
    private EstadoBoleto estado;
    private double precio;
    private String seccion;

    public BoletoGeneral() {
        this.estado = EstadoBoleto.DISPONIBLE;
        this.precio = 100.0;
        this.seccion = "General A";
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("=== BOLETO GENERAL ===");
        System.out.println("Sección: " + seccion);
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
    public String getSeccion() { return seccion; }
    public void setSeccion(String seccion) { this.seccion = seccion; }
}
