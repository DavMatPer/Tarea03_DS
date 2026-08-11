package com.ticketbeat.modelo;

/**
 * Estados posibles de un {@link Evento}.
 *
 * Introducido para corregir el code smell "Obsesión Primitiva": Evento.estado
 * antes era un String libre ("CANCELADO", etc.), sin ninguna verificación en
 * tiempo de compilación.
 *
 * @author Rafael Cosmo
 */
public enum EstadoEvento {
    ACTIVO,
    CANCELADO
}
