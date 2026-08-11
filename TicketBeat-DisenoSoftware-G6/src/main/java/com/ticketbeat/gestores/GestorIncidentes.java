package com.ticketbeat.gestores;

import com.ticketbeat.modelo.Incidente;
import com.ticketbeat.manejador_incidente.ManejadorIncidente;
import com.ticketbeat.manejador_incidente.AgenteSoporte;
import com.ticketbeat.manejador_incidente.DepartamentoAdministracion;

/**
 * Se eliminó el método legacy "reportarIncidente" (que duplicaba el flujo de
 * registrarIncidente y además recibía dos parámetros de tipo Object,
 * "agente"/"admin", que nunca usaba): corrección de los code smells
 * "Código Duplicado a Nivel de Responsabilidad", "Generalización
 * Especulativa" y "Lista de Parámetros Larga". Si además de registrar y
 * escalar un incidente se necesita notificar al comprador, ese paso ahora se
 * hace explícito en el llamador, con GestorNotificaciones.notificarResolucion(...).
 *
 * @author Rafael Cosmo
 */
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
