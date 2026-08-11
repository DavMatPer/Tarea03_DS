package com.ticketbeat.modelo;

/**
 * Ahora usa {@link EstadoPago} en vez de un String libre (corrección del code
 * smell "Obsesión Primitiva"), y expone {@link #estaCompletado()} para que
 * los llamadores dejen de comparar el estado manualmente (corrección parcial
 * del code smell "Clase de Datos").
 *
 * @author Rafael Cosmo
 */
public class Pago {
    private String id;
    private double monto;
    private EstadoPago estado;

    public Pago(String id, double monto, EstadoPago estado) {
        this.id = id;
        this.monto = monto;
        this.estado = estado;
    }

    public boolean estaCompletado() {
        return estado == EstadoPago.COMPLETADO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }
}
