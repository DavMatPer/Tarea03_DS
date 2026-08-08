package com.ticketbeat.interfaces;

import com.ticketbeat.modelo.Comprador;

public interface IPoliticaCompra {
    boolean validarCompra(Comprador comprador, int cantidad);
    double calcularReembolso(double monto);
    boolean permiteCambioFecha();
}

