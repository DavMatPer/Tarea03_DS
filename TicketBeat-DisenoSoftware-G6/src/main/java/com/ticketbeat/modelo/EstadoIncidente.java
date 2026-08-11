package com.ticketbeat.modelo;

/**
 * Estados posibles de un {@link Incidente}.
 *
 * Introducido junto con la corrección del code smell "Clase Floja" (Incidente
 * no tenía accesores para su campo "estado") y "Obsesión Primitiva" (se evita
 * modelarlo como String).
 *
 * @author Rafael Cosmo
 */
public enum EstadoIncidente {
    ABIERTO,
    RESUELTO
}
