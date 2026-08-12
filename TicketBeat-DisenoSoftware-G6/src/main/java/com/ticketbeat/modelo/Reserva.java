package com.ticketbeat.modelo;

import com.ticketbeat.interfaces.IBoleto;
import java.util.Date;
import java.util.List;

public class Reserva {
    private final String id;
    private final Comprador comprador;
    private final List<IBoleto> boletosReservados;
    private final Date fechaExpiracion;

    public Reserva(String id, Comprador comprador, List<IBoleto> boletosReservados, Date fechaExpiracion) {
        this.id = id;
        this.comprador = comprador;
        this.boletosReservados = boletosReservados;
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getId() { return id; }
    public Comprador getComprador() { return comprador; }
    public List<IBoleto> getBoletosReservados() { return boletosReservados; }
    public Date getFechaExpiracion() { return fechaExpiracion; }
}
