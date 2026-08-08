package com.ticketbeat.modelo;

import com.ticketbeat.interfaces.IPoliticaCompra;

public class VerificacionEdadDecorator extends PoliticaDecorator {
    private int edadMinima;

    public VerificacionEdadDecorator(IPoliticaCompra componente, int edadMinima) {
        super(componente);
        this.edadMinima = edadMinima;
    }

    @Override
    public boolean validarCompra(Comprador comprador, int cantidad) {
        if (comprador.getEdad() < edadMinima) {
            System.out.println("[VerificacionEdad] RECHAZADO: El comprador tiene " 
                + comprador.getEdad() + " años, pero la edad mínima es " + edadMinima + ".");
            return false;
        }
        System.out.println("[VerificacionEdad] OK: Edad " + comprador.getEdad() + " cumple con el mínimo de " + edadMinima + ".");
        return componente.validarCompra(comprador, cantidad);
    }
}
