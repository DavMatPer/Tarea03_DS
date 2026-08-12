package com.ticketbeat.boletos.creadores;

public class BoletoGeneral extends BoletoAbstracto {
    private String seccion;

    public BoletoGeneral() {
        super(100.0);
        this.seccion = "General A";
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("=== BOLETO GENERAL ===");
        System.out.println("Sección: " + seccion);
        System.out.println("Precio: $" + precio);
        System.out.println("Estado: " + estado);
    }

    public String getSeccion() { return seccion; }
    public void setSeccion(String seccion) { this.seccion = seccion; }
}
