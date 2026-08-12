package com.ticketbeat.politica;

import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.interfaces.IPoliticaCompra;
import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.Evento;
import java.util.ArrayList;
import java.util.List;

public class PoliticaEventoBase implements IPoliticaCompra {
    private boolean permiteDevoluciones;
    private int plazoDevolucionDias;
    private double porcentajeReembolso;

    public PoliticaEventoBase() {
        this.permiteDevoluciones = true;
        this.plazoDevolucionDias = 30;
        this.porcentajeReembolso = 0.8;
    }

    public boolean isPermiteDevoluciones() { return permiteDevoluciones; }
    public void setPermiteDevoluciones(boolean permiteDevoluciones) { this.permiteDevoluciones = permiteDevoluciones; }

    public int getPlazoDevolucionDias() { return plazoDevolucionDias; }
    public void setPlazoDevolucionDias(int plazoDevolucionDias) { this.plazoDevolucionDias = plazoDevolucionDias; }

    public double getPorcentajeReembolso() { return porcentajeReembolso; }
    public void setPorcentajeReembolso(double porcentajeReembolso) { this.porcentajeReembolso = porcentajeReembolso; }


    @Override
    public boolean validarCompra(Comprador comprador, int cantidad) {
        if (comprador == null || cantidad <= 0) {
            System.out.println("[PoliticaBase] RECHAZADO: comprador nulo o cantidad invalida.");
            return false;
        }
        System.out.println("[PoliticaBase] Validacion base: compra permitida.");
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

    @Override
    public List<IBoleto> aplicarPoliticaDevolucion(Evento evento) {
        if (evento == null) {
            return new ArrayList<>();
        }
        return evento.obtenerBoletosParaDevolucion();
    }
}
