package com.ticketbeat.manejador_incidente;

import com.ticketbeat.modelo.EstadoIncidente;
import com.ticketbeat.modelo.Incidente;


public class DepartamentoAdministracion extends ManejadorIncidente {
    private String idDepartamento;

    public DepartamentoAdministracion() {
        this.idDepartamento = "DEPT-ADMIN-001";
    }

    @Override
    public void manejarIncidente(Incidente incidente) {
        System.out.println("[DepartamentoAdministracion " + idDepartamento + "] Recibió incidente escalado.");
        resolucionFinal(incidente);
    }

    public void resolucionFinal(Incidente incidente) {
        incidente.setEstado(EstadoIncidente.RESUELTO);
        System.out.println("[DepartamentoAdministracion " + idDepartamento + "] Resolución final aplicada: " 
            + incidente.getDescripcion());
        System.out.println("[DepartamentoAdministracion] Incidente cerrado exitosamente.");
    }
}
/*
 * Manejador Abstracto - Patrón Chain of Responsibility
 */
