package com.ticketbeat.modelo;

/**
 *
 * @author Rafael Cosmo
 */
import com.ticketbeat.interfaces.IBoleto;
import java.util.ArrayList;
import java.util.List;

public class PoliticaEvento {
    private String descripcion;
    private double porcentajeDevolucion;

    public List<IBoleto> aplicarPoliticaDevolucion(Evento evento) {
        System.out.println("Aplicando políticas de devolución para el evento...");

        List<IBoleto> boletosAfectados = new ArrayList<>();
        if (evento == null || evento.getBoletos() == null) {
            return boletosAfectados;
        }

        for (IBoleto boleto : evento.getBoletos()) {
            if (boleto == null) continue;
            EstadoBoleto estado = boleto.getEstado();
            if (estado == EstadoBoleto.VENDIDO || estado == EstadoBoleto.RESERVADO) {
                boletosAfectados.add(boleto);
            }
        }

        return boletosAfectados;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */