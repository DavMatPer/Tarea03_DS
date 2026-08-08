/*
 * Manejador Concreto - Patrón Chain of Responsibility
 */
package com.ticketbeat.modelo;

/**
 * Primer eslabón de la cadena. Intenta resolver el incidente
 * en primer nivel; si no puede, lo pasa al siguiente manejador.
 *
 * @author Rafael Cosmo
 */
public class AgenteSoporte extends ManejadorIncidente {
    private String idAgente;

    public AgenteSoporte() {
        this.idAgente = "AGT-001";
    }

    public boolean puedeResolver(Incidente incidente) {
        // Un agente puede resolver incidentes simples (ej. sin la palabra "complejo")
        String desc = incidente.getDescripcion();
        return desc != null && !desc.toLowerCase().contains("complejo");
    }

    @Override
    public void manejarIncidente(Incidente incidente) {
        if (puedeResolver(incidente)) {
            System.out.println("[AgenteSoporte " + idAgente + "] Incidente resuelto en primer nivel: " 
                + incidente.getDescripcion());
        } else {
            System.out.println("[AgenteSoporte " + idAgente + "] No puede resolver. Escalando al siguiente nivel...");
            if (this.siguienteManejador != null) {
                this.siguienteManejador.manejarIncidente(incidente);
            } else {
                System.out.println("[AgenteSoporte] No hay más manejadores en la cadena.");
            }
        }
    }
}
