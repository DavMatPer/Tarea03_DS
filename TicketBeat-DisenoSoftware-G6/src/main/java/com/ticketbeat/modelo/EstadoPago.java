package com.ticketbeat.modelo;

/**
 * Estados posibles de un {@link Pago}.
 *
 * Introducido para corregir el code smell "Obsesión Primitiva": Pago.estado
 * antes era un String libre ("COMPLETADO"), comparado por texto en varios
 * puntos del sistema (p. ej. GestorReservas.confirmarCompra).
 *
 * @author Rafael Cosmo
 */
public enum EstadoPago {
    PENDIENTE,
    COMPLETADO,
    RECHAZADO
}
