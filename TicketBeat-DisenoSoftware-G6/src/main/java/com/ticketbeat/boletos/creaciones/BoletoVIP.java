package com.ticketbeat.boletos.creaciones;

import java.util.Arrays;

/**
 * Boleto de tipo VIP con zona exclusiva y beneficios adicionales.
 *
 * Ahora extiende {@link BoletoAbstracto} (corrección del code smell "Código
 * Duplicado").
 *
 * @author Rafael Cosmo
 */
public class BoletoVIP extends BoletoAbstracto {
    private String zonaVIP;
    private String[] beneficios;

    public BoletoVIP() {
        super(500.0);
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

    public String getZonaVIP() { return zonaVIP; }
    public void setZonaVIP(String zonaVIP) { this.zonaVIP = zonaVIP; }
    public String[] getBeneficios() { return beneficios; }
    public void setBeneficios(String[] beneficios) { this.beneficios = beneficios; }
}
