package com.ticketbeat.politica;

import com.ticketbeat.interfaces.IPoliticaCompra;
import com.ticketbeat.modelo.Comprador;

public class LimiteBoletosDecorator extends PoliticaDecorator {
    private int limiteBoletoPorUsuario;

    public LimiteBoletosDecorator(IPoliticaCompra componente, int limiteBoletoPorUsuario) {
        super(componente);
        this.limiteBoletoPorUsuario = limiteBoletoPorUsuario;
    }

    @Override
    public boolean validarCompra(Comprador comprador, int cantidad) {
        if (cantidad > limiteBoletoPorUsuario) {
            System.out.println("[LimiteBoletos] RECHAZADO: Se intento comprar " + cantidad
                + " boletos, pero el limite es " + limiteBoletoPorUsuario + ".");
            return false;
        }
        System.out.println("[LimiteBoletos] OK: Cantidad " + cantidad + " dentro del limite de " + limiteBoletoPorUsuario + ".");
        return componente.validarCompra(comprador, cantidad);
    }
}
