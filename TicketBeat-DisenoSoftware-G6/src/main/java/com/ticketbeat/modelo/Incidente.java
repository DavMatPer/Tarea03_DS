package com.ticketbeat.modelo;

/**
 * Antes el campo "estado" no tenía getter ni setter, por lo que era
 * inalcanzable desde fuera de la clase (code smell "Clase Floja"). Ahora
 * expone accesores y usa {@link EstadoIncidente} en vez de un String libre
 * (code smell "Obsesión Primitiva").
 *
 * @author Rafael Cosmo
 */
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
