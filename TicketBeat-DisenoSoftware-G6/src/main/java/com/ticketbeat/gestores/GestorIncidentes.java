package com.ticketbeat.gestores;

import com.ticketbeat.modelo.Incidente;
import com.ticketbeat.manejador_incidente.ManejadorIncidente;
import com.ticketbeat.manejador_incidente.AgenteSoporte;
import com.ticketbeat.manejador_incidente.DepartamentoAdministracion;


public class GestorIncidentes {

    private ManejadorIncidente cadenaSoporte;

    public GestorIncidentes() {
        // Enlazar los eslabones de la cadena en el constructor
        AgenteSoporte agente = new AgenteSoporte();
        DepartamentoAdministracion departamentoAdmin = new DepartamentoAdministracion();
        agente.setSiguienteManejador(departamentoAdmin);
        this.cadenaSoporte = agente;
    }

    public void registrarIncidente(String descripcion) {
        Incidente nuevoIncidente = registrarYClasificar(descripcion);
        System.out.println("Delegando incidente a la cadena de soporte...");
        cadenaSoporte.manejarIncidente(nuevoIncidente);
    }

    public Incidente registrarYClasificar(String descripcion) {
        System.out.println("Incidente registrado y clasificado.");
        Incidente incidente = new Incidente();
        incidente.setDescripcion(descripcion);
        return incidente;
    }
}
