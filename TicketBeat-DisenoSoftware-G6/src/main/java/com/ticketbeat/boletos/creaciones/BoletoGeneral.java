package com.ticketbeat.boletos.creaciones;

/**
 * Boleto de tipo General con una sección asignada.
 *
 * Ahora extiende {@link BoletoAbstracto}, que concentra id/estado/precio y
 * sus accesores (corrección del code smell "Código Duplicado").
 *
 * @author Rafael Cosmo
 */
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
