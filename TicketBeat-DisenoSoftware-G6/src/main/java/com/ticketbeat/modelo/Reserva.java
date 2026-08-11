package com.ticketbeat.modelo;

/**
 *
 * @author Rafael Cosmo
 */
import com.ticketbeat.interfaces.IBoleto;
import java.util.Date;
import java.util.List;

/**
 * Antes solo declaraba sus cuatro campos y un único getter (getComprador()),
 * sin constructor propio ni forma de poblarla desde fuera del paquete
 * (code smell "Clase Floja"). Ahora tiene un constructor real y accesores
 * completos, y GestorReservas la usa efectivamente para representar una
 * reserva en curso.
 */
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
