package com.ticketbeat.gestores;

import com.ticketbeat.modelo.Incidente;
import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.ManejadorIncidente;
import com.ticketbeat.modelo.AgenteSoporte;
import com.ticketbeat.modelo.DepartamentoAdministracion;

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

    // Método legacy para compatibilidad con el main existente
    public void reportarIncidente(String descripcion, Object agente, Object admin, Comprador comprador, GestorNotificaciones notificador) {
        Incidente nuevoIncidente = registrarYClasificar(descripcion);
        System.out.println("Delegando incidente a la cadena de soporte...");
        cadenaSoporte.manejarIncidente(nuevoIncidente);
        notificador.notificarResolucion(comprador, "Su incidente ha sido cerrado exitosamente.");
    }

    public Incidente registrarYClasificar(String descripcion) {
        System.out.println("Incidente registrado y clasificado.");
        Incidente incidente = new Incidente();
        incidente.setDescripcion(descripcion);
        return incidente;
    }
}
