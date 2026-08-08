package com.ticketbeat.modelo;

import com.ticketbeat.interfaces.IPoliticaCompra;

public class PoliticaEventoBase implements IPoliticaCompra {
    private boolean permiteDevoluciones;
    private int plazoDevolucionDias;
    private double porcentajeReembolso;

    public PoliticaEventoBase() {
        this.permiteDevoluciones = true;
        this.plazoDevolucionDias = 30;
        this.porcentajeReembolso = 0.8;
    }

    @Override
    public boolean validarCompra(Comprador comprador, int cantidad) {
        System.out.println("[PoliticaBase] Validación base: compra permitida.");
        return true;
    }

    @Override
    public double calcularReembolso(double monto) {
        if (permiteDevoluciones) {
            return monto * porcentajeReembolso;
        }
        return 0;
    }

    @Override
    public boolean permiteCambioFecha() {
        return true;
    }
}
