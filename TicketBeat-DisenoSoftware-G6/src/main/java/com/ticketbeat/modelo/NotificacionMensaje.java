package com.ticketbeat.modelo;

/**
 * Antes el campo "asunto" no tenía getter ni setter, por lo que era
 * inalcanzable desde fuera de la clase (code smell "Clase Floja").
 *
 * @author Rafael Cosmo
 */
public class NotificacionMensaje {
    private String asunto;
    private String contenido;

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
}
