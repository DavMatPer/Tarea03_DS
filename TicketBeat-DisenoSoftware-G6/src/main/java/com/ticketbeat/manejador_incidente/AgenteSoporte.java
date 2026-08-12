package com.ticketbeat.manejador_incidente;

import com.ticketbeat.modelo.EstadoIncidente;
import com.ticketbeat.modelo.Incidente;



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
            incidente.setEstado(EstadoIncidente.RESUELTO);
            System.out.println("[AgenteSoporte " + idAgente + "] Incidente resuelto en primer nivel: " 
                + incidente.getDescripcion());
        } else {
            System.out.println("[AgenteSoporte " + idAgente + "] No puede resolver. Escalando al siguiente nivel...");
            if (this.siguienteManejador != null) {
                this.siguienteManejador.manejarIncidente(incidente);
            } else {
                System.out.println("[AgenteSoporte] No hay mas manejadores en la cadena.");
            }
        }
    }
}
/*
 * Manejador Concreto - Patrón Chain of Responsibility
 */
