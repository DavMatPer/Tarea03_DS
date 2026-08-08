package com.ticketbeat.politica;

import com.ticketbeat.interfaces.IPoliticaCompra;
import com.ticketbeat.modelo.Comprador;

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
