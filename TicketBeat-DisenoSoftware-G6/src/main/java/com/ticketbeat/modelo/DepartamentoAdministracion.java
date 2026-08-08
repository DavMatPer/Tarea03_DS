/*
 * Manejador Concreto - Patrón Chain of Responsibility
 */
package com.ticketbeat.modelo;

/**
 * Último eslabón de la cadena. Aplica resolución final
 * para cualquier incidente que llegue a este nivel.
 *
 * @author Rafael Cosmo
 */
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
        System.out.println("[DepartamentoAdministracion " + idDepartamento + "] Resolución final aplicada: " 
            + incidente.getDescripcion());
        System.out.println("[DepartamentoAdministracion] Incidente cerrado exitosamente.");
    }
}
