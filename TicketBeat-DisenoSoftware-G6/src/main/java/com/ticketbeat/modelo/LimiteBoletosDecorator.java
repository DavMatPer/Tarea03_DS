package com.ticketbeat.modelo;

import com.ticketbeat.interfaces.IPoliticaCompra;

public class LimiteBoletosDecorator extends PoliticaDecorator {
    private int limiteBoletoPorUsuario;

    public LimiteBoletosDecorator(IPoliticaCompra componente, int limiteBoletoPorUsuario) {
        super(componente);
        this.limiteBoletoPorUsuario = limiteBoletoPorUsuario;
    }

    @Override
    public boolean validarCompra(Comprador comprador, int cantidad) {
        if (cantidad > limiteBoletoPorUsuario) {
            System.out.println("[LimiteBoletos] RECHAZADO: Se intentó comprar " + cantidad 
                + " boletos, pero el límite es " + limiteBoletoPorUsuario + ".");
            return false;
        }
        System.out.println("[LimiteBoletos] OK: Cantidad " + cantidad + " dentro del límite de " + limiteBoletoPorUsuario + ".");
        return componente.validarCompra(comprador, cantidad);
    }
}
