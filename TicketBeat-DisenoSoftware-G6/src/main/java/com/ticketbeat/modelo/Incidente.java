package com.ticketbeat.modelo;

public class Incidente {
    private String idTicket;
    private String descripcion;
    private EstadoIncidente estado = EstadoIncidente.ABIERTO;

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EstadoIncidente getEstado() { return estado; }
    public void setEstado(EstadoIncidente estado) { this.estado = estado; }

    public String getIdTicket() { return idTicket; }
    public void setIdTicket(String idTicket) { this.idTicket = idTicket; }
}
