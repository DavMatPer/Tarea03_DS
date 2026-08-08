/*
 * Producto Concreto - Patrón Factory Method
 */
package com.ticketbeat.modelo;

import com.ticketbeat.interfaces.IBoleto;
import java.util.Arrays;

/**
 * Boleto de tipo VIP con zona exclusiva y beneficios adicionales.
 *
 * @author Rafael Cosmo
 */
public class BoletoVIP implements IBoleto {
    private String id;
    private EstadoBoleto estado;
    private double precio;
    private String zonaVIP;
    private String[] beneficios;

    public BoletoVIP() {
        this.estado = EstadoBoleto.DISPONIBLE;
        this.precio = 500.0;
        this.zonaVIP = "Zona Platino";
        this.beneficios = new String[]{"Acceso backstage", "Bebidas incluidas", "Meet & Greet"};
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("=== BOLETO VIP ===");
        System.out.println("Zona VIP: " + zonaVIP);
        System.out.println("Precio: $" + precio);
        System.out.println("Estado: " + estado);
        System.out.println("Beneficios: " + Arrays.toString(beneficios));
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
    public String getZonaVIP() { return zonaVIP; }
    public void setZonaVIP(String zonaVIP) { this.zonaVIP = zonaVIP; }
    public String[] getBeneficios() { return beneficios; }
    public void setBeneficios(String[] beneficios) { this.beneficios = beneficios; }
}

