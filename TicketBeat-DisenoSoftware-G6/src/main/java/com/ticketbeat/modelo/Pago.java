package com.ticketbeat.modelo;

public class Pago {
    private String id;
    private double monto;
    private String estado;

    public Pago(String id, double monto, String estado) {
        this.id = id;
        this.monto = monto;
        this.estado = estado;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
