package com.ticketbeat.interfaces;

import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.Evento;
import java.util.List;

public interface IPoliticaCompra {
    boolean validarCompra(Comprador comprador, int cantidad);
    double calcularReembolso(double monto);
    boolean permiteCambioFecha();
    List<IBoleto> aplicarPoliticaDevolucion(Evento evento);
}
